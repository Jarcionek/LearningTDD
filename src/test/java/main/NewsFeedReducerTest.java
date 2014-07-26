package main;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class NewsFeedReducerTest {

    private final NewsFeedReducer newsFeedReducer = new NewsFeedReducer();

    @Test
    public void reducesMessagesInNewsFeed() {
        List<Message> entireNewsFeed = Arrays.asList(msg(1), msg(2), msg(4), msg(3));
        List<Message> expectedNewsFeed= Arrays.asList(msg(2), msg(4), msg(3));

        List<Message> reducedNewsFeed = newsFeedReducer.reduce(entireNewsFeed, 3);

        assertThat(reducedNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }


    private static Message msg(int time) {
        return new Message(time, "" + time);
    }

}
