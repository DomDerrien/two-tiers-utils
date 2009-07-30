package domderrien.jsontools;

import java.util.Map;

/**
 * Errors are conveyed to the browser application logic using JsonException, a special type of
 * {@link JsonObject}.
 * <p>
 * Exception type in JavaScript cannot be easily indicated by class, so it's carried by a field <code>
 * exceptionType</code> (note that the field value is used to identify the JsonException type and
 * to find the corresponding information message from a resource bundle or a xliff file). In JavaScript,
 * a JsonException  being received as normal JsonObject is easily distinguishable thanks to its default
 * <code>isException</code> attribute.
 */
public class JsonException extends Exception implements JsonObject {

    private static final long serialVersionUID = 7063913461828647286L;

    private JsonObject internalStorage = new GenericJsonObject();

    private static long EXCEPTION_ID_COUNTER = 0;
    private long id = getNextId();

    /**
     * Generate the exception identifier
     */
    private synchronized static long getNextId() {
        return ++ EXCEPTION_ID_COUNTER;
    }

    /**
     * Basic constructor.
     * Message defaults to <code>typegetBundleId()</code>.
     *
     * @param type Client-side exception identifier.
     */
    public JsonException(String type) {
        this(type, type, null);
    }

    /**
     * Constructor with a wrapped exception.
     * Message defaults to <code>exception.getClass().getName() + ": " + exception.getMessage()</code>.
     *
     * @param type Client-side exception identifier.
     * @param exception  Original exception
     */
    public JsonException(String type, Exception exception) {
        this(type, exception.getClass().getName() + ": " + exception.getMessage(), exception);
    }

    /**
     * Constructor specifying message
     *
     * @param type Client-side exception identifier.
     * @param message Message associated with the exception
     */
    public JsonException(String type, String message) {
        this(type, message, null);
    }

    /**
     * Constructor specifying message with a wrapped exception.
     *
     * @param type Client-side exception identifier.
     * @param message Message associated with the exception
     * @param exception  Original exception
     */
    public JsonException(String type, String message, Exception exception) {
        super(message == null ? type : message, exception);
        internalStorage.put("isException", true);
        internalStorage.put("exceptionId", id);
        internalStorage.put("exceptionType", type);
        internalStorage.put("exceptionMessage", super.getMessage());
    }

    @Override
    public String toString() {
        return internalStorage.toString();
    }

    @Override
    public String getMessage() {
        String bundleId = internalStorage.getString("exceptionType");
        String superMessage = super.getMessage();
        if (superMessage == null || "".equals(superMessage) || bundleId.equals(superMessage) ) {
            return "BundleId: " + bundleId;
        }
        return "BundleId: " + bundleId + " -- " + superMessage;
    }

    /** accessor */
    protected long getExceptionId() {
        return getLong("exceptionId");
    }

    /** accessor */
    protected String getJsonExceptionType() {
        return getString("exceptionType");
    }

    public int size() {
        return internalStorage.size();
    }

    public Map<String, Object> getMap() {
        return internalStorage.getMap();
    }

    public boolean isNonNull(String key) {
        return internalStorage.isNonNull(key);
    }
    public boolean containsKey(String key) {
        return internalStorage.containsKey(key);
    }

    public boolean getBoolean(String key) throws ClassCastException {
        return internalStorage.getBoolean(key);
    }

    public long getLong(String key) throws ClassCastException {
        return internalStorage.getLong(key);
    }

    public double getDouble(String key) throws ClassCastException, NumberFormatException {
        return internalStorage.getDouble(key);
    }

    public String getString(String key) throws ClassCastException {
        return internalStorage.getString(key);
    }

    public JsonObject getJsonObject(String key) throws ClassCastException {
        return internalStorage.getJsonObject(key);
    }

    public JsonArray getJsonArray(String key) throws ClassCastException {
        return internalStorage.getJsonArray(key);
    }

    public JsonException getJsonException(String key) throws ClassCastException {
        return internalStorage.getJsonException(key);
    }

    protected boolean blockJsonExceptionAttributeOverriding(String key) {
        return "isException".equals(key) ||
            "exceptionId".equals(key) ||
            "exceptionType".equals(key) ||
            "exceptionMessage".equals(key);
    }

    public void put(String key, boolean value) {
        if (!blockJsonExceptionAttributeOverriding(key))
            internalStorage.put(key, value);
    }

    public void put(String key, long value) {
        if (!blockJsonExceptionAttributeOverriding(key))
            internalStorage.put(key, value);
    }

    public void put(String key, double value) {
        if (!blockJsonExceptionAttributeOverriding(key))
            internalStorage.put(key, value);
    }

    public void put(String key, String value) {
        if (!blockJsonExceptionAttributeOverriding(key))
            internalStorage.put(key, value);
    }

    public void put(String key, JsonObject value) {
        if (!blockJsonExceptionAttributeOverriding(key))
            internalStorage.put(key, value);
    }

    public void put(String key, JsonArray value) {
        if (!blockJsonExceptionAttributeOverriding(key))
            internalStorage.put(key, value);
    }

    public void put(String key, JsonException value) {
        if (!blockJsonExceptionAttributeOverriding(key))
            internalStorage.put(key, value);
    }

    public void remove(String key) {
        if (!blockJsonExceptionAttributeOverriding(key))
            internalStorage.remove(key);
    }

    public void removeAll() {
        throw new UnsupportedOperationException("Some JsonException attributes should not be removed");
    }

    public void append(JsonObject additionalValues) {
        throw new UnsupportedOperationException("Some JsonException attributes should not be preserved");
    }
}
