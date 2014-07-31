package system;

import org.junit.After;
import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class NewsFeedTest {

    private final Application application = Config.application();
    private final UserId userId = new UserId(1234);

    @After
    public void cleanup() {
        Config.newsFeedDbAdapter().removeAll(userId);
    }

    @Test
    public void retrievesAllPostedMessages() {
        Message msg1 = new Message(20, "msg1");
        Message msg2 = new Message(30, "msg2");
        Message msg3 = new Message(40, "msg3");
        Message msg4 = new Message(50, "msg4");
        Message msg5 = new Message(60, "msg5");

        application.post(userId, msg1);
        application.post(userId, msg2);
        application.post(userId, msg3);
        application.post(userId, msg4);
        application.post(userId, msg5);

        NewsFeed actualNewsFeed = application.getNewsFeed(userId);

        assertThat(actualNewsFeed, is(sameBeanAs(new NewsFeed(msg1, msg2, msg3, msg4, msg5))));
    }

}
