package com.google.appengine.api.urlfetch;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MockHTTPResponse extends HTTPResponse {

    private String content;
    private List<HTTPHeader> headers;
    private URL finalURL;

    public MockHTTPResponse(int responseCode, String content) {
        super(responseCode);
        this.content = content;
    }

    @Override
    public byte[] getContent() {
        return content.getBytes();
    }

    @Override
    public void setContent(byte[] content) {
        this.content = new String(content);
    }

    @Override
    public void addHeader(String name, String value) {
        getHeaders().add(new HTTPHeader(name, value));
    }

    @Override
    public List<HTTPHeader> getHeaders() {
        if (headers == null) {
            headers = new ArrayList<HTTPHeader>();
        }
        return headers;
    }

    @Override
    public URL getFinalUrl() {
        return finalURL;
    }

    @Override
    public void setFinalUrl(URL finalUrl) {
        this.finalURL = finalUrl;
    }
}
