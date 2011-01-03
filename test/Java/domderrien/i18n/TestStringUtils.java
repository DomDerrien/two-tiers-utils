package domderrien.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javamocks.io.MockInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestStringUtils {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testNewStringUtils() {
        new StringUtils();
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConvertUnicodeCharI() {
        StringUtils.convertUnicodeChar(""); // Too short
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConvertUnicodeCharII() {
        StringUtils.convertUnicodeChar("abcdefghijk..."); // Too long
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConvertUnicodeCharIIIa() {
        StringUtils.convertUnicodeChar("!!!!"); // Invalid characters before '0'
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConvertUnicodeCharIIIb() {
        StringUtils.convertUnicodeChar("===="); // Invalid characters between '0' and 'A'
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConvertUnicodeCharIIIc() {
        StringUtils.convertUnicodeChar("ZZZZ"); // Invalid characters between 'F' and 'a'
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConvertUnicodeCharIIId() {
        StringUtils.convertUnicodeChar("zzzz"); // Invalid characters after 'f'
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConvertUnicodeCharIVa() {
        StringUtils.convertUnicodeChar("\\uzzzz"); // Invalid characters
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConvertUnicodeCharIVb() {
        StringUtils.convertUnicodeChar("^u0020"); // Invalid escape sequence
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConvertUnicodeCharIVc() {
        StringUtils.convertUnicodeChar("\\n0020"); // Invalid escape sequence
    }

    @Test
    public void testConvertUnicodeCharV() {
        assertEquals(' ', StringUtils.convertUnicodeChar("\\u0020"));
    }

    @Test
    public void testConvertUnicodeCharVI() {
        assertEquals('\n', StringUtils.convertUnicodeChar("\\u000a"));
        assertEquals('\n', StringUtils.convertUnicodeChar("\\u000A"));
        assertEquals('\uffff', StringUtils.convertUnicodeChar("\\uffff"));
    }

    @Test
    public void testConvertUnicodeChars() {
        assertEquals("a b\nc", StringUtils.convertUnicodeChars("a\\u0020b\\u000ac"));
    }

    @Test
    public void testGetString() throws IOException {
        String data = "abcdefghijklmnopqrstuvwxyz";
        assertEquals(data, StringUtils.getString(new MockInputStream(data), StringUtils.JAVA_UTF8_CHARSET, 1024)); // Large enough buffer
        assertEquals(data, StringUtils.getString(new MockInputStream(data), StringUtils.JAVA_UTF8_CHARSET, 8)); // Buffer to be increased incrementally
    }

    @Test
    public void testToUnicode() throws UnsupportedEncodingException {
        byte[] utf8Bytes = new byte[] {
                    (byte) 0x00c3, (byte) 0x00a0, // à
                    (byte) 0x00c3, (byte) 0x00a9, // é
                    (byte) 0x00c3, (byte) 0x00b4, // ô
                    (byte) 0x00c3, (byte) 0x00bc, // ü
                    (byte) 0x00c3, (byte) 0x0087, // Ç
                    (byte) 0x00e2, (byte) 0x0082, (byte) 0x00ac // €
                };
        String unicodeStr = StringUtils.toUnicode(utf8Bytes);
        assertEquals(5 + 1, unicodeStr.length());
        assertEquals('\u00e0', unicodeStr.charAt(0)); // unicode à
        assertEquals('\u00e9', unicodeStr.charAt(1)); // unicode é
    }

    @Test
    public void testToUTF8() throws UnsupportedEncodingException {
        String unicodeStr = "\u00e0\u00e9\u00f4\u00fc\u00c7\u20ac"; // "àéôüÇ€"
        byte[] utf8Buffer = StringUtils.toUTF8(unicodeStr);
        assertEquals(2 * 5 + 3 * 1, utf8Buffer.length);
        assertEquals((byte) '\u00c3', utf8Buffer[0]); // utf-8  first byte of à
        assertEquals((byte) '\u00a0', utf8Buffer[1]); // utf-8 second byte of à
        assertEquals((byte) '\u00c3', utf8Buffer[2]); // utf-8  first byte of é
        assertEquals((byte) '\u00a9', utf8Buffer[3]); // utf-8 second byte of é
    }
}
