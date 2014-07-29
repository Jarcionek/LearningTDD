package system;

public class SortingNewsFeedReader implements NewsFeedReader {

    public SortingNewsFeedReader(NewsFeedReader delegate) {

    }

    @Override
    public NewsFeed getNewsFeed(UserId userId) {
        return null;
    }

    @Override
    public void post(UserId userId, Message message) {

    }

    @Override
    public void removeAll(UserId userId) {

    }

}
