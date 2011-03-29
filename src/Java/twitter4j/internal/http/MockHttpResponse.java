package twitter4j.internal.http;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import twitter4j.internal.http.HttpResponse;

public class MockHttpResponse extends HttpResponse {

    public MockHttpResponse() throws IOException {
        super();
    }

    @Override public String getResponseHeader(String key) { return null; }
    @Override public void disconnect() throws IOException { }
    @Override public Map<String, List<String>> getResponseHeaderFields() { return null; }
}
