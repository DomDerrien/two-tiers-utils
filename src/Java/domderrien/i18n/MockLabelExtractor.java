package domderrien.i18n;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class MockLabelExtractor extends LabelExtractor {

    static class MockResourceBundle extends ListResourceBundle {
        private Object[][] contents;
        public MockResourceBundle(Object[][] bundleContent) {
            contents = bundleContent;
        }
        @Override
        protected Object[][] getContents() {
            return contents;
        }
    }

    private static ResourceBundle originalRB;

    public static void setup(ResourceFileId fileId, Object [][] bundleContent, Locale locale) {
        originalRB = LabelExtractor.setResourceBundle(fileId, new MockResourceBundle(bundleContent), locale);
    }

    public static void cleanup(ResourceFileId fileId, Locale locale) {
        LabelExtractor.setResourceBundle(fileId, originalRB, locale);
    }
}
