package system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortingNewsFeedReader implements NewsFeedReader {

    private final NewsFeedReader delegate;

    public SortingNewsFeedReader(NewsFeedReader delegate) {
        this.delegate = delegate;
    }

    @Override
    public NewsFeed getNewsFeed(UserId userId) {
        NewsFeed newsFeed = delegate.getNewsFeed(userId);

        List<Message> messages = new ArrayList<Message>(newsFeed.getMessages());

        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message msg1, Message msg2) {
                long t1 = msg1.getTimestamp();
                long t2 = msg2.getTimestamp();
                return t1 > t2 ? 1 : t2 > t1 ? -1 : 0;
            }
        });

        return new NewsFeed(messages);
    }

    @Override
    public void post(UserId userId, Message message) {
        delegate.post(userId, message);
    }

    @Override
    public void removeAll(UserId userId) {
        delegate.removeAll(userId);
    }

}
