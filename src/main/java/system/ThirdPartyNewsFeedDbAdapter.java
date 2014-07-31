package system;

public class ThirdPartyNewsFeedDbAdapter implements NewsFeedDbAdapter {

    @Override
    public NewsFeed getNewsFeed(UserId userId) {
        return new NewsFeed(new Message(20, "msg1"), new Message(30, "msg2"));
    }
}
