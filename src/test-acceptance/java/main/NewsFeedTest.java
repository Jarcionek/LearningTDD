package main;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class NewsFeedTest {

    private final UserId userId = new UserId(12345);

    private final Application application = Config.getApplication();

    @Test
    public void returnsAllPostedMessages() {
        Message msg1 = new Message(15, "msg1");
        Message msg2 = new Message(30, "msg2");
        Message msg3 = new Message(45, "msg3");
        Message msg4 = new Message(60, "msg4");
        Message msg5 = new Message(75, "msg5");

        application.post(userId, msg1);
        application.post(userId, msg2);
        application.post(userId, msg3);
        application.post(userId, msg4);
        application.post(userId, msg5);

        List<Message> newsFeed = application.getNewsFeed(userId);

        assertThat(newsFeed, is(sameBeanAs(Arrays.asList(msg1, msg2, msg3, msg4, msg5))));
    }

}