package twitter4j;

import twitter4j.http.HttpResponse;

@SuppressWarnings("serial")
public class MockResponseList<T extends TwitterResponse> extends ResponseList<T> {

    public MockResponseList(int size, HttpResponse res) {
        super(size, res);
    }
}
