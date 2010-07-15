package twitter4j;

import java.util.Date;

@SuppressWarnings("serial")
public class MockStatus implements Status {

    private String from;
    private String text;

    public MockStatus(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public Date getCreatedAt() { return null; }
    public GeoLocation getGeoLocation() { return null; }
    public long getId() { return 0; }
    public String getInReplyToScreenName() { return from; }
    public long getInReplyToStatusId() { return 0; }
    public int getInReplyToUserId() { return 0; }
    public Status getRetweetedStatus() { return null; }
    public String getSource() { return null; }
    public String getText() { return text; }
    public User getUser() { return null; }
    public boolean isFavorited() { return false; }
    public boolean isRetweet() { return false; }
    public boolean isTruncated() { return false; }
    public RateLimitStatus getRateLimitStatus() { return null;}

    public String[] getContributors() { return null; }
    public Place getPlace() { return null; }
    public int compareTo(Status o) { return 0; }
}
