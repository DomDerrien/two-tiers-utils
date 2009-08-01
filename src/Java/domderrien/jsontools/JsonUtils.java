package domderrien.jsontools;

import java.util.List;
import java.util.Map;

public class JsonUtils {

    /**
     * Serialize the given map of <code>TransfertObject</code> instance to be send on the wire
     * 
     * @param objects Map of serialize-able objects
     * return object ready to be serialized
     */
    public static JsonObject toJson(Map<String, ?> objects) {
    	JsonObject out = new GenericJsonObject();
    	for (String key: objects.keySet()) {
    		out.put(key, ((TransferObject) objects.get(key)).toJson());
    	}
        return out;
    }

    /**
     * Serialize the given list of <code>TransfertObject</code> instance to be send on the wire
     * 
     * @param objects List of serialize-able objects
     * return object ready to be serialized
     */
    public static JsonArray toJson(List<?> objects) {
    	JsonArray out = new GenericJsonArray();
    	for (Object object: objects) {
    		out.add(((TransferObject) object).toJson());
    	}
    	return out;
    }

}
