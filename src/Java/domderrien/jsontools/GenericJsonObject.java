package domderrien.jsontools;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Provide documentation
 */
public class GenericJsonObject implements JsonObject {
    protected HashMap<String, Object> hashMap;

    /**
     * Default constructor, useful to generate the object to be sent to the browser
     */
    public GenericJsonObject() {
        hashMap = new HashMap<String, Object>();
    }

    @Override
    public String toString() {
        return toString(0);
    }

    /**
     * Local toString() providing a nicely indented view of the object, with one attribute per line
     *
     * @param index Depth of the recursive call
     * @return Nicely formatted string
     */
    protected String toString(int index) {
        StringBuilder offset = new StringBuilder("  ");
        for (int i = 0; i < index; ++ i) {
            offset.append("  ");
        }
        StringBuilder output = new StringBuilder();
        output.append("JsonObject: ").append("{");
        boolean firstItem = true;
        for (String key : hashMap.keySet()) {
            if (!firstItem) {
                output.append(", ");
            }
            else {
                firstItem = false;
            }
            output.append("\n").append(offset).append(key).append(": ");
            Object value = hashMap.get(key);
            if (value instanceof GenericJsonObject) {
                output.append(((GenericJsonObject) value).toString(index + 1));
            }
            else if (value instanceof GenericJsonArray) {
                output.append(((GenericJsonArray) value).toString(index + 1));
            }
            else if (value instanceof String) {
                output.append("\"").append(value).append("\"");
            }
            else {
                output.append(value);
            }
        }
        output.append("\n").append(offset.substring((index) * 2)).append("}");
        return output.toString();
    }

    public int size() {
        return hashMap.size();
    }

    public Map<String, Object> getMap() {
        return hashMap;
    }

    public boolean isNonNull(String key) {
        return hashMap.get(key) != null;
    }

    public boolean containsKey(String key) {
        return hashMap.containsKey(key);
    }

    /** @see java.util.Map#get */
    protected Object getObject(String key) {
        return hashMap.get(key);
    }

    public boolean getBoolean(String key) throws ClassCastException {
        Boolean value = (Boolean) getObject(key);
        return Boolean.TRUE.equals(value);
    }

    public long getLong(String key) throws ClassCastException {
        return ((Double) getObject(key)).longValue();
    }

    public double getDouble(String key) throws ClassCastException, NumberFormatException {
        return ((Double) getObject(key));
    }

    public String getString(String key) throws ClassCastException {
        return (String) getObject(key);
    }

    public JsonObject getJsonObject(String key) throws ClassCastException {
        return (JsonObject) getObject(key);
    }

    public JsonArray getJsonArray(String key) throws ClassCastException {
        return (JsonArray) getObject(key);
    }

    public JsonException getJsonException(String key) throws ClassCastException {
        return (JsonException) getObject(key);
    }

    /** @see java.util.Map#put */
    protected void putArbitrary(String key, Object object) {
        hashMap.put(key, object);
    }

    public void put(String key, boolean value) {
        putArbitrary(key, value ? Boolean.TRUE : Boolean.FALSE);
    }

    public void put(String key, long value) {
        putArbitrary(key, new Double(value));
    }

    public void put(String key, double value) {
        putArbitrary(key, new Double(value));
    }

    public void put(String key, String value) {
        putArbitrary(key, (Object) value);
    }

    public void put(String key, JsonObject value) {
        putArbitrary(key, (Object) value);
    }

    public void put(String key, JsonArray value) {
        putArbitrary(key, (Object) value);
    }

    public void put(String key, JsonException value) {
        putArbitrary(key, (Object) value);
    }

    public void remove(String key) {
        this.hashMap.remove(key);
    }

    public void removeAll() {
        this.hashMap.clear();
    }

    public void append(JsonObject additionalValues) {
        hashMap.putAll(additionalValues.getMap());
    }
}
