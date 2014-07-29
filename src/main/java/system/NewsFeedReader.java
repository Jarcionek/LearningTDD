package system;

public class NewsFeedReader {

    public NewsFeed getNewsFeed(UserId userId) {
        return new NewsFeed(new Message(15, "msg1"), new Message(25, "msg2"));
    }

}
