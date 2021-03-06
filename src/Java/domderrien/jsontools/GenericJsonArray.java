package domderrien.jsontools;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class GenericJsonArray implements JsonArray, Serializable {

    private static final long serialVersionUID = 164580948320986452L;
    protected List<Object> arrayList = null;

    /**
     * Default constructor, useful to generate the array to be sent to the browser
     */
    public GenericJsonArray() {
        arrayList = new ArrayList<Object>();
    }

    /**
     * Constructor made of an existing list
     */
    public GenericJsonArray(List<Object> list) {
        arrayList = list;
    }

    /**
     * Constructor made of an existing array
     */
    public GenericJsonArray(Object[] list) {
        int limit = list.length;
        arrayList = new ArrayList<Object>(limit);
        for (int i = 0; i < limit; ++i) {
            arrayList.add(list[i]);
        }
    }

    @Override
    public String toString() {
        return toString(0);
    }

    /**
     * Local  toString() providing a nicely indented view of the object, with one attribute per line
     * @param index Depth of the recursive call
     * @return Nicely formatted string
     */
    protected String toString(int index) {
        StringBuilder offset = new StringBuilder("  ");
        for (int i = 0; i < index; ++ i) {
            offset.append("  ");
        }
        StringBuilder output = new StringBuilder();
        output.append("JsonArray: ").append("[");
        int length = size();
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                output.append(", ");
            }
            output.append("\n").append(offset);
            Object value = arrayList.get(i);
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
        output.append("\n").append(offset.substring(index * 2)).append("]");
        return output.toString();
    }

    public int size() {
        return arrayList.size();
    }

    public List<Object> getList() {
        return arrayList;
    }

    public boolean isInstance(int index, Class<?> clazz) {
        Object object = getObject(index);
        if (object == null) {
            return false;
        }
        return clazz.isInstance(object);
    }

    /** @see java.util.List#get(int)  */
    protected Object getObject(int index) {
        return arrayList.get(index);
    }

    public boolean getBoolean(int index) {
        Boolean value = (Boolean) getObject(index);
        return Boolean.TRUE.equals(value);
    }

    public long getLong(int index) {
        Object value = getObject(index);
        if (value instanceof Double) {
            return ((Double) getObject(index)).longValue();
        }
        return ((Long) getObject(index)).longValue();
    }

    public double getDouble(int index) {
        return ((Double) getObject(index));
    }

    public String getString(int index) {
        return (String) getObject(index);
    }

    private static Pattern escapedNewLinePattern = Pattern.compile("\\\\n");
    private static String newLine = "\n";

    public String getString(int index, boolean multiLine) {
        String value = getString(index);
        if (value != null && multiLine) {
            value = escapedNewLinePattern.matcher(value).replaceAll(newLine);
        }
        return value;
    }

    public JsonObject getJsonObject(int index) {
        return (JsonObject) getObject(index);
    }

    public JsonArray getJsonArray(int index) {
        return (JsonArray) getObject(index);
    }

    public JsonException getJsonException(int index) {
        return (JsonException) getObject(index);
    }

    /** @see java.util.List#add  */
    protected void addObject(Object object) {
        arrayList.add(object);
    }

    public void add(boolean value) {
        add(value ? Boolean.TRUE : Boolean.FALSE);
    }

    public void add(Boolean value) {
        addObject(value);
    }

    public void add(long value) {
        add(Long.valueOf(value));
    }

    public void add(Long value) {
        addObject(value);
    }

    public void add(double value) {
        add(Double.valueOf(value));
    }

    public void add(Double value) {
        addObject(value);
    }

    public void add(String value) {
        add(value, false);
    }

    private static Pattern newLinePattern = Pattern.compile("\r\n|\n");
    private static String escapedNewLine = "\\\\n";

    public void add(String value, boolean multiLine) {
        if (value != null && multiLine) {
            value = newLinePattern.matcher(value).replaceAll(escapedNewLine);
        }
        addObject(value);
    }

    public void add(JsonObject value) {
        addObject(value);
    }

    public void add(JsonArray value) {
        addObject(value);
    }

    public void add(JsonException value) {
        addObject(value);
    }

    /** @see java.util.List#set */
    protected void setObject(int index, Object object) {
        if (index == arrayList.size()) {
            arrayList.add(object);
            return;
        }
        arrayList.set(index, object);
    }

    public void set(int index, boolean value) {
        set(index, value ? Boolean.TRUE : Boolean.FALSE);
    }

    public void set(int index, Boolean value) {
        setObject(index, value);
    }

    public void set(int index, long value) {
        set(index, Long.valueOf(value));
    }

    public void set(int index, Long value) {
        setObject(index, value);
    }

    public void set(int index, double value) {
        set(index, Double.valueOf(value));
    }

    public void set(int index, Double value) {
        setObject(index, value);
    }

    public void set(int index, String value) {
        setObject(index, value);
    }

    public void set(int index, JsonObject value) {
        setObject(index, value);
    }

    public void set(int index, JsonArray value) {
        setObject(index, value);
    }

    public void set(int index, JsonException value) {
        setObject(index, value);
    }

    public void remove(int index) {
        arrayList.remove(index);
    }

    public void remove(Object value) {
        arrayList.remove(value);
    }

    public void removeAll() {
        arrayList.clear();
    }

    public void append(JsonArray additionalValues) {
        arrayList.addAll(additionalValues.getList());
    }

    public OutputStream toStream(OutputStream out, boolean isFollowed) throws IOException {
        Iterator<Object> it = arrayList.iterator();
        JsonSerializer.startArray(out);
        while (it.hasNext()) {
            Object value = it.next();
            if (value instanceof Boolean) {
                JsonSerializer.toStream(((Boolean) value).booleanValue(), out, it.hasNext());
            }
            else if (value instanceof Long) {
                JsonSerializer.toStream(((Long) value).longValue(), out, it.hasNext());
            }
            else if (value instanceof Double) {
                JsonSerializer.toStream(((Double) value).doubleValue(), out, it.hasNext());
            }
            else if (value instanceof String) {
                JsonSerializer.toStream((String) value, out, it.hasNext());
            }
            else if (value instanceof JsonObject) {
                ((JsonObject) value).toStream(out, it.hasNext());
            }
            else if (value instanceof JsonArray) {
                ((JsonArray) value).toStream(out, it.hasNext());
            }
        }
        JsonSerializer.endArray(out, isFollowed);
        return out;
    }
}
