package main;

import java.util.List;

public class Application implements NewsFeedEndpoints {

    private final NewsFeedReader newsFeedReader;
    private final NewsFeedReducer newsFeedReducer;
    private final NewsFeedPaginator newsFeedPaginator;

    public Application(NewsFeedReader newsFeedReader, NewsFeedReducer newsFeedReducer, NewsFeedPaginator newsFeedPaginator) {
        this.newsFeedReader = newsFeedReader;
        this.newsFeedReducer = newsFeedReducer;
        this.newsFeedPaginator = newsFeedPaginator;
    }

    @Override
    public void post(UserId userId, Message msg) {
        newsFeedReader.post(userId, msg);
    }

    @Override
    public List<Message> getNewsFeed(UserId userId) {
        return newsFeedReader.getNewsFeed(userId);
    }

    @Override
    @SuppressWarnings("UnnecessaryLocalVariable")
    public List<Message> getNewsFeed(UserId userId, int size) {
        List<Message> entireNewsFeed = newsFeedReader.getNewsFeed(userId);

        List<Message> reducedNewsFeed = newsFeedReducer.reduce(entireNewsFeed, size);

        return reducedNewsFeed;
    }

    @Override
    @SuppressWarnings("UnnecessaryLocalVariable")
    public List<Message> getNewsFeed(UserId userId, int pageNumber, int pageSize) {
        List<Message> entireNewsFeed = newsFeedReader.getNewsFeed(userId);

        List<Message> paginatedNewsFeed = newsFeedPaginator.paginate(entireNewsFeed, pageNumber, pageSize);

        return paginatedNewsFeed;
    }

}
