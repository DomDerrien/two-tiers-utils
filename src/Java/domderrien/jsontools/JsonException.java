package domderrien.jsontools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    private final long id = getNextId();

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
        put("exceptionId", id);
        put("exceptionType", type);
        put("exceptionMessage", super.getMessage());
    }

    /** Accessor to ease the behavior injection for the unit tests
     * @throws IOException If the buffer creation fails*/
    protected OutputStream getOutputStream() throws IOException {
        return new ByteArrayOutputStream();
    }

    @Override
    public String toString() {
        try {
            OutputStream fakeOutput = getOutputStream();
            toStream(fakeOutput, true);
            return fakeOutput.toString();
        }
        catch (IOException e) {
            return internalStorage.toString();
        }
    }

    /** accessor */
    protected long getExceptionId() {
        return id;
    }

    /** accessor */
    protected String getExceptionType() {
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

    public boolean isInstance(String key, Class<?> clazz) {
        return internalStorage.isInstance(key, clazz);
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

    public String getString(String key, boolean multiLine) throws ClassCastException {
        return internalStorage.getString(key, multiLine);
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

    public void put(String key, boolean value) {
        internalStorage.put(key, value);
    }

    public void put(String key, Boolean value) {
        internalStorage.put(key, value);
    }

    public void put(String key, long value) {
        internalStorage.put(key, value);
    }

    public void put(String key, Long value) {
        internalStorage.put(key, value);
    }

    public void put(String key, double value) {
        internalStorage.put(key, value);
    }

    public void put(String key, Double value) {
        internalStorage.put(key, value);
    }

    public void put(String key, String value) {
        internalStorage.put(key, value);
    }

    public void put(String key, String value, boolean multiLine) {
        internalStorage.put(key, value, multiLine);
    }

    public void put(String key, JsonObject value) {
        internalStorage.put(key, value);
    }

    public void put(String key, JsonArray value) {
        internalStorage.put(key, value);
    }

    public void put(String key, JsonException value) {
        internalStorage.put(key, value);
    }

    public void remove(String key) {
        internalStorage.remove(key);
    }

    public void removeAll() {
        throw new UnsupportedOperationException("Some JsonException attributes should not be removed");
    }

    public void append(JsonObject additionalValues) {
        throw new UnsupportedOperationException("Some JsonException attributes should not be preserved");
    }

    public OutputStream toStream(OutputStream out, boolean isFollowed) throws IOException {
        JsonSerializer.startObject("success", false, out, true);
        JsonSerializer.toStream("isException", true, out, true);
        JsonSerializer.toStream("exceptionId", id, out, true);
        // JsonSerializer.toStream("exceptionType", getExceptionType(), out, true);
        JsonSerializer.toStream("exceptionMessage", getMessage().replaceAll("[\\r\\n]+", " "), out, true);
        /** / // This call to printStackTrace() causes a StackOverflowException because of Corbetura instrumentation
        MockOutputStream temp = new MockOutputStream();
        printStackTrace(new PrintStream(temp));
        JsonSerializer.toStream("exceptionStackTrace", temp.getStream().substring(0, 100), out, true);
        /**/
        // JsonSerializer.introduceComplexValue("originalException", out);
        // (new JsonException("SOURCE_EXCEPTION"), getCause()).toStream(out, isFollowed);
        String originalExceptionMessage = null;
        if (getCause() == null) {
            originalExceptionMessage = "[no cause]";
        }
        else if (getCause().getMessage() == null) {
            originalExceptionMessage = "[cause class: " + getCause().getClass().getName() + "]";
        }
        else {
            originalExceptionMessage = getCause().getMessage().replaceAll("[\\r\\n]+", " ");
        }
        JsonSerializer.endObject("originalExceptionMessage", originalExceptionMessage, out, isFollowed);
        return out;
    }
}
