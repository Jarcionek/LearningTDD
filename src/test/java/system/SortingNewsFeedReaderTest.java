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
public class SortingNewsFeedReaderTest {

    private static final UserId USER_ID = new UserId(853);

    @Mock private NewsFeedReader delegate;

    private SortingNewsFeedReader sortingNewsFeedReader;

    @Before
    public void setup() {
        sortingNewsFeedReader = new SortingNewsFeedReader(delegate);
    }

    @Test
    public void sortsRetrievedNewsFeed() {
        NewsFeed expectedNewsFeed = new NewsFeed(msg(1), msg(2), msg(3), msg(4));

        when(delegate.getNewsFeed(USER_ID)).thenReturn(new NewsFeed(msg(3), msg(1), msg(4), msg(2)));

        NewsFeed sortedNewsFeed = sortingNewsFeedReader.getNewsFeed(USER_ID);

        assertThat(sortedNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

    @Test
    public void delegatesPostQuery() {
        Message message = new Message(99999, "any message");

        sortingNewsFeedReader.post(USER_ID, message);

        verify(delegate, times(1)).post(USER_ID, message);
    }

    @Test
    public void delegatesDeleteQuery() {
        sortingNewsFeedReader.removeAll(USER_ID);

        verify(delegate, times(1)).removeAll(USER_ID);
    }


    private static Message msg(int timestamp) {
        return new Message(timestamp, "" + timestamp);
    }

}