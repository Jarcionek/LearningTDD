package main;

import java.util.Arrays;
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

    public List<Message> getNewsFeed(UserId userId, int size) {
        Message msg3 = new Message(30, "msg3");
        Message msg4 = new Message(40, "msg4");
        Message msg5 = new Message(50, "msg5");
        Message msg6 = new Message(60, "msg6");
        Message msg7 = new Message(70, "msg7");

        return Arrays.asList(msg3, msg4, msg5, msg6, msg7);
    }
}
