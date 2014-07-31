package system;

public class Application {

    private final NewsFeedDbAdapter newsFeedDbAdapter;

    public Application(NewsFeedDbAdapter newsFeedDbAdapter) {
        this.newsFeedDbAdapter = newsFeedDbAdapter;
    }

    public void post(UserId userId, Message message) {

    }

    public NewsFeed getNewsFeed(UserId userId) {
        return newsFeedDbAdapter.getNewsFeed(userId);
    }

}
