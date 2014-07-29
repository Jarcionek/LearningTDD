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

    private static final UserId USER_ID = new UserId(3);

    @Mock private NewsFeedReader newsFeedReader;

    private Application application;

    @Before
    public void setup() {
        application = new Application(newsFeedReader);
    }

    @Test
    public void delegatesGetQueryToNewsFeedReader() {
        NewsFeed expectedNewsFeed = new NewsFeed(new Message(90, "M1"), new Message(100, "M2"));

        when(newsFeedReader.getNewsFeed(USER_ID)).thenReturn(expectedNewsFeed);

        NewsFeed actualNewsFeed = application.getNewsFeed(USER_ID);

        assertThat(actualNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

    @Test
    public void delegatesPostQueryToNewsFeedReader() {
        Message message = new Message(5632, "anything");

        application.post(USER_ID, message);

        verify(newsFeedReader, times(1)).post(USER_ID, message);
    }

}