package domderrien.build;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class TMXCommandLineToolBase {

    protected final static String UTF8 = "utf-8";

    /**
     * Default constructor
     *
     * @param isStandalone Specifies that any error should invoke "System.exit(1)"
     * to report an application error code to the instanciator environment
     */
    public TMXCommandLineToolBase(boolean isStandalone) {
        runStandalone = isStandalone;
    }

    /**
     * Just display how the class should be invoked from the command line
     */
    protected abstract void displayUsage();

    /**
     * Set the class instance variables with data extracted from the command line arguments
     *
     * @param args Usual command line argument list
     * @throws NullPointerException if the given parameter is <code>null</code>
     */
    public abstract void loadContext(String[] args);

    /**
     * Process the contextual information
     */
    public abstract void processContext();

    /**
     * Process the TMX content
     */
    public abstract void processTMX();

    /**
     * Report an error code to the application instaniator if it works
     * in standalone mode.
     *
     */
    protected void stopProcess() {
        processStopped = true;
        if (runStandalone) {
            // To make the standalone program
            // failing the build process
            System.exit(1);
        }
    }

    /**
     * Report an error message.
     *
     */
    protected void reportError(String message) {
        errorReported = true;
        if (runStandalone) {
            System.out.println(message);
        }
    }

    protected boolean runStandalone = false;
    protected boolean processStopped = false;
    protected boolean errorReported = false;

    /** Accessor */
    protected boolean getProcessStopped() {
        return processStopped;
    }

    /** Accessor */
    protected boolean isErrorReported() {
        return errorReported;
    }

    /**
     * Extract the name of the files contained in the given directory and their
     * last-modified date.
     *
     * @param filenameBase Base name of the TMX files
     * @param directory File instance to study (can be relative or absolute).
     * @return Map with extracted file dates
     */
    protected Map<String, Long> getFileDates(String filenameBase, File directory) {
        if (directory.isDirectory()) {
            File[] file = directory.listFiles();
            Map<String, Long> fileDates = new HashMap<String, Long>();
            for (int i = 0; i <file.length; i++) {
                File containedFile = file[i];
                if (containedFile.isFile()) {
                    String filename = containedFile.getName();
                    if (filename.startsWith(filenameBase)) {
                        int dotIndex = filename.indexOf('.');
                        String localizedFilenameBase = dotIndex == -1 ? filename : filename.substring(0, filename.indexOf('.'));
                        fileDates.put(localizedFilenameBase, containedFile.lastModified());
                    }
                }
            }
            return fileDates;
        }
        return null;
    }

    // Helper
    protected File getFile(String filename) {
        return new File(filename);
    }

    // Helper
    protected InputStream getInputStream(String filename) throws FileNotFoundException {
        return new BufferedInputStream(
            new FileInputStream(filename)
        );
    }

    // Helper
    protected OutputStream getOutputStream(String filename) throws FileNotFoundException {
        return  new BufferedOutputStream(
            new FileOutputStream(filename, false)
        );
    }

    /**
     * Nop entity resolver to avoid accessing an external DTD.
     * Without this, you need to be online when you run the converter.
     */
    protected EntityResolver getEntityResolver() {
        return new EntityResolver() {
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                return new InputSource(new StringReader(""));
            }
        };
    }

    private DocumentBuilder parser = null;

    protected DocumentBuilderFactory getFactory() {
        return DocumentBuilderFactory.newInstance();
    }

    protected DocumentBuilder getParser() {
        if (parser == null) {
            DocumentBuilderFactory factory = getFactory();
            factory.setValidating(false);

            parser = null;
            try {
                parser = factory.newDocumentBuilder();
            }
            catch (ParserConfigurationException ex) {
                reportError("Cannot instanciate the DOM parser [Exception: " + ex + "]");
                stopProcess();
                return null;
            }
            parser.setEntityResolver(getEntityResolver());
        }

        return parser;
    }

    protected Document getDocument(InputStream sourceIS) {
        try {
            return getParser().parse(sourceIS);
        }
        catch (SAXException ex) {
            reportError(" [Exception: " + ex + "] ");
            stopProcess();
        }
        catch (IOException ex) {
            reportError(" [Exception: " + ex + "] ");
            stopProcess();
        }
        return null;
    }

    protected NodeList getNodeList(Node elem, String path) {
        try {
            return XPathAPI.selectNodeList(elem, path); //$NON-NLS-1$
        }
        catch (TransformerException ex) {
            reportError(" [Exception: " + ex + "] ");
            stopProcess();
        }
        return null;
    }

    protected StringBuilder getTextRepresentation (StringBuilder out, NodeList nodeList) {
        for (int idx = 0; idx < nodeList.getLength(); idx++) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                out.append("<").append(node.getNodeName());
                NamedNodeMap attributes = node.getAttributes();
                for(int crsr = 0; crsr < attributes.getLength(); crsr++) {
                    out.append(" ").append(attributes.item(crsr).getNodeName()).append("=\"").append(attributes.item(crsr).getNodeValue()).append("\"");
                }
                if (hasNoChild(node.getNodeName())) {
                    out.append(" />");
                }
                else {
                    out.append(">");
                    getTextRepresentation (out, node.getChildNodes());
                    out.append("</").append(node.getNodeName()).append(">");
                }
            }
            else if (node.getNodeType() == Node.TEXT_NODE) {
                out.append(node.getNodeValue());
            }
            else {
                reportError("TMXCommandLineToolBase: Unexpected tag content: " + node.getNodeName()); //$NON-NLS-1$
            }
        }
        return out;
    }

    protected boolean hasNoChild(String nodeName) {
        nodeName = nodeName.toLowerCase();
        if ("br".equals(nodeName)) return true;
        if ("input".equals(nodeName)) return true;
        return false;
    }
}
