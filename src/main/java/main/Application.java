package main;

import java.util.List;

public class Application {

    private final NewsFeedReader newsFeedReader;

    public Application(NewsFeedReader newsFeedReader) {
        this.newsFeedReader = newsFeedReader;
    }

    public void post(UserId userId, Message msg) {
        newsFeedReader.post(userId, msg);
    }

    public List<Message> getNewsFeed(UserId userId) {
        return newsFeedReader.getNewsFeed(userId);
    }
}
