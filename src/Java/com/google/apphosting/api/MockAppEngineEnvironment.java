package com.google.apphosting.api;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.dev.LocalTaskQueue;
import com.google.appengine.api.labs.taskqueue.dev.QueueStateInfo;
import com.google.appengine.api.labs.taskqueue.dev.QueueStateInfo.TaskStateInfo;
import com.google.appengine.tools.development.ApiProxyLocal;
import com.google.appengine.tools.development.ApiProxyLocalFactory;
import com.google.appengine.tools.development.LocalServerEnvironment;

/**
 * Mock for the App Engine Java environment used by the JDO wrapper.
 *
 * This class has been build with information gathered on:
 * - App Engine documentation: http://code.google.com/appengine/docs/java/howto/unittesting.html
 * - App Engine Fan blog: http://blog.appenginefan.com/2009/05/jdo-and-unit-tests.html
 *
 * @author Dom Derrien
 */
public class MockAppEngineEnvironment {

    private class MockApiProxyEnvironment implements ApiProxy.Environment {
        public String getAppId() {
            return "test";
        }

        public String getVersionId() {
            return "1.0";
        }

        public String getEmail() {
            throw new UnsupportedOperationException();
        }

        public boolean isLoggedIn() {
            throw new UnsupportedOperationException();
        }

        public boolean isAdmin() {
            throw new UnsupportedOperationException();
        }

        public String getAuthDomain() {
            throw new UnsupportedOperationException();
        }

        public String getRequestNamespace() {
            return "";
        }

        public Map<String, Object> getAttributes() {
            Map<String, Object> out = new HashMap<String, Object>();

            // Only be necessary for tasks that are added when there is no "live" request
            // See: http://groups.google.com/group/google-appengine-java/msg/8f5872b052144c8d?pli=1
            out.put("com.google.appengine.server_url_key", "http://localhost:8080");

            return out;
        }
    };

    private final ApiProxy.Environment env;
    private PersistenceManagerFactory pmf;

    public MockAppEngineEnvironment() {
        env = new MockApiProxyEnvironment();
    }

    /**
     * Setup the mock environment
     */
    public void setUp() throws Exception {
        // Setup the App Engine services
        ApiProxy.setEnvironmentForCurrentThread(env);
        ApiProxyLocalFactory factory = new ApiProxyLocalFactory();
        ApiProxyLocal proxy = factory.create(new LocalServerEnvironment() {
            public void waitForServerToStart() throws InterruptedException {}
            public int getPort() { return 0; }
            public File getAppDir() { return new File("."); }
            public String getAddress() { return null; }
        });

        // Setup the App Engine data store
        proxy.setProperty(LocalDatastoreService.NO_STORAGE_PROPERTY, Boolean.TRUE.toString());
        ApiProxy.setDelegate(proxy);

        // Setup the Task Queue
        // Nothing special here, just a flush during tearDown()
    }

    /**
     * Clean up the mock environment
     */
    public void tearDown() throws Exception {
        // Verify that there's no pending transaction (ie pm.close() has been called)
        Transaction transaction = DatastoreServiceFactory.getDatastoreService().getCurrentTransaction(null);
        boolean transactionPending = transaction != null;
        if (transactionPending) {
            transaction.rollback();
        }

        // Clean up the App Engine data store
        ApiProxyLocal proxy = (ApiProxyLocal) ApiProxy.getDelegate();
        if (proxy != null) {
            LocalDatastoreService datastoreService = (LocalDatastoreService) proxy.getService("datastore_v3");
            datastoreService.clearProfiles();
        }

        // Clean up the App Engine services
        ApiProxy.setDelegate(null);
        ApiProxy.clearEnvironmentForCurrentThread();

        // Clean-uup the Task Queue
        // See: http://groups.google.com/group/google-appengine-java/browse_thread/thread/fe334c9e461026fa/8f5872b052144c8d?#8f5872b052144c8d
        if (proxy != null) {
            LocalTaskQueue ltq = (LocalTaskQueue) proxy.getService(LocalTaskQueue.PACKAGE);
            ltq.flushQueue(QueueFactory.getDefaultQueue().getQueueName());
        }

        // Report the issue with the transaction still open
        if (transactionPending) {
            throw new IllegalStateException("Found a transaction nor commited neither rolled-back. Probably related to a missing PersistenceManager.close() call.");
        }
    }

    /**
     * Creates a PersistenceManagerFactory on the fly, with the exact same information
     *  stored in the<war-dir>/WEB-INF/META-INF/jdoconfig.xml file.
     */
    public PersistenceManagerFactory getPersistenceManagerFactory() {
        if (pmf == null) {
            Properties newProperties = new Properties();
            newProperties.put("javax.jdo.PersistenceManagerFactoryClass",
                    "org.datanucleus.store.appengine.jdo.DatastoreJDOPersistenceManagerFactory");
            newProperties.put("javax.jdo.option.ConnectionURL", "appengine");
            newProperties.put("javax.jdo.option.NontransactionalRead", "true");
            newProperties.put("javax.jdo.option.NontransactionalWrite", "true");
            newProperties.put("javax.jdo.option.RetainValues", "true");
            newProperties.put("datanucleus.appengine.autoCreateDatastoreTxns", "true");
            newProperties.put("datanucleus.appengine.autoCreateDatastoreTxns", "true");
            pmf = JDOHelper.getPersistenceManagerFactory(newProperties);
        }
        return pmf;
    }

    /**
     * Gets an instance of the PersistenceManager class
     */
    public PersistenceManager getPersistenceManager() {
        return getPersistenceManagerFactory().getPersistenceManager();
    }

    /**
     * Return the list of TaskStateInfo instance added to the specified queue.
     *
     * Note: TaskOptions instances added to the queue have been transformed
     * into TaskStateInfo ones. Use the following accessors to verify that the
     * expected tasks have been added to the queue as expected.
     * - TaskStateInfo.getMethod(): GET/POST/PUT/DELETE
     * - TaskStateInfo.getUrl(): URL with request parameters if method == GET
     * -  TaskStateInfo.getBody(): Request parameters if method == POST/PUT
     *
     * @param queueName Name of the queue to query
     *
     * @return List of TaskStateInfo representing the processed TaskOptions
     * added to the specified queue
     */
    public List<TaskStateInfo> getTaskStateInfo(String queueName) {
        ApiProxyLocal proxy = (ApiProxyLocal) ApiProxy.getDelegate();
        if (proxy != null) {
            LocalTaskQueue taskQueue = (LocalTaskQueue) proxy.getService(LocalTaskQueue.PACKAGE);
            QueueStateInfo queueStateInfo = taskQueue.getQueueStateInfo().get(queueName);
            if (queueStateInfo != null) {
                return queueStateInfo.getTaskInfo();
            }
            return null;
        }
        return null;
    }
}
