package twitter4j;

import java.util.Date;

@SuppressWarnings("serial")
public class MockDirectMessage implements DirectMessage {

    private long id;
    private User from;
    private User to;
    private String text;

    public MockDirectMessage(long id, User from, User to, String text) {
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

    @Override public Date getCreatedAt() { return null; }
    @Override public long getId() { return id; }
    @Override public User getRecipient() { return to; }
    @Override public int getRecipientId() { return to.getId(); }
    @Override public String getRecipientScreenName() { return to.getScreenName(); }
    @Override public User getSender() { return from; }
    @Override public int getSenderId() { return from.getId(); }
    @Override public String getSenderScreenName() { return from.getScreenName(); }
    @Override public String getText() { return text; }
    @Override public RateLimitStatus getRateLimitStatus() { return null;}
}
