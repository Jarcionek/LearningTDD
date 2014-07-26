package main;

import java.util.Arrays;
import java.util.List;

public class Application {

    private final NewsFeedReader newsFeedReader;
    private final NewsFeedReducer newsFeedReducer;

    public Application(NewsFeedReader newsFeedReader, NewsFeedReducer newsFeedReducer) {
        this.newsFeedReader = newsFeedReader;
        this.newsFeedReducer = newsFeedReducer;
    }

    public void post(UserId userId, Message msg) {
        newsFeedReader.post(userId, msg);
    }

    public List<Message> getNewsFeed(UserId userId) {
        return newsFeedReader.getNewsFeed(userId);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public List<Message> getNewsFeed(UserId userId, int size) {
        List<Message> entireNewsFeed = newsFeedReader.getNewsFeed(userId);

        List<Message> reducedNewsFeed = newsFeedReducer.reduce(entireNewsFeed, size);

        return reducedNewsFeed;
    }

    public List<Message> getNewsFeed(UserId userId, int pageNumber, int pageSize) {
        return Arrays.asList(new Message(4, "msg+4"));
    }
}
