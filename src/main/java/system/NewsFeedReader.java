package system;

public interface NewsFeedReader {

    NewsFeed getNewsFeed(UserId userId);

    void post(UserId userId, Message message);

    void removeAll(UserId userId);

}
