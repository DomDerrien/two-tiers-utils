package twitter4j;

import java.util.Date;

@SuppressWarnings("serial")
public class MockDirectMessage implements DirectMessage {

    private int id;
    private User from;
    private User to;
    private String text;

    public MockDirectMessage(int id, User from, User to, String text) {
        this(from, to, text);
        this.id = id;
    }

    public MockDirectMessage(User from, User to, String text) {
        this(to, text);
        this.from = from;
    }

    public MockDirectMessage(User to, String text) {
        this.to = to;
        this.text = text;
    }

    public MockDirectMessage(String from, String to, String text) {
        this(new MockUser(1111, to, to), text);
        this.from = new MockUser(2222, from, from);
    }

    public Date getCreatedAt() { return null; }
    public int getId() { return id; }
    public User getRecipient() { return to; }
    public int getRecipientId() { return to.getId(); }
    public String getRecipientScreenName() { return to.getScreenName(); }
    public User getSender() { return from; }
    public int getSenderId() { return from.getId(); }
    public String getSenderScreenName() { return from.getScreenName(); }
    public String getText() { return text; }
    public RateLimitStatus getRateLimitStatus() { return null;}
}
