package javax.jdo;

import java.util.Collection;
import java.util.Properties;
import java.util.Set;

import javax.jdo.datastore.DataStoreCache;
import javax.jdo.listener.InstanceLifecycleListener;
import javax.jdo.metadata.JDOMetadata;
import javax.jdo.metadata.TypeMetadata;

@SuppressWarnings("serial")
public class MockPersistenceManagerFactory implements PersistenceManagerFactory {

    public void addFetchGroups(FetchGroup... arg0) {}
    @SuppressWarnings("unchecked") public void addInstanceLifecycleListener(InstanceLifecycleListener arg0, Class[] arg1) {}
    public void close() {}
    public String getConnectionDriverName() { return null; }
    public Object getConnectionFactory() { return null; }
    public Object getConnectionFactory2() { return null; }
    public String getConnectionFactory2Name() { return null; }
    public String getConnectionFactoryName() { return null; }
    public String getConnectionURL() { return null; }
    public String getConnectionUserName() { return null; }
    public boolean getCopyOnAttach() { return false; }
    public DataStoreCache getDataStoreCache() { return null; }
    public boolean getDetachAllOnCommit() { return false; }
    @SuppressWarnings("unchecked") public FetchGroup getFetchGroup(Class arg0, String arg1) { return null; }
    @SuppressWarnings("unchecked") public Set getFetchGroups() { return null; }
    public boolean getIgnoreCache() { return false; }
    public String getMapping() { return null; }
    public TypeMetadata getMetadata(String arg0) { return null; }
    public boolean getMultithreaded() { return false; }
    public String getName() { return null; }
    public boolean getNontransactionalRead() { return false; }
    public boolean getNontransactionalWrite() { return false; }
    public boolean getOptimistic() { return false; }

    public PersistenceManager getPersistenceManager() {
        Properties newProperties = new Properties();
        newProperties.put("javax.jdo.PersistenceManagerFactoryClass",
                "org.datanucleus.store.appengine.jdo.DatastoreJDOPersistenceManagerFactory");
        newProperties.put("javax.jdo.option.ConnectionURL", "appengine");
        newProperties.put("javax.jdo.option.NontransactionalRead", "true");
        newProperties.put("javax.jdo.option.NontransactionalWrite", "true");
        newProperties.put("javax.jdo.option.RetainValues", "true");
        newProperties.put("datanucleus.appengine.autoCreateDatastoreTxns", "true");
        newProperties.put("datanucleus.appengine.autoCreateDatastoreTxns", "true");

        return JDOHelper.getPersistenceManagerFactory(newProperties).getPersistenceManager();
    }

    public PersistenceManager getPersistenceManager(String arg0, String arg1) {
        return getPersistenceManager();
    }

    public PersistenceManager getPersistenceManagerProxy() { return null; }
    public String getPersistenceUnitName() { return null; }
    public Properties getProperties() { return null; }
    public Integer getQueryTimeoutMillis() { return null; }
    public boolean getReadOnly() { return false; }
    public boolean getRestoreValues() { return false; }
    public boolean getRetainValues() { return false; }
    public String getServerTimeZoneID() { return null; }
    public String getTransactionIsolationLevel() { return null; }
    public String getTransactionType() { return null; }
    public boolean isClosed() { return false; }
    public JDOMetadata newMetadata() { return null; }
    public void registerMetadata(JDOMetadata arg0) {}
    public void removeAllFetchGroups() {}
    public void removeFetchGroups(FetchGroup... arg0) {}
    public void removeInstanceLifecycleListener(InstanceLifecycleListener arg0) {}
    public void setConnectionDriverName(String arg0) {}
    public void setConnectionFactory(Object arg0) {}
    public void setConnectionFactory2(Object arg0) {}
    public void setConnectionFactory2Name(String arg0) {}
    public void setConnectionFactoryName(String arg0) {}
    public void setConnectionPassword(String arg0) {}
    public void setConnectionURL(String arg0) {}
    public void setConnectionUserName(String arg0) {}
    public void setCopyOnAttach(boolean arg0) {}
    public void setDetachAllOnCommit(boolean arg0) {}
    public void setIgnoreCache(boolean arg0) {}
    public void setMapping(String arg0) {}
    public void setMultithreaded(boolean arg0) {}
    public void setName(String arg0) {}
    public void setNontransactionalRead(boolean arg0) {}
    public void setNontransactionalWrite(boolean arg0) {}
    public void setOptimistic(boolean arg0) {}
    public void setPersistenceUnitName(String arg0) {}
    public void setQueryTimeoutMillis(Integer arg0) {}
    public void setReadOnly(boolean arg0) {}
    public void setRestoreValues(boolean arg0) {}
    public void setRetainValues(boolean arg0) {}
    public void setServerTimeZoneID(String arg0) {}
    public void setTransactionIsolationLevel(String arg0) {}
    public void setTransactionType(String arg0) {}
    public Collection<String> supportedOptions() { return null;}
}
