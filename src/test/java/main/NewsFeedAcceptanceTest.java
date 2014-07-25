package main;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class NewsFeedAcceptanceTest {

    private static final int TIME_1 = 15;
    private static final int TIME_2 = 30;

    private final UserId userId = new UserId(12345);

    private final Application application = Config.getApplication();

    @Test
    public void returnsAllPostedMessages() {
        application.post(userId, new Message(TIME_1, "msg1"));
        application.post(userId, new Message(TIME_2, "msg2"));

        List<Message> newsFeed = application.getNewsFeed(userId);

        assertThat(newsFeed, is(sameBeanAs(Arrays.asList(
                new Message(TIME_1, "msg1"),
                new Message(TIME_2, "msg2")))));
    }

}