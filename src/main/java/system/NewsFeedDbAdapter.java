package system;

public interface NewsFeedDbAdapter {

    NewsFeed getNewsFeed(UserId userId);

    void post(UserId userId, Message message);

    void removeAll(UserId userId);
}
