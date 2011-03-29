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

    @Override public Date getCreatedAt() { return null; }
    @Override public String getDescription() { return null; }
    @Override public int getFavouritesCount() { return 0; }
    @Override public int getFollowersCount() { return 0; }
    @Override public int getFriendsCount() { return 0; }
    @Override public int getId() { return userId; }
    @Override public String getLocation() { return null; }
    @Override public String getName() { return userName; }
    @Override public String getProfileBackgroundColor() { return null; }
    @Override public String getProfileBackgroundImageUrl() { return null; }
    @Override public URL getProfileImageURL() { return null; }
    @Override public String getProfileLinkColor() { return null; }
    @Override public String getProfileSidebarBorderColor() { return null; }
    @Override public String getProfileSidebarFillColor() { return null; }
    @Override public String getProfileTextColor() { return null; }
    @Override public String getScreenName() { return userScreenName; }
    @Override public Date getStatusCreatedAt() { return null; }
    @Override public long getStatusId() { return 0; }
    @Override public String getStatusInReplyToScreenName() { return null; }
    @Override public long getStatusInReplyToStatusId() { return 0; }
    @Override public int getStatusInReplyToUserId() { return 0; }
    @Override public String getStatusSource() { return null; }
    @Override public String getStatusText() { return null; }
    @Override public int getStatusesCount() { return 0; }
    @Override public String getTimeZone() { return null; }
    @Override public URL getURL() { return null; }
    @Override public int getUtcOffset() { return 0; }
    @Override public boolean isGeoEnabled() { return false; }
    @Override public boolean isProfileBackgroundTiled() { return false; }
    @Override public boolean isProtected() { return false; }
    @Override public boolean isStatusFavorited() { return false; }
    @Override public boolean isStatusTruncated() { return false; }
    @Override public boolean isVerified() { return false; }
    @Override public RateLimitStatus getRateLimitStatus() { return null; }

    @Override public String getLang() { return null; }
    @Override public Status getStatus() { return null; }
    @Override public boolean isContributorsEnabled() { return false; }
    @Override public int compareTo(User o) { return 0; }

    @Override public int getListedCount() { return 0; }
    @Override public boolean isFollowRequestSent() { return false; }
    @Override public boolean isProfileUseBackgroundImage() { return false; }
    @Override public boolean isShowAllInlineMedia() { return false; }
    @Override public boolean isTranslator() { return false; }
}
