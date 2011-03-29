package twitter4j;

import java.net.URL;
import java.util.Date;

@SuppressWarnings("serial")
public class MockStatus implements Status {

    private String from;
    private String text;

    public MockStatus(String from, String text) {
        this.from = from;
        this.text = text;
    }

    @Override public Date getCreatedAt() { return null; }
    @Override public GeoLocation getGeoLocation() { return null; }
    @Override public long getId() { return 0; }
    @Override public String getInReplyToScreenName() { return from; }
    @Override public long getInReplyToStatusId() { return 0; }
    @Override public int getInReplyToUserId() { return 0; }
    @Override public Status getRetweetedStatus() { return null; }
    @Override public String getSource() { return null; }
    @Override public String getText() { return text; }
    @Override public User getUser() { return null; }
    @Override public boolean isFavorited() { return false; }
    @Override public boolean isRetweet() { return false; }
    @Override public boolean isTruncated() { return false; }
    @Override public RateLimitStatus getRateLimitStatus() { return null;}

    @Override public String[] getContributors() { return null; }
    @Override public Place getPlace() { return null; }
    @Override public int compareTo(Status o) { return 0; }

    @Override public Annotations getAnnotations() { return null; }
    @Override public HashtagEntity[] getHashtagEntities() { return null; }
    @Override public String[] getHashtags() { return null; }
    @Override public long getRetweetCount() { return 0; }
    @Override public URLEntity[] getURLEntities() { return null; }
    @Override public URL[] getURLs() { return null; }
    @Override public UserMentionEntity[] getUserMentionEntities() { return null; }
    @Override public User[] getUserMentions() { return null; }
    @Override public boolean isRetweetedByMe() { return false; }
}
