package twitter4j;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "deprecation", "serial" })
public class MockTwitter extends Twitter {

    private String from;

    public MockTwitter(String from) {
        this.from = from;
    }

    @Override
    public String getScreenName() {
        return from;
    }

    private String dmTo;
    private List<String> dmText = new ArrayList<String>();

    @Override
    public DirectMessage sendDirectMessage(String screenName, String text) throws TwitterException {
        dmTo = screenName;
        dmText.add(text);
        return new MockDirectMessage(from, dmTo, text);
    }

    String getDmTo() { return dmTo; }
    String getLastDm() { return dmText.get(dmText.size() - 1); }
    String getDm(int index) { return dmText.get(index); }
    int getDmNb() { return dmText.size(); }

    private String pubTo;
    private List<String> pubText = new ArrayList<String>();

    @Override
    public Status updateStatus(String text) throws TwitterException {
        pubTo = getScreenName();
        pubText.add(text);
        return new MockStatus(from, text);
    }

    String getPubTo() { return pubTo; }
    String getLastPub() { return pubText.get(pubText.size() - 1); }
    String getPub(int index) { return pubText.get(index); }
    int getPubNb() { return pubText.size(); }
}
