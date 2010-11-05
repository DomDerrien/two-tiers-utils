package domderrien.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class StringUtils {

    /**
     * Transform the sequence '\\uxxxx' or just 'xxxx' in the corresponding
     * Unicode character
     *
     * @param inSequence hexadecimal sequence
     * @return Unicode character
     */
    public static char convertUnicodeChar(String inSequence) {
        if (inSequence.length() == 6 && inSequence.charAt(0) == '\\' && inSequence.charAt(1) == 'u') {
            inSequence = inSequence.substring(2);
        }
        else if (inSequence.length() != 4) {
            throw new IllegalArgumentException("The sequence should be prefixed by '\\u' followed by 4 hexadecimal digits, or just be composed of the 4 hexadecimal digits.");
        }
        int outValue = 0;
        for (int i = 0; i < 4; i++) {
            char item = inSequence.charAt(i);
            if ('0' <= item && item <= '9') {
                outValue = (outValue << 4) + item - '0';
            }
            else if ('a' <= item && item <= 'f') {
                outValue = (outValue << 4) + 10 + item - 'a';
            }
            else if ('A' <= item && item <= 'F') {
                outValue = (outValue << 4) + 10 + item - 'A';
            }
            else {
                throw new IllegalArgumentException("The sequence should be prefixed by '\\u' followed by 4 hexadecimal digits, or just be composed of the 4 hexadecimal digits.");
            }
        }
        return (char) outValue;
    }

    /**
     * Exchange all Unicode escaped sequences by the corresponding
     * Unicode string
     *
     * @param in Source with possible escaped sequences
     * @return Clean string
     */
    public static String convertUnicodeChars(String in) {
        StringBuilder out = new StringBuilder(in);
        int unicodePrefixIdx = out.indexOf("\\u");
        while (unicodePrefixIdx != -1) {
            out.replace(unicodePrefixIdx, unicodePrefixIdx + 6, "" + convertUnicodeChar(out.substring(unicodePrefixIdx + 2, unicodePrefixIdx + 6)));
            unicodePrefixIdx = out.indexOf("\\u");
        }
        return out.toString();
    }

    /**
     * Read all bytes produced by the given input stream and decode
     * it into a string for the specified character set
     *
     * @param stream Data source
     * @param charsetName Type of character encoding
     * @return String produced with the data from the input stream
     *
     * @throws IOException If the stream cannot be read, or if the conversion for the character set fails
     */
    public static String getString(InputStream stream, String charsetName) throws IOException {
        return getString(stream, charsetName, 1024);
    }

    // Introduced for test purposes only, to be able to test the buffer recycling
    protected static String getString(InputStream stream, String charsetName, int bufferSize) throws IOException {
        // Get the characters one-by-one
        int characterIdx = 0;
        byte[] characters = Arrays.copyOf(new byte[0], bufferSize);
        while (true) {
            int character = stream.read();
            if(character == -1) {
                break;
            }
            characters[characterIdx] = (byte) character;
            characterIdx ++;
            // Augment the buffer size if more characters have to be read
            if ((characterIdx % bufferSize) == 0) {
                characters = Arrays.copyOf(characters, characterIdx + bufferSize);
            }
        }
        // Generate the string for the given character set
        return new String (characters, 0, characterIdx, charsetName);
    }

    /**
     * Transform the UTF-8 string in its Unicode counterpart
     *
     * @param utf8Str Original string
     * @return Converted string if there's no error, the original value otherwise
     */
    public static String toUnicode(String utf8Str) {
        String out = utf8Str;
        try {
            out = new String(utf8Str.getBytes(), JAVA_UTF8_CHARSET);
        }
        catch (UnsupportedEncodingException e) {
            // Note for the testers:
            //   UnsupportedEncodingException can be generated if the character set would be invalid (instead of "UTF8")
            //   Not a possible use case here...
        }
        return out;
    }

    /**
     * Transform the Unicode string in its UTF-8 counterpart
     *
     * @param unicodeStr Original string
     * @return Converted string if there's no error, the original value otherwise
     */
    public static String toUTF8(String unicodeStr) {
        String out = unicodeStr;
        try {
            out = new String(unicodeStr.getBytes(JAVA_UTF8_CHARSET));
        }
        catch (UnsupportedEncodingException e) {
            // Note for the testers:
            //   UnsupportedEncodingException can be generated if the character set would be invalid (instead of "UTF8")
            //   Not a possible use case here...
        }
        return out;
    }

    /**
     * Definition for the buffer encoding of java.lang.* functions
     */
    public static final String JAVA_UTF8_CHARSET = "UTF8";

    /**
     * Definition for the HTML page encoding
     */
    public static final String HTML_UTF8_CHARSET = "UTF-8";
}
