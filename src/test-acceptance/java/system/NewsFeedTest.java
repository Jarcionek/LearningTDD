package system;

import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class NewsFeedTest {

    private final Application application = Config.application();

    private final UserId userId = new UserId(123);

    @Test
    public void retrievesEntireNewsFeed() {
        Message msg1 = new Message(15, "msg1");
        Message msg2 = new Message(25, "msg2");

        application.post(userId, msg1);
        application.post(userId, msg2);

        NewsFeed newsFeed = application.getNewsFeed(userId);

        assertThat(newsFeed, is(sameBeanAs(new NewsFeed(msg1, msg2))));
    }

}