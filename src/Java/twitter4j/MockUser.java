package twitter4j;

import java.net.URL;
import java.util.Date;

@SuppressWarnings("serial")
public class MockUser implements User {

    private int userId;
    private String userName;
    private String userScreenName;

    public MockUser() {
        this(0, "mock", "Mock User");
    }

    public MockUser(int userId, String userScreenName, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.userScreenName = userScreenName;
    }

    public Date getCreatedAt() { return null; }
    public String getDescription() { return null; }
    public int getFavouritesCount() { return 0; }
    public int getFollowersCount() { return 0; }
    public int getFriendsCount() { return 0; }
    public int getId() { return userId; }
    public String getLocation() { return null; }
    public String getName() { return userName; }
    public String getProfileBackgroundColor() { return null; }
    public String getProfileBackgroundImageUrl() { return null; }
    public URL getProfileImageURL() { return null; }
    public String getProfileLinkColor() { return null; }
    public String getProfileSidebarBorderColor() { return null; }
    public String getProfileSidebarFillColor() { return null; }
    public String getProfileTextColor() { return null; }
    public String getScreenName() { return userScreenName; }
    public Date getStatusCreatedAt() { return null; }
    public long getStatusId() { return 0; }
    public String getStatusInReplyToScreenName() { return null; }
    public long getStatusInReplyToStatusId() { return 0; }
    public int getStatusInReplyToUserId() { return 0; }
    public String getStatusSource() { return null; }
    public String getStatusText() { return null; }
    public int getStatusesCount() { return 0; }
    public String getTimeZone() { return null; }
    public URL getURL() { return null; }
    public int getUtcOffset() { return 0; }
    public boolean isGeoEnabled() { return false; }
    public boolean isProfileBackgroundTiled() { return false; }
    public boolean isProtected() { return false; }
    public boolean isStatusFavorited() { return false; }
    public boolean isStatusTruncated() { return false; }
    public boolean isVerified() { return false; }
    public RateLimitStatus getRateLimitStatus() { return null; }

    public String getLang() { return null; }
    public Status getStatus() { return null; }
    public boolean isContributorsEnabled() { return false; }
    public int compareTo(User o) { return 0; }
}
