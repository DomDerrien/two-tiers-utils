package twitter4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import javamocks.io.MockInputStream;

import twitter4j.http.HttpResponse;

public class MockHttpResponse extends HttpResponse {

    public MockHttpResponse(HttpURLConnection con) throws IOException {
        super(new HttpURLConnection(null) {
            // Virtual methods
            @Override public void disconnect() { }
            @Override public boolean usingProxy() { return false; }
            @Override public void connect() throws IOException { }

            // Existing methods
            @Override public int getResponseCode() { return 200; } // HTTP_OK
            @Override public InputStream getErrorStream() { return null; }
            @Override public InputStream getInputStream() { return new MockInputStream(); }
            @Override public String getContentEncoding() { return null; }
        });
    }

    @Override
    public String getResponseHeader(String key) {
        return null;
    }
}
