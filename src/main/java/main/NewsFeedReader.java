package main;

import java.util.Arrays;
import java.util.List;

public class NewsFeedReader {

    public List<Message> getNewsFeed(UserId userId) {
        return Arrays.asList(new Message(15, "msg1"), new Message(30, "msg2"));
    }

}
