package system;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class SortingNewsFeedDbAdapter implements NewsFeedDbAdapter {

    private final NewsFeedDbAdapter delegate;

    public SortingNewsFeedDbAdapter(NewsFeedDbAdapter delegate) {
        this.delegate = delegate;
    }

    @Override
    public NewsFeed getNewsFeed(UserId userId) {
        NewsFeed newsFeed = delegate.getNewsFeed(userId);

        List<Message> messages = newArrayList(newsFeed.getMessages());

        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                long t1 = o1.getTimestamp();
                long t2 = o2.getTimestamp();
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
