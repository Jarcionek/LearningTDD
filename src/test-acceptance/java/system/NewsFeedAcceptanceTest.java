package system;

import org.junit.AfterClass;
import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class NewsFeedAcceptanceTest {

    private static final Application APPLICATION = Config.application();
    private static final UserId USER_ID_ONE = new UserId(1234);
    private static final UserId USER_ID_TWO = new UserId(5678);

    @AfterClass
    public static void cleanup() {
        Config.newsFeedDbAdapter().removeAll(USER_ID_ONE);
        Config.newsFeedDbAdapter().removeAll(USER_ID_TWO);
    }

    @Test
    public void retrievesAllPostedMessages() {
        Message msg1 = new Message(20, "msg1");
        Message msg2 = new Message(30, "msg2");
        Message msg3 = new Message(40, "msg3");
        Message msg4 = new Message(50, "msg4");
        Message msg5 = new Message(60, "msg5");

        APPLICATION.post(USER_ID_ONE, msg1);
        APPLICATION.post(USER_ID_ONE, msg2);
        APPLICATION.post(USER_ID_ONE, msg3);
        APPLICATION.post(USER_ID_ONE, msg4);
        APPLICATION.post(USER_ID_ONE, msg5);

        NewsFeed actualNewsFeed = APPLICATION.getNewsFeed(USER_ID_ONE);

        assertThat(actualNewsFeed, is(sameBeanAs(new NewsFeed(msg1, msg2, msg3, msg4, msg5))));
    }

    @Test
    public void retrievesPaginatedNewsFeed() {
        Message a = new Message(1, "a-1");
        Message b = new Message(2, "b-2");
        Message c = new Message(3, "c-3");
        Message d = new Message(4, "d-4");
        Message e = new Message(5, "e-5");
        Message f = new Message(6, "f-5");
        Message g = new Message(7, "g-5");

        APPLICATION.post(USER_ID_TWO, f);
        APPLICATION.post(USER_ID_TWO, g);
        APPLICATION.post(USER_ID_TWO, c);
        APPLICATION.post(USER_ID_TWO, a);
        APPLICATION.post(USER_ID_TWO, d);
        APPLICATION.post(USER_ID_TWO, b);
        APPLICATION.post(USER_ID_TWO, e);

        NewsFeed actualNewsFeed = APPLICATION.getNewsFeed(USER_ID_TWO, new PageSize(2), new PageNumber(1));

        assertThat(actualNewsFeed, is(sameBeanAs(new NewsFeed(d, e))));
    }

}
