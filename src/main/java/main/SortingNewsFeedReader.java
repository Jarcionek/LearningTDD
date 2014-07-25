package main;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortingNewsFeedReader implements NewsFeedReader {

    private final NewsFeedReader delegateNewsFeedReader;

    public SortingNewsFeedReader(NewsFeedReader delegateNewsFeedReader) {
        this.delegateNewsFeedReader = delegateNewsFeedReader;
    }

    @Override
    public List<Message> getNewsFeed(UserId userId) {
        List<Message> newsFeed = delegateNewsFeedReader.getNewsFeed(userId);
        Collections.sort(newsFeed, new Comparator<Message>() {
            @Override
            public int compare(Message msg1, Message msg2) {
                return msg1.getTime() > msg2.getTime() ? 1 : msg1.getTime() < msg2.getTime() ? -1 : 0;
            }
        });
        return newsFeed;
    }

    @Override
    public void post(UserId userId, Message msg) {
        delegateNewsFeedReader.post(userId, msg);
    }

}
