package main;

import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ThirdPartyNewsFeedReaderIntegrationTest {

    private UserId userId1 = new UserId(1001);
    private UserId userId2 = new UserId(1002);

    private ThirdPartyNewsFeedReader newsFeedReader = new ThirdPartyNewsFeedReader();

    @SuppressWarnings("unchecked")
    @Test
    public void persistsNewsFeedIntoDb() {
        Message msg1 = new Message(7, "user 1, message 1");
        Message msg2 = new Message(8, "user 2, message 1");
        Message msg3 = new Message(8, "user 2, message 2");

        newsFeedReader.post(userId1, msg1);
        newsFeedReader.post(userId2, msg2);
        newsFeedReader.post(userId2, msg3);

        assertThat(newsFeedReader.getNewsFeed(userId1), is(sameBeanAs(asList(msg1))));
        assertThat(newsFeedReader.getNewsFeed(userId2), containsInAnyOrder(sameBeanAs(msg2), sameBeanAs(msg3)));
    }

}