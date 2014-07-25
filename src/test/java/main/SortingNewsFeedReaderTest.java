package main;

import com.shazam.shazamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SortingNewsFeedReaderTest {

    private static final UserId USER_ID = new UserId(672);

    @Mock private NewsFeedReader delegateNewsFeedReader;

    private NewsFeedReader sortingNewsFeedReader;

    @Before
    public void setup() {
        sortingNewsFeedReader = new SortingNewsFeedReader(delegateNewsFeedReader);
    }

    @Test
    public void returnsSortedMessagesReturnedByDelegate() {
        when(delegateNewsFeedReader.getNewsFeed(USER_ID)).thenReturn(
                Arrays.asList(msg(5), msg(4), msg(6), msg(3), msg(7), msg(2), msg(8))
        );

        List<Message> newsFeed = sortingNewsFeedReader.getNewsFeed(USER_ID);

        assertThat(newsFeed, is(sameBeanAs(
                Arrays.asList(msg(2), msg(3), msg(4), msg(5), msg(6), msg(7), msg(8))
        )));
    }

    @Test
    public void delegatesPost() {
        Message message = new Message(12345, "3");

        sortingNewsFeedReader.post(USER_ID, message);

        verify(delegateNewsFeedReader, times(1)).post(USER_ID, message);
    }

    private static Message msg(int time) {
        return new Message(time, "" + time);
    }

}