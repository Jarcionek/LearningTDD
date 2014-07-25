package main;

import java.util.List;

public class Application {

    private NewsFeedReader newsFeedReader;

    public Application(NewsFeedReader newsFeedReader) {
        this.newsFeedReader = newsFeedReader;
    }

    public void post(UserId userId, Message msg) {

    }

    public List<Message> getNewsFeed(UserId userId) {
        return newsFeedReader.getNewsFeed(userId);
    }
}
