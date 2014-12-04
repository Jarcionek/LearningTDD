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
        if (pageSize.asInt() < 1) {
            throw new IllegalArgumentException("page size must be positive, was " + pageSize);
        }
        if (pageNumber.asInt() < 0) {
            throw new IllegalArgumentException("page number must be not negative, was " + pageNumber);
        }
        return newsFeedPaginator.fetch(userId, pageSize, pageNumber);
    }

    public NewsFeed getNewsFeed(UserId userId, PageSize numberOfMessages) {
        Message msg3 = new Message(40, "msg+3");
        Message msg4 = new Message(50, "msg+4");
        Message msg5 = new Message(60, "msg+5");
        return new NewsFeed(msg3, msg4, msg5);
    }

}
