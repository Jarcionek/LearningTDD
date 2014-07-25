package main;

import java.util.List;

public interface NewsFeedReader {

    List<Message> getNewsFeed(UserId userId);

    void post(UserId userId, Message msg);

}
