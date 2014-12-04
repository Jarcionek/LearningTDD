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
    private static final UserId USER_ID_THREE = new UserId(9999);
    private static final UserId USER_ID_FOUR = new UserId(10000000);

    @AfterClass
    public static void cleanup() {
        Config.newsFeedDbAdapter().removeAll(USER_ID_ONE);
        Config.newsFeedDbAdapter().removeAll(USER_ID_TWO);
        Config.newsFeedDbAdapter().removeAll(USER_ID_THREE);
        Config.newsFeedDbAdapter().removeAll(USER_ID_FOUR);
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
        Message f = new Message(6, "f-6");
        Message g = new Message(7, "g-7");

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

    @Test
    public void retrievesLastPageIfThereAreNotEnoughMessagesToFillThePage() {
        Message a = new Message(100, "a-100");
        Message b = new Message(200, "b-200");
        Message c = new Message(300, "c-300");
        Message d = new Message(400, "d-400");
        Message e = new Message(500, "e-500");
        Message f = new Message(600, "f-600");
        Message g = new Message(700, "g-700");
        Message h = new Message(800, "h-800");

        APPLICATION.post(USER_ID_THREE, c);
        APPLICATION.post(USER_ID_THREE, b);
        APPLICATION.post(USER_ID_THREE, h);
        APPLICATION.post(USER_ID_THREE, g);
        APPLICATION.post(USER_ID_THREE, d);
        APPLICATION.post(USER_ID_THREE, a);
        APPLICATION.post(USER_ID_THREE, f);
        APPLICATION.post(USER_ID_THREE, e);

        NewsFeed actualNewsFeed = APPLICATION.getNewsFeed(USER_ID_THREE, new PageSize(3), new PageNumber(2));

        assertThat(actualNewsFeed, is(sameBeanAs(new NewsFeed(a, b))));
    }

    @Test
    public void retrievesEmptyPageWhenThereAreNoMessages() {
        NewsFeed actualNewsFeed = APPLICATION.getNewsFeed(USER_ID_THREE, new PageSize(3), new PageNumber(200));

        assertThat(actualNewsFeed, is(sameBeanAs(new NewsFeed())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenPageSizeIsNotPositive() {
        APPLICATION.getNewsFeed(USER_ID_ONE, new PageSize(0), new PageNumber(7));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenPageNumberIsNegative() {
        APPLICATION.getNewsFeed(USER_ID_ONE, new PageSize(9), new PageNumber(-1));
    }

    @Test
    public void retrievesCappedNewsFeed() {
        Message msg1 = new Message(20, "msg+1");
        Message msg2 = new Message(30, "msg+2");
        Message msg3 = new Message(40, "msg+3");
        Message msg4 = new Message(50, "msg+4");
        Message msg5 = new Message(60, "msg+5");

        APPLICATION.post(USER_ID_FOUR, msg3);
        APPLICATION.post(USER_ID_FOUR, msg1);
        APPLICATION.post(USER_ID_FOUR, msg5);
        APPLICATION.post(USER_ID_FOUR, msg2);
        APPLICATION.post(USER_ID_FOUR, msg4);

        NewsFeed actualNewsFeed = APPLICATION.getNewsFeed(USER_ID_FOUR, new PageSize(3));

        assertThat(actualNewsFeed, is(sameBeanAs(new NewsFeed(msg3, msg4, msg5))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenNumberOfMessagesIsNotPositive() {
        APPLICATION.getNewsFeed(USER_ID_FOUR, new PageSize(0));
    }

}
