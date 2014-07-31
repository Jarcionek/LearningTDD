package system;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SortingNewsFeedDbAdapterTest {

    private UserId userId = new UserId(643);
    private Message message = new Message(703, "abc");

    @Mock private NewsFeedDbAdapter delegate;
    private SortingNewsFeedDbAdapter sortingNewsFeedDbAdapter;

    @Before
    public void setup() {
        sortingNewsFeedDbAdapter = new SortingNewsFeedDbAdapter(delegate);
    }

    @Test
    public void delegatesPostQuery() {
        sortingNewsFeedDbAdapter.post(userId, message);

        verify(delegate, times(1)).post(userId, message);
    }

    @Test
    public void delegatesRemoveAllQuery() {
        sortingNewsFeedDbAdapter.removeAll(userId);

        verify(delegate, times(1)).removeAll(userId);
    }

    @Test
    public void returnsSortedNewsFeedReceivedFromDelegate() {
        NewsFeed unsortedNewsFeed = new NewsFeed(msg(5), msg(4), msg(6), msg(3), msg(7));
        NewsFeed sortedNewsFeed = new NewsFeed(msg(3), msg(4), msg(5), msg(6), msg(7));

        when(delegate.getNewsFeed(userId)).thenReturn(unsortedNewsFeed);

        NewsFeed actualNewsFeed = sortingNewsFeedDbAdapter.getNewsFeed(userId);

        assertThat(actualNewsFeed, is(sameBeanAs(sortedNewsFeed)));
    }


    private static Message msg(int timestamp) {
        return new Message(timestamp, "" + timestamp);
    }

}