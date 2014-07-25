package main;

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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    private static final int ANY_TIME = 10;
    private static final UserId ANY_USER_ID = new UserId(5);

    @Mock private NewsFeedReader newsFeedReader;

    private Application application;

    @Before
    public void setup() {
        application = new Application(newsFeedReader);
    }

    @Test
    public void delegatesQueryToNewsFeedReader() {
        List<Message> expectedNewsFeed = Arrays.asList(new Message(ANY_TIME, "any message"));

        when(newsFeedReader.getNewsFeed(ANY_USER_ID)).thenReturn(expectedNewsFeed);

        List<Message> actualNewsFeed = application.getNewsFeed(ANY_USER_ID);

        assertThat(actualNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

}