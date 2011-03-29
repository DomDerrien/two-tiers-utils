package domderrien.jsontools;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class GenericJsonObject implements JsonObject, Serializable {

    private static final long serialVersionUID = 438945948320986452L;
    protected Map<String, Object> hashMap;

    /**
     * Default constructor, useful to generate the object to be sent to the browser
     */
    public GenericJsonObject() {
        hashMap = new HashMap<String, Object>();
    }

    /**
     * Constructor made of an existing map
     */
    public GenericJsonObject(Map<String, Object> map) {
        hashMap = map;
    }

    /**
     * Constructor made of parameters passed to a HTTP request (URL-encoded)
     */
    public GenericJsonObject(HttpServletRequest request) {
        this();

        Enumeration<?> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String[] values = request.getParameterValues(name);
            if (values == null || values.length == 0) {
                hashMap.put(name, null);
            }
            else if (values.length == 1) {
                hashMap.put(name, values[0]);
            }
            else {
                hashMap.put(name, new GenericJsonArray(values));
            }
        }
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
            Object value = getObject(key);
            if (value instanceof GenericJsonObject) {
                output.append(((GenericJsonObject) value).toString(index + 1));
            }
            else if (value instanceof GenericJsonArray) {
                output.append(((GenericJsonArray) value).toString(index + 1));
            }
            else if (value instanceof String) {
                output.append("String: ");
                output.append("\"").append(value).append("\"");
            }
            else if (value instanceof String[]) {
                output.append("String[]: ");
                String[] arrayOfStrings = (String[]) value;
                int limit = arrayOfStrings.length;
                if (limit == 0) {
                    output.append("null");
                }
                else if (limit == 1) {
                    output.append("\"").append(arrayOfStrings[0]).append("\"");
                }
                else {
                    output.append("[");
                    for (int i=0; i<limit; i++) {
                        output.append("\"").append(arrayOfStrings[i]).append("\"");
                        if (i != limit - 1) {
                            output.append(",");
                        }
                    }
                    output.append("]");
                }
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
        return getObject(key) != null;
    }

    public boolean containsKey(String key) {
        return hashMap.containsKey(key);
    }

    public boolean isInstance(String key, Class<?> clazz) {
        Object object = getObject(key);
        if (object == null) {
            return false;
        }
        return clazz.isInstance(object);
    }

    /** @see java.util.Map#get */
    protected Object getObject(String key) {
        return hashMap.get(key);
    }

    public boolean getBoolean(String key) throws ClassCastException {
        Object value = getObject(key);
        Boolean typedValue = Boolean.FALSE;
        if (value instanceof String[]) {
            String[] arrayOfStrings = (String[]) value;
            if (0 < arrayOfStrings.length) {
                typedValue = Boolean.parseBoolean(arrayOfStrings[0]);
            }
        }
        else if (value instanceof String) {
            typedValue = Boolean.valueOf((String) value);
        }
        else {
            typedValue = (Boolean) value;
        }
        return Boolean.TRUE.equals(typedValue);
    }

    public long getLong(String key) throws ClassCastException {
        Object value = getObject(key);
        Long typedValue = 0L;
        if (value instanceof String[]) {
            String[] arrayOfStrings = (String[]) value;
            if (0 < arrayOfStrings.length) {
                typedValue = Long.parseLong(arrayOfStrings[0]);
            }
        }
        else {
            if (value == null) {
                typedValue = 0L;
            }
            else if (value instanceof Long) {
                typedValue = (Long) value;
            }
            else if (value instanceof String) {
                typedValue = Long.valueOf((String) value);
            }
            else { // if (value instanceof Double) {
                // Because the JsonParser generates Double instances
                typedValue = ((Double) value).longValue();
            }
        }
        return typedValue.longValue();
    }

    public double getDouble(String key) throws ClassCastException, NumberFormatException {
        Object value = getObject(key);
        Double typedValue = 0.0D;
        if (value instanceof String[]) {
            String[] arrayOfStrings = (String[]) value;
            if (0 < arrayOfStrings.length) {
                typedValue = Double.parseDouble(arrayOfStrings[0]);
            }
        }
        else {
            if (value == null) {
                typedValue = 0.0D;
            }
            else if (value instanceof String) {
                typedValue = Double.valueOf((String) value);
            }
            else { // if (value instanceof Double) {
                // Because the JsonParser generates Double instances
                typedValue = (Double) value;
            }
        }
        return typedValue.doubleValue();
    }

    public String getString(String key) throws ClassCastException {
        Object value = getObject(key);
        String typedValue = null;
        if (value instanceof String[]) {
            String[] arrayOfStrings = (String[]) value;
            typedValue = arrayOfStrings.length == 0 ? null : arrayOfStrings[0];
        }
        else {
            typedValue = (String) value;
        }
        return typedValue;
    }

    private static Pattern escapedNewLinePattern = Pattern.compile("\\\\n");
    private static String newLine = "\n";

    public String getString(String key, boolean multiLine) throws ClassCastException {
        String value = getString(key);
        if (value != null && multiLine) {
            value = escapedNewLinePattern.matcher(value).replaceAll(newLine);
        }
        return value;
    }

    public JsonObject getJsonObject(String key) throws ClassCastException {
        Object value = getObject(key);
        if (value instanceof String[]) {
            throw new ClassCastException("Cannot extract a JsonObject from an array of String instance");
        }
        return (JsonObject) value;
    }

    public JsonArray getJsonArray(String key) throws ClassCastException {
        Object value = getObject(key);
        JsonArray typedValue = null;
        if (value instanceof String[]) {
            String[] arrayOfStrings = (String[]) value;
            typedValue = new GenericJsonArray();
            for (int i=0; i<arrayOfStrings.length; i++) {
                typedValue.add(arrayOfStrings[i]);
            }
        }
        else if (value instanceof String) {
            typedValue = new GenericJsonArray();
            typedValue.add((String) value);
        }
        else {
            typedValue = (JsonArray) value;
        }
        return typedValue;
    }

    public JsonException getJsonException(String key) throws ClassCastException {
        return (JsonException) getObject(key);
    }

    /** @see java.util.Map#put */
    protected void putObject(String key, Object object) {
        hashMap.put(key, object);
    }

    public void put(String key, boolean value) {
        put(key, value ? Boolean.TRUE : Boolean.FALSE);
    }

    public void put(String key, Boolean value) {
        putObject(key, value);
    }

    public void put(String key, long value) {
        put(key, Long.valueOf(value));
    }

    public void put(String key, Long value) {
        putObject(key, value);
    }

    public void put(String key, double value) {
        put(key, Double.valueOf(value));
    }

    public void put(String key, Double value) {
        putObject(key, value);
    }

    public void put(String key, String value) {
        put(key, value, false);
    }

    private static Pattern newLinePattern = Pattern.compile("\r\n|\n");
    private static String escapedNewLine = "\\\\n";

    public void put(String key, String value, boolean multiLine) {
        if (value != null && multiLine) {
            value = newLinePattern.matcher(value).replaceAll(escapedNewLine);
        }
        putObject(key, (Object) value);
    }

    public void put(String key, JsonObject value) {
        putObject(key, (Object) value);
    }

    public void put(String key, JsonArray value) {
        putObject(key, (Object) value);
    }

    public void put(String key, JsonException value) {
        putObject(key, (Object) value);
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

    public OutputStream toStream(OutputStream out, boolean isFollowed) throws IOException {
        Iterator<String> it = hashMap.keySet().iterator();
        JsonSerializer.startObject(out);
        while (it.hasNext()) {
            String key = it.next();
            Object value = getObject(key);
            if (value instanceof Boolean) {
                JsonSerializer.toStream(key, ((Boolean) value).booleanValue(), out, it.hasNext());
            }
            else if (value instanceof Long) {
                JsonSerializer.toStream(key, ((Long) value).longValue(), out, it.hasNext());
            }
            else if (value instanceof Double) {
                JsonSerializer.toStream(key, ((Double) value).doubleValue(), out, it.hasNext());
            }
            else if (value instanceof String) {
                JsonSerializer.toStream(key, (String) value, out, it.hasNext());
            }
            else if (value instanceof JsonObject) {
                JsonSerializer.introduceComplexValue(key, out);
                ((JsonObject) value).toStream(out, it.hasNext());
            }
            else if (value instanceof JsonArray) {
                JsonSerializer.introduceComplexValue(key, out);
                ((JsonArray) value).toStream(out, it.hasNext());
            }
        }
        JsonSerializer.endObject(out, isFollowed);
        return out;
    }
}
