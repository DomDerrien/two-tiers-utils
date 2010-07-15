package twitter4j.internal.http;

import java.io.IOException;
import twitter4j.internal.http.HttpResponse;

public class MockHttpResponse extends HttpResponse {

    public MockHttpResponse() throws IOException {
        super();
    }

    @Override
    public String getResponseHeader(String key) {
        return null;
    }

    @Override
    public void disconnect() throws IOException {
        // TODO Auto-generated method stub

    }
}
