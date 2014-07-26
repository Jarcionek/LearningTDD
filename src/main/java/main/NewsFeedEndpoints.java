package main;

import java.util.List;

public interface NewsFeedEndpoints {

    void post(UserId userId, Message msg);

    List<Message> getNewsFeed(UserId userId);

    List<Message> getNewsFeed(UserId userId, int size);

    List<Message> getNewsFeed(UserId userId, int pageNumber, int pageSize);

}
