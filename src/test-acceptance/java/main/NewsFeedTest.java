package main;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class NewsFeedTest {

    private final Application application = Config.getApplication();

    @Test
    public void returnsAllPostedMessages() {
        final UserId userId = new UserId(5001);

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

    @Test
    public void returnsLastFivePostedMessages() {
        final UserId userId = new UserId(5002);

        Message msg1 = new Message(10, "msg1");
        Message msg2 = new Message(20, "msg2");
        Message msg3 = new Message(30, "msg3");
        Message msg4 = new Message(40, "msg4");
        Message msg5 = new Message(50, "msg5");
        Message msg6 = new Message(60, "msg6");
        Message msg7 = new Message(70, "msg7");

        application.post(userId, msg1);
        application.post(userId, msg2);
        application.post(userId, msg3);
        application.post(userId, msg4);
        application.post(userId, msg5);
        application.post(userId, msg6);
        application.post(userId, msg7);

        List<Message> newsFeed = application.getNewsFeed(userId, 5);

        assertThat(newsFeed, is(sameBeanAs(Arrays.asList(msg3, msg4, msg5, msg6, msg7))));
    }

}