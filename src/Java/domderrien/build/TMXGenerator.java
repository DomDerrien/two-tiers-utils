package domderrien.build;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TMXGenerator extends TMXCommandLineToolBase {

    /**
     * TMX Generator entry point: extract the parameters and process the retrieved TMX files.
     * @param args Usual arguments of a program
     */
    public static void main(String[] args) {
        TMXCommandLineToolBase application = new TMXGenerator(true);
        application.loadContext(args);
        application.processContext();
        application.processTMX();
    }

    protected final static String TMX_FILENAME_BASE_ARG = "-filenameBase";
    protected final static String TMX_DIRECTORY_NAME_ARG = "-directoryName";

    private String filenameBase = null;
    private String directoryName = null;

    /** Accessor for unit test */
    protected void setFilenameBase(String name) {
        filenameBase = name;
    }

    /** Accessor for unit test */
    protected void setDirectoryName(String name) {
        directoryName = name;
    }

    /**
     * Default constructor
     * @param isStandalone Specifies that any error should invoke "System.exit(1)" to report a
     *                     an application error code to the instanciator environment
     */
    public TMXGenerator(boolean isStandalone) {
        super(isStandalone);
    }

    @Override
    protected void displayUsage() {
        if (runStandalone) {
            System.out.println("TMX Generator usage:");
            System.out.print("TMXGenerator: ");
            System.out.print(TMX_FILENAME_BASE_ARG + " <filename> ");
            System.out.print(TMX_DIRECTORY_NAME_ARG + " <path> ");
            System.out.println(".");
        }
        stopProcess();
    }

    @Override
    public void loadContext(String[] args) {
        List<String> arguments = Arrays.asList(args);

        int filenameBaseIdx = arguments.indexOf(TMX_FILENAME_BASE_ARG);
        int directoryIdx = arguments.indexOf(TMX_DIRECTORY_NAME_ARG);

        if (filenameBaseIdx == -1 ||
            directoryIdx == -1) {
            displayUsage();
            return;
        }

        try {
            filenameBase = arguments.get(filenameBaseIdx + 1);
            directoryName = arguments.get(directoryIdx + 1);
        }
        catch(ArrayIndexOutOfBoundsException ex) {
            displayUsage();
            return;
        }

        if (filenameBase.length() == 0 ||
            directoryName.length() == 0) {
            displayUsage();
            return;
        }
    }

    @Override
    public void processContext() { }

    /**
     * TODO: document
     */
    public void processTMX() {
        Map<String, Long> fileDates = getFileDates(filenameBase, getFile(directoryName));
        Document sourceDoc = null;

        for (String filename : fileDates.keySet()) {
            if (!filenameBase.equals(filename)) {
                Long sourceDate = fileDates.get(filenameBase);
                Long targetDate = fileDates.get(filename);
                boolean processRequired = targetDate < sourceDate;

                if(processRequired) {
                    try {
                        long start = System.currentTimeMillis();
                        if (sourceDoc == null) {
                            System.out.print("running: TmxGenerator loading " + filenameBase + ".tmx"); //$NON-NLS-1$ //$NON-NLS-2$
                            InputStream sourceBIF = getInputStream(directoryName + File.separator + filenameBase + ".tmx");
                            sourceDoc = getDocument(sourceBIF);
                            sourceBIF.close();
                            System.out.print("\n"); //$NON-NLS-1$
                        }
                        System.out.print("running: TmxGenerator process " + filename); //$NON-NLS-1$
                        generate(sourceDoc, filename);
                        long end = System.currentTimeMillis();
                        System.out.print(" in " + (end - start) + " ms\n"); //$NON-NLS-1$ //$NON-NLS-2$
                    }
                    catch (IOException ex) {
                        // Returning non zero should stop the build
                        reportError(": [Exception: " + ex + "] ");
                        stopProcess();
                        return;
                    }
                }
            }
        }
    }

    protected final static String TMX_HEADER =
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
        "<!DOCTYPE tmx PUBLIC \"-//LISA OSCAR:1998//DTD for Translation Memory eXchange//EN\" \"http://www.lisa.org/tmx/tmx14.dtd\">\n" +
        "<tmx version=\"1.4\">\n" +
            "\t<header creationtool=\"domderrien.i18n\" creationtoolversion=\"1.0\"\n" +
                "\t\tdatatype=\"PlainText\" segtype=\"paragraph\" adminlang=\"en\" srclang=\"English\"\n" +
                "\t\to-tmf=\"domderrien\"\n" +
            "\t></header>\n" +
            "\t<body>\n";
    protected final static String TMX_FOOTER =
            "\t</body>\n" +
        "</tmx>";


    protected void generate(Document sourceDoc, String filename) throws IOException {
        InputStream targetBIF = getInputStream(directoryName + File.separator + filename + ".tmx");
        StringBuilder outBuffer = new StringBuilder();

        int underscoreIndex = filename.indexOf('_');
        String locale = filename.substring(underscoreIndex + 1);

        Document targetDoc = getDocument(targetBIF);
        outBuffer.append(TMX_HEADER);

        NodeList sourceNL = getNodeList(sourceDoc, "/tmx/body/tu"); //$NON-NLS-1$

        for (int i = 0; i < sourceNL.getLength(); i++) {
            generate((Element) sourceNL.item(i), targetDoc, outBuffer, locale);
        }

        outBuffer.append(TMX_FOOTER);

        targetBIF.close();

        OutputStream targetBOF = getOutputStream(directoryName + File.separator + filename + ".tmx");
        targetBOF.write(outBuffer.toString().getBytes(UTF8));
        targetBOF.close();
    }

    protected void generate(Element sourceTU, Document targetDoc, StringBuilder outBuffer, String locale) throws IOException {
        String id = sourceTU.getAttribute("tuid"); //$NON-NLS-1$
        if (id.length() == 0) {
            reportError("TMXConverter: Cannot find tuid."); //$NON-NLS-1$
            stopProcess();
            return;
        }

        outBuffer.append(("\t\t<tu tuid=\"" + id + "\" dataType=\"text\">\n"));

        int sourceVersion = 0;
        NodeList props = getNodeList(sourceTU, "prop"); //$NON-NLS-1$
        for (int j = 0; j < props.getLength(); j++) {
            Element prop = (Element)props.item(j);
            String type = prop.getAttribute("type"); //$NON-NLS-1$
            if ("x-version".equals(type)) { //$NON-NLS-1$
                sourceVersion = Integer.parseInt(prop.getFirstChild().getNodeValue());
            }
            else {
                outBuffer.append(("\t\t\t<prop type=\"" + type + "\">" + (prop.getFirstChild().getNodeValue()) + "</prop>\n"));
            }
        }
        if (sourceVersion == 0) {
            reportError("Missing <prop type='x-version'/> for <tu tuid='" + id + "'/>");
            stopProcess();
            return;
        }
        outBuffer.append(("\t\t\t<prop type=\"x-version\">" + sourceVersion + "</prop>\n"));

        int targetVersion = 0;
        NodeList targetNL = getNodeList(targetDoc, "/tmx/body/tu[@tuid='" + id + "']"); //$NON-NLS-1$
        if (0 < targetNL.getLength()) {
            NodeList targetProps = (getNodeList(targetNL.item(0), "prop[@type='x-version']"));
            if (0 < targetProps.getLength()) {
                targetVersion = Integer.parseInt(targetProps.item(0).getFirstChild().getNodeValue());
            }
        }

        String text = "";
        if (sourceVersion <= targetVersion) {
            // Keep the translated one
            Node targetSeg = getNodeList(targetNL.item(0), "tuv/seg/text()").item(0); //$NON-NLS-1$
            text = targetSeg == null ? null : targetSeg.getNodeValue();
        }
        else {
            // Override the translated one
            Node sourceSeg = getNodeList(sourceTU, "tuv/seg/text()").item(0); //$NON-NLS-1$
            text = sourceSeg == null ? null : sourceSeg.getNodeValue();
            text = escapeHtmlEntities(text);
        }

        outBuffer.append(("\t\t\t<tuv xml:lang=\"" + locale + "\">\n\t\t\t\t<seg>" + (text == null ? "" : text) + "</seg>\n\t\t\t</tuv>\n\t\t</tu>\n"));
    }

    protected static String escapeHtmlEntities(String source) {
        if (source == null) {
            return null;
        }
        return source.replaceAll("&&(\\s|$)", "&amp;&amp;$1").replaceAll("&(\\s|$)", "&amp;$1").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
