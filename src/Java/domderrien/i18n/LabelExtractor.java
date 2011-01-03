package domderrien.i18n;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LabelExtractor
{
    static public final String ERROR_MESSAGE_PREFIX = "errMsg_";

    public enum ResourceFileId {
        master,
        second,
        third,
        fourth
    }

    /**
     * Return the message associated to the given error code, extracted from the master resource file.
     *
     * @param errorCode Error code
     * @param locale    Optional locale instance, use to determine in which
     *                  resource files the label should be found. If the
     *                  reference is <code>null</code>, the root resource
     *                  bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(int errorCode, Locale locale) {
        return get(ResourceFileId.master, errorCode, locale);
    }

    /**
     * Return the message associated to the given error code, extracted from the identifier resource file.
     *
     * @param errorCode Error code
     * @param resId     Identifier of the resource file where the localized data should be extracted from
     * @param locale    Optional locale instance, use to determine in which
     *                  resource files the label should be found. If the
     *                  reference is <code>null</code>, the root resource
     *                  bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(ResourceFileId resId, int errorCode, Locale locale) {
        return get(ResourceFileId.master, errorCode, null, locale);
    }

    /**
     * Return the message associated to the given error code.
     *
     * @param errorCode Error code
     * @param parameters Array of parameters, each one used to replace a
     *                   pattern made of a number between curly braces.
     * @param locale    Optional locale instance, use to determine in which
     *                  resource files the label should be found. If the
     *                  reference is <code>null</code>, the root resource
     *                  bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(int errorCode, Object[] parameters,  Locale locale) {
        return get(ResourceFileId.master, errorCode, parameters, locale);
    }

    /**
     * Return the message associated to the given error code.
     *
     * @param errorCode Error code
     * @param resId     Identifier of the resource file where the localized data should be extracted from
     * @param parameters Array of parameters, each one used to replace a
     *                   pattern made of a number between curly braces.
     * @param locale    Optional locale instance, use to determine in which
     *                  resource files the label should be found. If the
     *                  reference is <code>null</code>, the root resource
     *                  bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(ResourceFileId resId, int errorCode, Object[] parameters,  Locale locale) {
        String prefix = ERROR_MESSAGE_PREFIX;
        String label = get(resId, prefix + errorCode, parameters, locale);
        if (label.startsWith(prefix)) {
            return String.valueOf(errorCode);
        }
        return label;
    }

    /**
     * Return the message associated to the given identifier.
     *
     * @param messageId Identifier used to retrieve the localized label.
     * @param locale    Optional locale instance, use to determine in which
     *                  resource files the label should be found. If the
     *                  reference is <code>null</code>, the root resource
     *                  bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(String messageId, Locale locale) {
        return get(messageId, messageId, locale);
    }

    /**
     * Return the message associated to the given identifier. If it's missing,
     * fall back on the second identifier.
     *
     * @param messageId  Identifier used to retrieve the localized label.
     * @param fallbackId Identifier to be used if the first message can't be loaded.
     *                   If the retrieval of the second message fails too, this second
     *                   identifier is returned.
     * @param locale     Optional locale instance, use to determine in which
     *                   resource files the label should be found. If the
     *                   reference is <code>null</code>, the root resource
     *                   bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(String messageId, String fallbackId, Locale locale) {
        return get(ResourceFileId.master, messageId, fallbackId, locale);
    }

    /**
     * Return the message associated to the given identifier.
     *
     * @param messageId Identifier used to retrieve the localized label.
     * @param resId     Identifier of the resource file where the localized data should be extracted from
     * @param locale    Optional locale instance, use to determine in which
     *                  resource files the label should be found. If the
     *                  reference is <code>null</code>, the root resource
     *                  bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(ResourceFileId resId, String messageId, Locale locale) {
        return get(resId, messageId, messageId, (Map<String, Object>) null, locale);
    }

    /**
     * Return the message associated to the given identifier. If it's missing,
     * fall back on the second identifier.
     *
     * @param messageId  Identifier used to retrieve the localized label.
     * @param fallbackId Identifier to be used if the first message can't be loaded.
     *                   If the retrieval of the second message fails too, this second
     *                   identifier is returned.
     * @param resId      Identifier of the resource file where the localized data should be extracted from
     * @param locale     Optional locale instance, use to determine in which
     *                   resource files the label should be found. If the
     *                   reference is <code>null</code>, the root resource
     *                   bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(ResourceFileId resId, String messageId, String fallbackId, Locale locale) {
        return get(resId, messageId, fallbackId, (Map<String, Object>) null, locale);
    }

    /**
     * Return the message associated to the given identifier.
     *
     * @param messageId  Identifier used to retrieve the localized label.
     * @param parameters Array of parameters, each one used to replace a
     *                   pattern made of a number between curly braces.
     * @param locale     Optional locale instance, use to determine in which
     *                   resource files the label should be found. If the
     *                   reference is <code>null</code>, the root resource
     *                   bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(String messageId, Object[] parameters, Locale locale) {
        return get(messageId, messageId, parameters, locale);
    }

    /**
     * Return the message associated to the given identifier. If it's missing,
     * fall back on the second identifier.
     *
     * @param messageId  Identifier used to retrieve the localized label.
     * @param fallbackId Identifier to be used if the first message can't be loaded.
     *                   If the retrieval of the second message fails too, this second
     *                   identifier is returned.
     * @param parameters Array of parameters, each one used to replace a
     *                   pattern made of a number between curly braces.
     * @param locale     Optional locale instance, use to determine in which
     *                   resource files the label should be found. If the
     *                   reference is <code>null</code>, the root resource
     *                   bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(String messageId, String fallbackId, Object[] parameters, Locale locale) {
        return get(ResourceFileId.master, messageId, fallbackId, parameters, locale);
    }

    /**
     * Return the message associated to the given identifier.
     *
     * @param messageId  Identifier used to retrieve the localized label.
     * @param parameters Map of parameters, each one used to replace a
     *                   pattern made of a map key between curly braces.
     * @param locale     Optional locale instance, use to determine in which
     *                   resource files the label should be found. If the
     *                   reference is <code>null</code>, the root resource
     *                   bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(String messageId, Map<String, Object> parameters, Locale locale) {
        return get(messageId, messageId, parameters, locale);
    }

    /**
     * Return the message associated to the given identifier. If it's missing,
     * fall back on the second identifier.
     *
     * @param messageId  Identifier used to retrieve the localized label.
     * @param fallbackId Identifier to be used if the first message can't be loaded.
     *                   If the retrieval of the second message fails too, this second
     *                   identifier is returned.
     * @param parameters Map of parameters, each one used to replace a
     *                   pattern made of a map key between curly braces.
     * @param locale     Optional locale instance, use to determine in which
     *                   resource files the label should be found. If the
     *                   reference is <code>null</code>, the root resource
     *                   bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(String messageId, String fallbackId, Map<String, Object> parameters, Locale locale) {
        return get(ResourceFileId.master, messageId, fallbackId, parameters, locale);
    }

    /**
     * Return the message associated to the given identifier.
     *
     * @param messageId  Identifier used to retrieve the localized label.
     * @param resId     Identifier of the resource file where the localized data should be extracted from
     * @param parameters Array of parameters, each one used to replace a
     *                   pattern made of a number between curly braces.
     * @param locale     Optional locale instance, use to determine in which
     *                   resource files the label should be found. If the
     *                   reference is <code>null</code>, the root resource
     *                   bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(ResourceFileId resId, String messageId, Object[] parameters, Locale locale) {
        return get(resId, messageId, messageId, parameters, locale);
    }

    /**
     * Return the message associated to the given identifier. If it's missing,
     * fall back on the second identifier.
     *
     * @param resId      Identifier of the resource file where the localized data should be extracted from
     * @param messageId  Identifier used to retrieve the localized label.
     * @param fallbackId Identifier to be used if the first message can't be loaded.
     *                   If the retrieval of the second message fails too, this second
     *                   identifier is returned.
     * @param parameters Array of parameters, each one used to replace a
     *                   pattern made of a number between curly braces.
     * @param locale     Optional locale instance, use to determine in which
     *                   resource files the label should be found. If the
     *                   reference is <code>null</code>, the root resource
     *                   bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(ResourceFileId resId, String messageId, String fallbackId, Object[] parameters, Locale locale) {
        String label = messageId;
        if (messageId != null && 0 < messageId.length()) {
            try {
                ResourceBundle labels = getResourceBundle(resId, locale);
                label = labels.getString(messageId);
            }
            catch (MissingResourceException ex) {
                if (fallbackId == null) {
                    return null;
                }
                if (messageId != fallbackId) {
                    return get(resId, fallbackId, fallbackId, parameters, locale);
                }
            }
        }
        return insertParameters(label, parameters);
    }

    /**
     * Return the message associated to the given identifier.
     *
     * @param messageId  Identifier used to retrieve the localized label.
     * @param resId     Identifier of the resource file where the localized data should be extracted from
     * @param parameters Map of parameters, each one used to replace a
     *                   pattern made of a map key between curly braces.
     * @param locale     Optional locale instance, use to determine in which
     *                   resource files the label should be found. If the
     *                   reference is <code>null</code>, the root resource
     *                   bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(ResourceFileId resId, String messageId, Map<String, Object> parameters, Locale locale) {
        return get(resId, messageId, messageId, parameters, locale);
    }

    /**
     * Return the message associated to the given identifier. If it's missing,
     * fall back on the second identifier.
     *
     * @param resId      Identifier of the resource file where the localized data should be extracted from
     * @param messageId  Identifier used to retrieve the localized label.
     * @param fallbackId Identifier to be used if the first message can't be loaded.
     *                   If the retrieval of the second message fails too, this second
     *                   identifier is returned.
     * @param parameters Map of parameters, each one used to replace a
     *                   pattern made of a map key between curly braces.
     * @param locale     Optional locale instance, use to determine in which
     *                   resource files the label should be found. If the
     *                   reference is <code>null</code>, the root resource
     *                   bundle is used (written in English).
     * @return A localized label associated to the given error code. If no
     *         association is found, the message identifier is returned.
     */
    public static String get(ResourceFileId resId, String messageId, String fallbackId, Map<String, Object> parameters, Locale locale) {
        String label = messageId;
        if (messageId != null && 0 < messageId.length()) {
            try {
                ResourceBundle labels = getResourceBundle(resId, locale);
                label = labels.getString(messageId);
            }
            catch (MissingResourceException ex) {
                if (fallbackId == null) {
                    return null;
                }
                if (messageId != fallbackId) {
                    return get(resId, fallbackId, fallbackId, parameters, locale);
                }
            }
        }
        return insertParameters(label, parameters);
    }

    public final static String NULL_INDICATOR = "[null]";

    /**
     * Utility method inserting the parameters into the given string
     *
     * @param label      Text to process
     * @param parameters Array of parameters, each one used to replace a
     *                   pattern made of a number between curly braces.
     * @return Text where the place holders have been replaced by the
     *         toString() of the objects passed in the array.
     *
     * @see java.text.MessageFormat#format(String, Object[])
     */
    public static String insertParameters(String label, Object[] parameters) {
        if (label != null && parameters != null) {
            // Note Message.format(label, parameters) does NOT work.
            StringBuilder temp = new StringBuilder(label);
            int paramNb = parameters.length;
            for (int i=0; i<paramNb; ++i) {
                processParameter(temp, "{" + i + "}", parameters[i]); //$NON-NLS-1$ //$NON-NLS-2$
            }
            label = temp.toString();
        }
        return label;
    }

    /**
     * Utility method inserting the parameters into the given string
     *
     * @param label      Text to process
     * @param parameters Map of parameters, each one used to replace a
     *                   pattern made of a map key between curly braces.
     * @return Text where the place holders have been replaced by the
     *         toString() of the objects passed in the array.
     *
     * @see java.text.MessageFormat#format(String, Object[])
     */
    public static String insertParameters(String label, Map<String, Object> parameters) {
        if (label != null && parameters != null) {
            // Note Message.format(label, parameters) does NOT work.
            StringBuilder temp = new StringBuilder(label);
            for (String key: parameters.keySet()) {
                processParameter(temp, "{" + key + "}", parameters.get(key)); //$NON-NLS-1$ //$NON-NLS-2$
            }
            label = temp.toString();
        }
        return label;
    }

    /**
     * Helper inserting the parameter in place of the all occurrences of the given pattern in the given sentence
     *
     * @param sentence
     * @param pattern
     * @param parameter
     * @return
     */
    protected static void processParameter(StringBuilder sentence, String pattern, Object parameter) {
        if (parameter instanceof List<?>) {
            for(Object innerObject: (List<?>) parameter) {
                String innerMessage = neutralizeParameter(innerObject) + pattern;
                replaceFirst(sentence, pattern, innerMessage);
            }
            replaceAll(sentence, pattern, "");
        }
        else {
            replaceAll(sentence, pattern, neutralizeParameter(parameter));
        }
    }

    /**
     * Helper replacing the first occurrence of the pattern in the given sentence by the specified value
     *
     * @param sentence Source with the pattern to be replaced
     * @param pattern String to be replaced
     * @param value Value to insert in place of the pattern
     */
    protected static void replaceFirst(StringBuilder sentence, String pattern, String value) {
        int patternIdx = sentence.indexOf(pattern);
        if (patternIdx != -1) {
            sentence.replace(patternIdx, patternIdx  + pattern.length(), value);
        }
    }

    /**
     * Helper replacing all occurrence of the pattern in the given sentence by the specified value
     *
     * @param sentence Source with the pattern to be replaced
     * @param pattern String to be replaced
     * @param value Value to insert in place of the pattern
     */
    protected static void replaceAll(StringBuilder sentence, String pattern, String value) {
        int patternIdx = -1;
        while ((patternIdx = sentence.indexOf(pattern, patternIdx + 1)) != -1) {
            sentence.replace(patternIdx, patternIdx  + pattern.length(), value);
        }
    }

    /**
     * Helper escaping characters that might be interpreted as regular expressions commands
     */
    protected static String neutralizeParameter(Object parameter) {
        // Dollar sign ($) neutralized in parameter to avoid issues with Regular Expression engine used by replaceAll();
        // return parameter == null ? NULL_INDICATOR : parameter.toString().replace("\\", "\\\\").replace("$", "\\$");

        // No more Regular Expression command escaping needed as the string replacement is done manually
        return parameter == null ? NULL_INDICATOR : parameter.toString();
    }

    /*------------------------------------------------------------------*/
    /*------------------------------------------------------------------*/

    protected static HashMap<String, ResourceBundle> masterResourceBundleSet = new HashMap<String, ResourceBundle>();
    protected static HashMap<String, ResourceBundle> secondResourceBundleSet = new HashMap<String, ResourceBundle>();
    protected static HashMap<String, ResourceBundle> thirdResourceBundleSet = new HashMap<String, ResourceBundle>();
    protected static HashMap<String, ResourceBundle> fourthResourceBundleSet = new HashMap<String, ResourceBundle>();

    /**
     * Provides a reset mechanism for the unit test suite
     * @param resId Identifier of the resource file set to manipulate
     */
    protected static void resetResourceBundleList(ResourceFileId resId) {
        masterResourceBundleSet.clear();
    }

    /**
     * Gives the string representing the locale or fall-back on the default one.
     * Made protected to be available for unit testing.
     *
     * @param locale locale to use
     * @return String identifying the locale
     */
    protected static String getResourceBundleId(Locale locale) {
        return locale == null ?
                "root" : //$NON-NLS-1$
                locale.toString(); // Composes language + country (if country available)
    }

    /**
     * Protected setter made available for the unit testing
     *
     * @param resId Identifier of the resource file set to manipulate
     * @param rb Mock resource bundle
     * @param locale Locale associated to the mock resource bundle
     */
    protected static ResourceBundle setResourceBundle(ResourceFileId resId, ResourceBundle rb, Locale locale) {
        String rbId = getResourceBundleId(locale);
        ResourceBundle previousRB = null;
        if (ResourceFileId.second.equals(resId)) {
            previousRB = secondResourceBundleSet.get(rbId);
            secondResourceBundleSet.put(rbId, rb);
        }
        else if (ResourceFileId.third.equals(resId)) {
            previousRB = thirdResourceBundleSet.get(rbId);
            thirdResourceBundleSet.put(rbId, rb);
        }
        else if (ResourceFileId.fourth.equals(resId)) {
            previousRB = fourthResourceBundleSet.get(rbId);
            fourthResourceBundleSet.put(rbId, rb);
        }
        else {
            previousRB = masterResourceBundleSet.get(rbId);
            masterResourceBundleSet.put(rbId, rb);
        }
        return previousRB;
    }

    /**
     * Retrieve the application resource bundle with the list of supported languages.
     * Specified protected only to ease the unit testing (IOP).
     *
     * @param resId Identifier of the resource file where the localized data should be extracted from
     * @param locale locale to use when getting the resource bundle
     * @return The already resolved/set resource bundle or the one expected at runtime
     *
     * @throws MissingResourceException
     */
    protected static ResourceBundle getResourceBundle(ResourceFileId resId, Locale locale) throws MissingResourceException {
        String rbId = getResourceBundleId(locale);
        ResourceBundle rb;
        if (ResourceFileId.second.equals(resId)) { rb = (ResourceBundle) secondResourceBundleSet.get(rbId); }
        else if (ResourceFileId.third.equals(resId)) { rb = (ResourceBundle) thirdResourceBundleSet.get(rbId); }
        else if (ResourceFileId.fourth.equals(resId)) { rb = (ResourceBundle) fourthResourceBundleSet.get(rbId); }
        else { rb = (ResourceBundle) masterResourceBundleSet.get(rbId); }
        if (rb == null) {
            // Get the resource bundle filename from the application settings and return the identified file
            ResourceBundle applicationSettings = ResourceBundle.getBundle(ResourceNameDefinitions.CONFIG_PROPERTIES_FILENAME, locale);
            String keyForLookup;
            if (ResourceFileId.second.equals(resId)) { keyForLookup = ResourceNameDefinitions.SECOND_TMX_FILENAME_KEY; }
            else if (ResourceFileId.third.equals(resId)) { keyForLookup = ResourceNameDefinitions.THIRD_TMX_FILENAME_KEY; }
            else if (ResourceFileId.fourth.equals(resId)) { keyForLookup = ResourceNameDefinitions.FOURTH_TMX_FILENAME_KEY; }
            else { keyForLookup = ResourceNameDefinitions.MASTER_TMX_FILENAME_KEY; }
            // Get the resource bundle
            rb = ResourceBundle.getBundle(applicationSettings.getString(keyForLookup), locale);
            // Save the resource bundle reference
            if (ResourceFileId.second.equals(resId)) { secondResourceBundleSet.put(rbId, rb); }
            else if (ResourceFileId.third.equals(resId)) { thirdResourceBundleSet.put(rbId, rb); }
            else if (ResourceFileId.fourth.equals(resId)) { fourthResourceBundleSet.put(rbId, rb); }
            else { masterResourceBundleSet.put(rbId, rb); }
        }
        return rb;
    }
}
