package system;

import org.junit.After;
import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class NewsFeedTest {

    private final Application application = Config.application();

    private final UserId userId = new UserId(123);

    @After
    public void removePostedMessagesFromDb() {
        Config.newsFeedReader().removeAll(userId);
    }

    @Test
    public void retrievesEntireNewsFeed() {
        Message msg1 = new Message(15, "msg1");
        Message msg2 = new Message(25, "msg2");
        Message msg3 = new Message(40, "msg3");
        Message msg4 = new Message(50, "msg4");
        Message msg5 = new Message(90, "msg5");

        application.post(userId, msg1);
        application.post(userId, msg2);
        application.post(userId, msg3);
        application.post(userId, msg4);
        application.post(userId, msg5);

        NewsFeed newsFeed = application.getNewsFeed(userId);

        assertThat(newsFeed, is(sameBeanAs(new NewsFeed(msg1, msg2, msg3, msg4, msg5))));
    }

}