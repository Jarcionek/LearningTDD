package system;

import org.junit.After;
import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;

public class ThirdPartyNewsFeedDbAdapterTest {

    private final UserId userId1 = new UserId(123);
    private final UserId userId2 = new UserId(567);

    private ThirdPartyNewsFeedDbAdapter thirdPartyNewsFeedDbAdapter = new ThirdPartyNewsFeedDbAdapter();

    @After
    public void removeMessages() {
        thirdPartyNewsFeedDbAdapter.removeAll(userId1);
        thirdPartyNewsFeedDbAdapter.removeAll(userId2);
    }

    @Test
    public void retrievesPostedMessages() {
        Message msg1 = new Message(5, "msg1");
        Message msg2 = new Message(7, "msg2");
        Message msg3 = new Message(8, "msg3");

        thirdPartyNewsFeedDbAdapter.post(userId1, msg1);
        thirdPartyNewsFeedDbAdapter.post(userId2, msg2);
        thirdPartyNewsFeedDbAdapter.post(userId2, msg3);

        NewsFeed actualNewsFeed1 = thirdPartyNewsFeedDbAdapter.getNewsFeed(userId1);
        NewsFeed actualNewsFeed2 = thirdPartyNewsFeedDbAdapter.getNewsFeed(userId2);

        assertThat(actualNewsFeed1, is(sameBeanAs(new NewsFeed(msg1))));
        assertThat(actualNewsFeed2, is(anyOf(sameBeanAs(new NewsFeed(msg2, msg3)), sameBeanAs(new NewsFeed(msg3, msg2)))));
    }

}