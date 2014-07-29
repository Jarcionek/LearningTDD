package system;

import org.junit.After;
import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class NewsFeedReaderTest {

    private final UserId userId1 = new UserId(56);
    private final UserId userId2 = new UserId(97);

    private final NewsFeedReader newsFeedReader = new NewsFeedReader();

    @After
    public void removePostedMessages() {
        newsFeedReader.removeAll(userId1);
        newsFeedReader.removeAll(userId2);
    }

    @Test
    public void persistsNewsFeedIntoDb() {
        Message msg1 = new Message(7, "user 1, message 2");
        Message msg2 = new Message(9, "user 2, message 1");
        Message msg3 = new Message(9, "user 2, message 2");

        newsFeedReader.post(userId1, msg1);
        newsFeedReader.post(userId2, msg2);
        newsFeedReader.post(userId2, msg3);

        assertThat(newsFeedReader.getNewsFeed(userId1), is(sameBeanAs(new NewsFeed(msg1))));
        assertThat(newsFeedReader.getNewsFeed(userId2), is(sameBeanAs(new NewsFeed(msg2, msg3))));
    }

}