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
public class ApplicationTest {

    @Mock private NewsFeedDbAdapter newsFeedDbAdapter;

    private UserId userId = new UserId(987);

    private Application application;

    @Before
    public void setup() {
        application = new Application(newsFeedDbAdapter);
    }

    @Test
    public void delegatesGetQueryToNewsFeedDbAdapter() {
        NewsFeed expectedNewsFeed = new NewsFeed(new Message(1, "anything"));
        when(newsFeedDbAdapter.getNewsFeed(userId)).thenReturn(expectedNewsFeed);

        NewsFeed actualNewsFeed = application.getNewsFeed(userId);

        assertThat(actualNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

    @Test
    public void delegatesPostQueryToNewsFeedDbAdapter() {
        Message message = new Message(32323, "xxx");

        application.post(userId, message);

        verify(newsFeedDbAdapter, times(1)).post(userId, message);
    }

}