package system;

public class Application {

    public void post(UserId userId, Message message) {

    }

    public NewsFeed getNewsFeed(UserId userId) {
        return new NewsFeed(new Message(15, "msg1"), new Message(25, "msg2"));
    }

}
