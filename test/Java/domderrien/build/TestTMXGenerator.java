package domderrien.build;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javamocks.io.MockInputStream;
import javamocks.io.MockOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestTMXGenerator {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMain() {
        // Cannot be unit tested because it will
        // make the application calling "System.exit(1)"
        // and this will break the automatic unit tests processing
    }

    @Test
    public void testStopProcess() {
        // Just verify the correct call
        // Note that the entire method paths can't be converted
        // because of the call to "System.exit(1)"
        TMXGenerator generator = new TMXGenerator(false);
        generator.stopProcess();
        assertTrue(generator.getProcessStopped());
    }

    @Test
    public void testErrorReport() {
        TMXGenerator generator = new TMXGenerator(true);
        generator.reportError("bla-bla");
        assertTrue(generator.isErrorReported());
        assertFalse(generator.getProcessStopped());
    }

    @Test
    public void testDisplayUsage() {
        // Just verify that no exception is thrown
        TMXGenerator generator = new TMXGenerator(true) {
            protected void stopProcess() {
                // Nothing
            }
        };
        generator.displayUsage();
    }

    @Test(expected = NullPointerException.class)
    public void testSetContextI() {
        // Exception expected because the <code>null</code> value is not checked
        TMXGenerator generator = new TMXGenerator(false);
        generator.loadContext(null);
        fail("NullPointerException should have been thrown");
    }

    @Test
    public void testSetContextII() {
        // Error because the number of arguments is even
        TMXGenerator generator = new TMXGenerator(false);
        generator.loadContext(new String[] {"nop"});
        assertTrue(generator.getProcessStopped());
    }

    @Test
    public void testSetContextIII() {
        // Error because the number of arguments is odd but under 10
        TMXGenerator generator = new TMXGenerator(false);
        generator.loadContext(new String[] {"nop", "nop"});
        assertTrue(generator.getProcessStopped());
    }

    @Test
    public void testSetContextIV() {
        // Error because one of the expected arguments is missing
        {
            TMXGenerator generator = new TMXGenerator(false);
            generator.loadContext(new String[] {
                    "-nop", "nop",
                    TMXGenerator.TMX_DIRECTORY_NAME_ARG, "nop"
            });
            assertTrue(generator.getProcessStopped());
        }
        {
            TMXGenerator generator = new TMXGenerator(false);
            generator.loadContext(new String[] {
                    TMXGenerator.TMX_FILENAME_BASE_ARG, "nop",
                    "-nop", "nop"
            });
            assertTrue(generator.getProcessStopped());
        }
    }

    @Test
    public void testloadContextV() {
        // Error because one of the value of the expected arguments is emtpy
        {
            TMXGenerator generator = new TMXGenerator(false);
            generator.loadContext(new String[] {
                    TMXGenerator.TMX_FILENAME_BASE_ARG, "",
                    TMXGenerator.TMX_DIRECTORY_NAME_ARG, "nop"
            });
            assertTrue(generator.getProcessStopped());
        }
        {
            TMXGenerator generator = new TMXGenerator(false);
            generator.loadContext(new String[] {
                    TMXGenerator.TMX_FILENAME_BASE_ARG, "nop",
                    TMXGenerator.TMX_DIRECTORY_NAME_ARG, ""
            });
            assertTrue(generator.getProcessStopped());
        }
    }

    @Test
    public void testSetContextVI() {
        // Error because one of the value of the expected arguments is missing
        TMXGenerator generator = new TMXGenerator(false);
        generator.loadContext(new String[] {
                TMXGenerator.TMX_FILENAME_BASE_ARG,
                TMXGenerator.TMX_DIRECTORY_NAME_ARG
        });
        assertTrue(generator.getProcessStopped());
    }

    @Test
    public void testSetContextVII() {
        // Everything is fine, so no error expected
        TMXGenerator generator = new TMXGenerator(false);
        generator.loadContext(new String[] {
                TMXGenerator.TMX_FILENAME_BASE_ARG, "nop",
                TMXGenerator.TMX_DIRECTORY_NAME_ARG, "nop"
        });
        assertFalse(generator.getProcessStopped());
    }

    @Test
    public void testProcessContext() {
        // Everything is fine, so no error expected
        TMXGenerator generator = new TMXGenerator(false);
        generator.processContext();
        assertFalse(generator.getProcessStopped());
    }

    @Test
    public void testProcessTMXI()
    {
        // Verify that the sequences will old dates are correctly processed
        final Map<String, Long> sourceFileDates = new HashMap<String, Long>();
        sourceFileDates.put("tmx", Long.valueOf(111));
        sourceFileDates.put("tmx_fr", Long.valueOf(222));

        TMXGenerator generator = new TMXGenerator(false) {
            @Override
            @SuppressWarnings("serial")
            protected File getFile(String filename)
            {
                assertEquals(".", filename);
                return new File("nop") {
                    @Override
                    public boolean  isDirectory() {
                        return false;
                    }
                };
            }
            @Override
            protected Map<String, Long> getFileDates(String filenameBase, File directory) {
                assertEquals("tmx", filenameBase);
                assertEquals("nop", directory.getName());
                return sourceFileDates;
            }
        };

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        generator.processTMX();

        assertFalse(generator.getProcessStopped());
    }

    @Test
    public void testProcessTMXII()
    {
        // Verify the exception handling if the conversion process fails
        final Map<String, Long> sourceFileDates = new HashMap<String, Long>();
        sourceFileDates.put("tmx", Long.valueOf(222));
        sourceFileDates.put("tmx_fr", Long.valueOf(111));

        TMXGenerator generator = new TMXGenerator(false) {
            @Override
            @SuppressWarnings("serial")
            protected File getFile(String filename)
            {
                assertEquals(".", filename);
                return new File("nop") {
                    @Override
                    public boolean  isDirectory() {
                        return false;
                    }
                };
            }
            @Override
            protected Map<String, Long> getFileDates(String filenameBase, File directory) {
                assertEquals("tmx", filenameBase);
                assertEquals("nop", directory.getName());
                return sourceFileDates;
            }
        };

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        generator.processTMX();

        assertTrue(generator.getProcessStopped());
    }

    @Test
    public void testProcessTMXIII()
    {
        // Verify the exception handling if the conversion process fails
        final Map<String, Long> sourceFileDates = new HashMap<String, Long>();
        sourceFileDates.put("tmx", Long.valueOf(222));
        sourceFileDates.put("tmx_fr", Long.valueOf(111));
        sourceFileDates.put("tmx_fr_CA", Long.valueOf(111));

        TMXGenerator generator = new TMXGenerator(false) {
            @Override
            @SuppressWarnings("serial")
            protected File getFile(String filename)
            {
                assertEquals(".", filename);
                return new File("nop") {
                    @Override
                    public boolean  isDirectory() {
                        return false;
                    }
                };
            }
            @Override
            protected Map<String, Long> getFileDates(String filenameBase, File directory) {
                assertEquals("tmx", filenameBase);
                assertEquals("nop", directory.getName());
                return sourceFileDates;
            }
            @Override
            protected InputStream getInputStream(String filename) {
                return new MockInputStream(TMXGenerator.TMX_HEADER + TMXGenerator.TMX_FOOTER);
            }
            @Override
            protected void generate(Document sourceDoc, String filename) throws IOException {
            }
        };

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        generator.processTMX();

        assertFalse(generator.getProcessStopped());
    }

    @Test
    public void testGenerateI() throws IOException {
        final String element = "<tu><tuv><seg>test</seg></tuv></tu>";
        final MockOutputStream outStream = new MockOutputStream();

        TMXGenerator generator = new TMXGenerator(false) {
            @Override
            protected InputStream getInputStream(String filename) {
                return new MockInputStream(TMXGenerator.TMX_HEADER + TMXGenerator.TMX_FOOTER);
            }
            @Override
            protected OutputStream getOutputStream(String filename) {
                return outStream;
            }
            @Override
            protected void generate(Element sourceTU, Document targetDoc, StringBuilder outBuffer, String locale) throws IOException {
            }
        };

        Document sourceDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + element + TMXGenerator.TMX_FOOTER));

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        generator.generate(sourceDoc, "tmx_fr");

        assertNotSame(0, outStream.getStream().length());
        assertFalse(generator.getProcessStopped());
    }

    @Test
    public void testGenerateII() throws IOException {
        final String element = "<tu><tuv><seg>test</seg></tuv></tu>";

        TMXGenerator generator = new TMXGenerator(false);

        Document sourceDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + element + TMXGenerator.TMX_FOOTER));
        Element sourceTU = (Element) generator.getNodeList(sourceDoc, "tmx/body/tu").item(0);

        Document targetDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + element + TMXGenerator.TMX_FOOTER));

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        StringBuilder outBuffer = new StringBuilder();
        generator.generate(sourceTU, targetDoc, outBuffer, "fr");

        assertEquals(0, outBuffer.length());
        assertTrue(generator.isErrorReported());
        assertTrue(generator.getProcessStopped());
    }

    @Test
    public void testGenerateIII() throws IOException {
        TMXGenerator generator = new TMXGenerator(false);

        final String sourceElement = "<tu tuid=\"test\" datatype=\"text\"></tu>";
        Document sourceDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + sourceElement + TMXGenerator.TMX_FOOTER));
        Element sourceTU = (Element) generator.getNodeList(sourceDoc, "tmx/body/tu").item(0);

        Document targetDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + TMXGenerator.TMX_FOOTER));

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        StringBuilder outBuffer = new StringBuilder();
        generator.generate(sourceTU, targetDoc, outBuffer, "fr");

        assertNotSame(0, outBuffer.length());
        assertTrue(generator.isErrorReported());
        assertTrue(generator.getProcessStopped());
    }

    @Test
    public void testGenerateIV() throws IOException {
        TMXGenerator generator = new TMXGenerator(false);

        final String sourceElement = "<tu tuid=\"test\" datatype=\"text\"><prop type=\"test\">test</prop></tu>";
        Document sourceDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + sourceElement + TMXGenerator.TMX_FOOTER));
        Element sourceTU = (Element) generator.getNodeList(sourceDoc, "tmx/body/tu").item(0);

        Document targetDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + TMXGenerator.TMX_FOOTER));

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        StringBuilder outBuffer = new StringBuilder();
        generator.generate(sourceTU, targetDoc, outBuffer, "fr");

        assertNotSame(0, outBuffer.length());
        assertTrue(generator.isErrorReported());
        assertTrue(generator.getProcessStopped());
    }

    @Test
    public void testGenerateV() throws IOException {
        TMXGenerator generator = new TMXGenerator(false);

        final String sourceElement = "<tu tuid=\"test\" datatype=\"text\"><prop type=\"x-version\">12</prop><tuv xml:lang=\"en\"><seg>test</seg></tuv></tu>";
        Document sourceDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + sourceElement + TMXGenerator.TMX_FOOTER));
        Element sourceTU = (Element) generator.getNodeList(sourceDoc, "tmx/body/tu").item(0);

        Document targetDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + TMXGenerator.TMX_FOOTER));

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        StringBuilder outBuffer = new StringBuilder();
        generator.generate(sourceTU, targetDoc, outBuffer, "fr");

        assertNotSame(0, outBuffer.length());
        assertFalse(generator.isErrorReported());
        assertFalse(generator.getProcessStopped());
    }

    @Test
    public void testGenerateVI() throws IOException {
        TMXGenerator generator = new TMXGenerator(false);

        final String sourceElement = "<tu tuid=\"test\" datatype=\"text\"><prop type=\"x-version\">12</prop><tuv xml:lang=\"en\"><seg>test</seg></tuv></tu>";
        Document sourceDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + sourceElement + TMXGenerator.TMX_FOOTER));
        Element sourceTU = (Element) generator.getNodeList(sourceDoc, "tmx/body/tu").item(0);

        final String targetElement = "<tu tuid=\"test\" datatype=\"text\"><tuv xml:lang=\"fr\"><seg>test</seg></tuv></tu>";
        Document targetDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + targetElement + TMXGenerator.TMX_FOOTER));

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        StringBuilder outBuffer = new StringBuilder();
        generator.generate(sourceTU, targetDoc, outBuffer, "fr");

        assertNotSame(0, outBuffer.length());
        assertFalse(generator.isErrorReported());
        assertFalse(generator.getProcessStopped());
    }

    @Test
    public void testGenerateVII() throws IOException {
        TMXGenerator generator = new TMXGenerator(false);

        final String sourceElement = "<tu tuid=\"test\" datatype=\"text\"><prop type=\"x-version\">12</prop><tuv xml:lang=\"en\"><seg>test</seg></tuv></tu>";
        Document sourceDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + sourceElement + TMXGenerator.TMX_FOOTER));
        Element sourceTU = (Element) generator.getNodeList(sourceDoc, "tmx/body/tu").item(0);

        final String targetElement = "<tu tuid=\"test\" datatype=\"text\"><prop type=\"x-version\">11</prop><tuv xml:lang=\"fr\"><seg>test</seg></tuv></tu>";
        Document targetDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + targetElement + TMXGenerator.TMX_FOOTER));

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        StringBuilder outBuffer = new StringBuilder();
        generator.generate(sourceTU, targetDoc, outBuffer, "fr");

        assertNotSame(0, outBuffer.length());
        assertFalse(generator.isErrorReported());
        assertFalse(generator.getProcessStopped());
    }

    @Test
    public void testGenerateVIII() throws IOException {
        TMXGenerator generator = new TMXGenerator(false);

        final String sourceElement = "<tu tuid=\"test\" datatype=\"text\"><prop type=\"x-version\">12</prop><tuv xml:lang=\"en\"><seg>test</seg></tuv></tu>";
        Document sourceDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + sourceElement + TMXGenerator.TMX_FOOTER));
        Element sourceTU = (Element) generator.getNodeList(sourceDoc, "tmx/body/tu").item(0);

        final String targetElement = "<tu tuid=\"test\" datatype=\"text\"><prop type=\"x-version\">12</prop><tuv xml:lang=\"fr\"><seg>test</seg></tuv></tu>";
        Document targetDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + targetElement + TMXGenerator.TMX_FOOTER));

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        StringBuilder outBuffer = new StringBuilder();
        generator.generate(sourceTU, targetDoc, outBuffer, "fr");

        assertNotSame(0, outBuffer.length());
        assertFalse(generator.isErrorReported());
        assertFalse(generator.getProcessStopped());
    }

    @Test
    public void testGenerateIX() throws IOException {
        TMXGenerator generator = new TMXGenerator(false);

        final String sourceElement = "<tu tuid=\"test\" datatype=\"text\"><prop type=\"x-version\">12</prop><tuv xml:lang=\"en\"><seg></seg></tuv></tu>";
        Document sourceDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + sourceElement + TMXGenerator.TMX_FOOTER));
        Element sourceTU = (Element) generator.getNodeList(sourceDoc, "tmx/body/tu").item(0);

        final String targetElement = "<tu tuid=\"test\" datatype=\"text\"><prop type=\"x-version\">11</prop><tuv xml:lang=\"fr\"><seg>test</seg></tuv></tu>";
        Document targetDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + targetElement + TMXGenerator.TMX_FOOTER));

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        StringBuilder outBuffer = new StringBuilder();
        generator.generate(sourceTU, targetDoc, outBuffer, "fr");

        assertNotSame(0, outBuffer.length());
        assertFalse(generator.isErrorReported());
        assertFalse(generator.getProcessStopped());
    }

    @Test
    public void testGenerateX() throws IOException {
        TMXGenerator generator = new TMXGenerator(false);

        final String sourceElement = "<tu tuid=\"test\" datatype=\"text\"><prop type=\"x-version\">12</prop><tuv xml:lang=\"en\"><seg></seg></tuv></tu>";
        Document sourceDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + sourceElement + TMXGenerator.TMX_FOOTER));
        Element sourceTU = (Element) generator.getNodeList(sourceDoc, "tmx/body/tu").item(0);

        final String targetElement = "<tu tuid=\"test\" datatype=\"text\"><prop type=\"x-version\">12</prop><tuv xml:lang=\"fr\"><seg></seg></tuv></tu>";
        Document targetDoc = generator.getDocument(new MockInputStream(TMXGenerator.TMX_HEADER + targetElement + TMXGenerator.TMX_FOOTER));

        generator.setFilenameBase("tmx");
        generator.setDirectoryName(".");
        StringBuilder outBuffer = new StringBuilder();
        generator.generate(sourceTU, targetDoc, outBuffer, "fr");

        assertNotSame(0, outBuffer.length());
        assertFalse(generator.isErrorReported());
        assertFalse(generator.getProcessStopped());
    }
}
