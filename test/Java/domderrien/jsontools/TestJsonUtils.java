package domderrien.jsontools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestJsonUtils {

    @Test
    public void testConstructor() {
        new JsonUtils();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void toJsonFromMap() {
        Map in = new HashMap();
        in.put("a", new TransferObject() {
            public TransferObject fromJson(JsonObject in) throws ParseException {
                return null;
            }
            public JsonObject toJson() {
                JsonObject out = new GenericJsonObject();
                out.put("b", "c");
                return out;
            }
        });
        JsonObject out = JsonUtils.toJson(in);
        assertNotNull(out);
        assertEquals(1, out.size());
        assertEquals("c", out.getJsonObject("a").getString("b"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void toJsonFromList() {
        List in = new ArrayList<String>();
        in.add(new TransferObject() {
            public TransferObject fromJson(JsonObject in) throws ParseException {
                return null;
            }
            public JsonObject toJson() {
                JsonObject out = new GenericJsonObject();
                out.put("b", "c");
                return out;
            }
        });
        JsonArray out = JsonUtils.toJson(in);
        assertNotNull(out);
        assertEquals(1, out.size());
        assertEquals("c", out.getJsonObject(0).getString("b"));
    }
}
