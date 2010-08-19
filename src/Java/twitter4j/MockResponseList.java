package twitter4j;

import twitter4j.internal.http.HttpResponse;

@SuppressWarnings("serial")
public class MockResponseList<T extends TwitterResponse> extends ResponseListImpl<T> {

    public MockResponseList(int size, HttpResponse res) {
        super(size, res);
    }
}
