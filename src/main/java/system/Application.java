package system;

public class Application {

    private final NewsFeedDbAdapter newsFeedDbAdapter;
    private final NewsFeedPaginator newsFeedPaginator;

    public Application(NewsFeedDbAdapter newsFeedDbAdapter, NewsFeedPaginator newsFeedPaginator) {
        this.newsFeedDbAdapter = newsFeedDbAdapter;
        this.newsFeedPaginator = newsFeedPaginator;
    }

    public void post(UserId userId, Message message) {
        newsFeedDbAdapter.post(userId, message);
    }

    public NewsFeed getNewsFeed(UserId userId) {
        return newsFeedDbAdapter.getNewsFeed(userId);
    }

    public NewsFeed getNewsFeed(UserId userId, PageSize pageSize, PageNumber pageNumber) {
        return newsFeedPaginator.fetch(userId, pageSize, pageNumber);
    }
}
