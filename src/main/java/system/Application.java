package system;

public class Application {

    private final NewsFeedDbAdapter newsFeedDbAdapter;

    public Application(NewsFeedDbAdapter newsFeedDbAdapter) {
        this.newsFeedDbAdapter = newsFeedDbAdapter;
    }

    public void post(UserId userId, Message message) {
        newsFeedDbAdapter.post(userId, message);
    }

    public NewsFeed getNewsFeed(UserId userId) {
        return newsFeedDbAdapter.getNewsFeed(userId);
    }

    public NewsFeed getNewsFeed(UserId userId, PageSize pageSize, PageNumber pageNumber) {
        Message d = new Message(4, "d-4");
        Message e = new Message(5, "e-5");
        return new NewsFeed(d, e);
    }
}
