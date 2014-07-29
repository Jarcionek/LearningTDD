package system;

public class Application {

    private final NewsFeedReader newsFeedReader;

    public Application(NewsFeedReader newsFeedReader) {
        this.newsFeedReader = newsFeedReader;
    }

    public void post(UserId userId, Message message) {

    }

    public NewsFeed getNewsFeed(UserId userId) {
        return newsFeedReader.getNewsFeed(userId);
    }

}
