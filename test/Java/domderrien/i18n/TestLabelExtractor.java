package domderrien.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domderrien.i18n.LabelExtractor.ResourceFileId;

public class TestLabelExtractor {

    class MockResourceBundle extends ListResourceBundle {
        public final String LABEL_0 = "0";   //$NON-NLS-1$
        public final String LABEL_1 = "1";   //$NON-NLS-1$
        public final String LABEL_10 = "10"; //$NON-NLS-1$
        public final String LABEL_11 = "11"; //$NON-NLS-1$
        public final String LABEL_12 = "12"; //$NON-NLS-1$
        public final String ERR_LABEL_0 = LabelExtractor.ERROR_MESSAGE_PREFIX + "0"; //$NON-NLS-1$
        public final String ERR_LABEL_1 = LabelExtractor.ERROR_MESSAGE_PREFIX + "1"; //$NON-NLS-1$
        private Object[][] contents = new Object[][]{
            {LABEL_0, LABEL_0},
            {LABEL_1, LABEL_1},
            {LABEL_10, LABEL_0},
            {LABEL_11, LABEL_1},
            {ERR_LABEL_0, LABEL_0},
            {ERR_LABEL_1, LABEL_1}
        };
        protected Object[][] getContents() {
            return contents;
        }
    }

    MockResourceBundle mock = new MockResourceBundle();

    @Before
    public void setUp() throws Exception {
        LabelExtractor.resetResourceBundleList(ResourceFileId.master);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor() {
        new LabelExtractor();
    }

    @Test
    public void testSetResourceBundleI() {
        // Set a resource bundle with a null locale reference
        LabelExtractor.setResourceBundle(ResourceFileId.master, mock, null);
        assertEquals(mock, LabelExtractor.getResourceBundle(ResourceFileId.master, null));
    }

    @Test
    public void testSetResourceBundleII() {
        // Set a resource bundle with a non null locale reference
        LabelExtractor.setResourceBundle(ResourceFileId.master, mock, Locale.US);
        assertEquals(mock, LabelExtractor.getResourceBundle(ResourceFileId.master, Locale.US));
    }

    @Test
    public void testSetResourceBundleIII() {
        // Set a resource bundle with a composite locale reference
        LabelExtractor.setResourceBundle(ResourceFileId.master, mock, Locale.CANADA_FRENCH);
        assertEquals(mock, LabelExtractor.getResourceBundle(ResourceFileId.master, Locale.CANADA_FRENCH));
    }

    @Test
    public void testGetResourceBundleI() {
        // Expected to read a English file for the locale en_US => fallback on en
        ResourceBundle rb = LabelExtractor.getResourceBundle(ResourceFileId.master, Locale.US);
        assertEquals("English", rb.getString("bundle_language"));
    }

    @Test
    public void testGetResourceBundleII() {
        // Expected to read a English file for the locale fr => fr
        ResourceBundle rb = LabelExtractor.getResourceBundle(ResourceFileId.master, Locale.FRENCH);
        assertEquals("Fran\u00e7ais", rb.getString("bundle_language"));
    }

    @Test
    public void testGetResourceBundleIII() {
        // Expected to read a English file for the locale fr_CA => fr_CA
        ResourceBundle rb = LabelExtractor.getResourceBundle(ResourceFileId.master, Locale.CANADA_FRENCH);
        assertEquals("Fran\u00e7ais Canadien", rb.getString("bundle_language"));
    }

    @Test
    public void testGetResourceBundleIV() {
        // Expected to read a English file for the locale cn => fallback on root
        ResourceBundle rb = LabelExtractor.getResourceBundle(ResourceFileId.master, Locale.TRADITIONAL_CHINESE);
        assertEquals("English", rb.getString("bundle_language"));
    }

    @Test
    public void testGetResourceBundleV() {
        // Expected to read a English file for the locale fr_BE => fallback on fr
        ResourceBundle rb = LabelExtractor.getResourceBundle(ResourceFileId.master, new Locale("fr", "BE"));
        assertEquals("Fran\u00e7ais", rb.getString("bundle_language"));
    }

    @Test
    public void testGetResourceBundleVI() {
        // Expected to read a English file for the locale fr_BE => fallback on fr
        ResourceBundle rb = LabelExtractor.getResourceBundle(ResourceFileId.second, Locale.FRENCH);
        assertEquals("Fran\u00e7ais", rb.getString("bundle_language"));
    }

    @Test
    public void testGetResourceBundleVII() {
        // Expected to read a English file for the locale fr_BE => fallback on fr
        ResourceBundle rb = LabelExtractor.getResourceBundle(ResourceFileId.third, Locale.FRENCH);
        assertEquals("Fran\u00e7ais", rb.getString("bundle_language"));
    }

    @Test
    public void testGetResourceBundleVIIII() {
        // Expected to read a English file for the locale fr_BE => fallback on fr
        ResourceBundle rb = LabelExtractor.getResourceBundle(ResourceFileId.fourth, Locale.FRENCH);
        assertEquals("Fran\u00e7ais", rb.getString("bundle_language"));
    }

    @Test
    public void testGetI() {
        LabelExtractor.setResourceBundle(ResourceFileId.master, mock, Locale.US);
        // The label asked has an entry in the dictionary
        assertEquals(mock.getString(mock.LABEL_0), LabelExtractor.get(mock.LABEL_0, Locale.US));
        assertEquals(mock.getString(mock.LABEL_1), LabelExtractor.get(mock.LABEL_1, Locale.US));
        assertEquals(mock.getString(mock.LABEL_10), LabelExtractor.get(mock.LABEL_0, Locale.US));
        assertEquals(mock.getString(mock.LABEL_11), LabelExtractor.get(mock.LABEL_1, Locale.US));
    }

    @Test
    public void testGetII() {
        LabelExtractor.setResourceBundle(ResourceFileId.master, mock, Locale.US);
        // The label asked has NO entry in the dictionary, the key is returned
        try {
            mock.getString(mock.LABEL_12);
            fail("MissingResourceException expected");
        }
        catch (MissingResourceException ex0) {
            // Expected exception
        }
        catch (Exception ex1) {
            fail("No other exception expected");
        }
        assertEquals(mock.LABEL_12, LabelExtractor.get(mock.LABEL_12, Locale.US));
    }

    @Test
    public void testGetIII() {
        LabelExtractor.setResourceBundle(ResourceFileId.master, mock, Locale.US);
        // The label asked has an entry in the dictionary
        assertEquals(mock.getString(LabelExtractor.ERROR_MESSAGE_PREFIX + mock.LABEL_0), LabelExtractor.get(0, Locale.US));
        assertEquals(mock.getString(LabelExtractor.ERROR_MESSAGE_PREFIX + mock.LABEL_1), LabelExtractor.get(1, Locale.US));
    }

    @Test
    public void testGetIV() {
        LabelExtractor.setResourceBundle(ResourceFileId.master, mock, Locale.US);
        // The label returned is the given error code because the entry is not in the dictionary
        assertEquals("33", LabelExtractor.get(33, Locale.US));
    }

    @Test
    public void testGetVa() {
        LabelExtractor.setResourceBundle(ResourceFileId.master, mock, Locale.US);
        // The label returned is the given error code because the entry is not in the dictionary
        assertEquals("33", LabelExtractor.get(33, new Object[0], Locale.US));
    }

    @Test
    public void testGetVb() {
        LabelExtractor.setResourceBundle(ResourceFileId.master, mock, Locale.US);
        // The label returned is the given error code because the entry is not in the dictionary
        assertEquals("33", LabelExtractor.get("33", new HashMap<String, Object>(), Locale.US));
    }

    @Test
    public void testGetVI() {
        assertNull(LabelExtractor.get(null, Locale.US));
    }

    @Test
    public void testGetVII() {
        assertEquals("", LabelExtractor.get("", Locale.US));
    }

    @Test
    public void testInsertParametersI() {
        assertEquals("test0", LabelExtractor.insertParameters("{0}", new Object[] { "test0" } ));
        assertEquals("test1 - test0", LabelExtractor.insertParameters("{1} - {0}", new Object[] { "test0", "test1" } ));
        assertEquals("test1 - test0 - test1", LabelExtractor.insertParameters("{1} - {0} - {1}", new Object[] { "test0", "test1" } ));
    }

    @Test
    public void testInsertParametersII() {
        assertNull(LabelExtractor.get(null, new Object[] { "test0" }, Locale.US));
        assertEquals("", LabelExtractor.get("", new Object[] { "test0" }, Locale.US));
    }

    @Test
    public void testInsertParametersIII() {
        assertEquals("{1} - {0}", LabelExtractor.get("{1} - {0}", (Object[]) null, Locale.US));
    }

    @Test
    public void testInsertParametersIV() {
        assertEquals(LabelExtractor.NULL_INDICATOR, LabelExtractor.insertParameters("{0}", new Object[] { null } ));
        assertEquals(LabelExtractor.NULL_INDICATOR + " - test0", LabelExtractor.insertParameters("{1} - {0}", new Object[] { "test0", null } ));
        assertEquals(LabelExtractor.NULL_INDICATOR + " - test0 - " + LabelExtractor.NULL_INDICATOR, LabelExtractor.insertParameters("{1} - {0} - {1}", new Object[] { "test0", null } ));
    }

    @Test
    public void testGetResourceBundleExtendedI() {
        LabelExtractor.setResourceBundle(ResourceFileId.second, mock, Locale.US);
        // The label returned is the given error code because the entry is not in the dictionary
        assertNotNull(LabelExtractor.getResourceBundle(ResourceFileId.second, Locale.US));
    }

    @Test
    public void testGetResourceBundleExtendedII() {
        LabelExtractor.setResourceBundle(ResourceFileId.third, mock, Locale.US);
        // The label returned is the given error code because the entry is not in the dictionary
        assertNotNull(LabelExtractor.getResourceBundle(ResourceFileId.third, Locale.US));
    }

    @Test
    public void testGetResourceBundleExtendedIII() {
        LabelExtractor.setResourceBundle(ResourceFileId.fourth, mock, Locale.US);
        // The label returned is the given error code because the entry is not in the dictionary
        assertNotNull(LabelExtractor.getResourceBundle(ResourceFileId.fourth, Locale.US));
    }

    @Test
    public void testInsertParametersV() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("0", "test0");
        parameters.put("1", "test1");
        assertEquals("test0", LabelExtractor.insertParameters("{0}", parameters ));
        assertEquals("test1 - test0", LabelExtractor.insertParameters("{1} - {0}", parameters ));
        assertEquals("test1 - test0 - test1", LabelExtractor.insertParameters("{1} - {0} - {1}", parameters ));

    }

    @Test
    public void testInsertParametersVI() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("0", "test0");
        assertNull(LabelExtractor.get(null, parameters, Locale.US));
        assertEquals("", LabelExtractor.get("", parameters, Locale.US));
    }

    @Test
    public void testInsertParametersVII() {
        assertEquals("{1} - {0}", LabelExtractor.get("{1} - {0}", (Map<String, Object>) null, Locale.US));
    }

    @Test
    public void testInsertParametersVIII() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("0", "test0");
        parameters.put("1", null);
        assertEquals("test0" + LabelExtractor.NULL_INDICATOR, LabelExtractor.insertParameters("{0}{1}", parameters ));
    }

    @Test
    public void testWithFallbackI() {
        String fakeId = "000";
        assertEquals(fakeId, LabelExtractor.get(fakeId, fakeId, Locale.US)); // Return the given identifier
    }

    @Test
    public void testWithFallbackII() {
        String fakeId = "000";
        assertEquals(mock.LABEL_1, LabelExtractor.get(fakeId, mock.LABEL_1, Locale.US)); // Return the label for the second identifier
    }

    @Test
    public void testWithFallbackIII() {
        String fakeId = "000";
        String secondId = "321432";
        assertEquals(secondId, LabelExtractor.get(fakeId, secondId, Locale.US)); // Return the second identifier
    }

    @Test
    public void testWithFallbackIV() {
        String fakeId = "000";
        assertNull(LabelExtractor.get(fakeId, (String) null, Locale.US)); // Return <code>null</code> instead of throwing the exception MissingResourceException
    }

    @Test
    public void testWithFallbackV() {
        String fakeId = "000";
        assertEquals(fakeId, LabelExtractor.get(fakeId, fakeId, (Object[]) null, Locale.US)); // Return the given identifier
    }

    @Test
    public void testWithFallbackVI() {
        String fakeId = "000";
        assertEquals(mock.LABEL_1, LabelExtractor.get(fakeId, mock.LABEL_1, (Object[]) null, Locale.US)); // Return the label for the second identifier
    }

    @Test
    public void testWithFallbackVII() {
        String fakeId = "000";
        String secondId = "321432";
        assertEquals(secondId, LabelExtractor.get(fakeId, secondId, (Object[]) null, Locale.US)); // Return the second identifier
    }

    @Test
    public void testWithFallbackVIII() {
        String fakeId = "000";
        assertNull(LabelExtractor.get(fakeId, (String) null, (Object[]) null, Locale.US)); // Return <code>null</code> instead of throwing the exception MissingResourceException
    }

    @Test
    public void testGetExtendedI() {
        assertEquals(mock.LABEL_0, LabelExtractor.get(ResourceFileId.master, mock.LABEL_0, Locale.US));
    }

    @Test
    public void testGetExtendedII() {
        assertEquals(mock.LABEL_0, LabelExtractor.get(ResourceFileId.master, mock.LABEL_0, (Map<String, Object>) null, Locale.US));
    }
}
