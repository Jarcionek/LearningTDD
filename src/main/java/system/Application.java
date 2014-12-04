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
            throw new IllegalArgumentException("page size must be positive, was " + pageSize.asInt());
        }
        if (pageNumber.asInt() < 0) {
            throw new IllegalArgumentException("page number must be not negative, was " + pageNumber.asInt());
        }
        return newsFeedPaginator.fetch(userId, pageSize, pageNumber);
    }

    public NewsFeed getNewsFeed(UserId userId, PageSize numberOfMessages) {
        if (numberOfMessages.asInt() < 1) {
            throw new IllegalArgumentException("number of messages must be positive, was " + numberOfMessages.asInt());
        }
        return newsFeedPaginator.fetch(userId, numberOfMessages, new PageNumber(0));
    }

}
