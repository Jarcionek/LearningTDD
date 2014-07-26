package main;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class NewsFeedPaginatorTest {

    private final NewsFeedPaginator newsFeedPaginator = new NewsFeedPaginator();

    @Test
    public void returnsEntireNewsFeedWhenPageSizeGreaterThanNewsFeedSize() {
        List<Message> entireNewsFeed = Arrays.asList(msg(1), msg(2), msg(3));
        int pageNumber = 0;
        int pageSize = 4;
        List<Message> expectedNewsFeed = Arrays.asList(msg(1), msg(2), msg(3));

        List<Message> paginatedNewsFeed = newsFeedPaginator.paginate(entireNewsFeed, pageNumber, pageSize);

        assertThat(paginatedNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

    @Test
    public void returnsMostRecentMessagesForPageZeroWhenNewsFeedGreaterThanPageSize() {
        List<Message> entireNewsFeed = Arrays.asList(msg(1), msg(2), msg(3), msg(4), msg(5));
        int pageNumber = 0;
        int pageSize = 2;
        List<Message> expectedNewsFeed = Arrays.asList(msg(4), msg(5));

        List<Message> paginatedNewsFeed = newsFeedPaginator.paginate(entireNewsFeed, pageNumber, pageSize);

        assertThat(paginatedNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

    @Test
    public void returnsFullPageTwoWhenThereIsEnoughMessages() {
        List<Message> entireNewsFeed = Arrays.asList(msg(1), msg(2), msg(3), msg(4), msg(5), msg(6), msg(7));
        int pageNumber = 2;
        int pageSize = 2;
        List<Message> expectedNewsFeed = Arrays.asList(msg(2), msg(3));

        List<Message> paginatedNewsFeed = newsFeedPaginator.paginate(entireNewsFeed, pageNumber, pageSize);

        assertThat(paginatedNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

    @Test
    public void returnsIncompletePageOneWhenThereIsNotEnoughMessages() {
        List<Message> entireNewsFeed = Arrays.asList(msg(1), msg(2), msg(3), msg(4), msg(5));
        int pageNumber = 1;
        int pageSize = 3;
        List<Message> expectedNewsFeed = Arrays.asList(msg(1), msg(2));

        List<Message> paginatedNewsFeed = newsFeedPaginator.paginate(entireNewsFeed, pageNumber, pageSize);

        assertThat(paginatedNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

    @Test
    public void returnsEmptyNewsFeedWhenNotEnoughMessagesForRequestedPage() {
        List<Message> entireNewsFeed = Arrays.asList(msg(10));
        int pageNumber = 20;
        int pageSize = 3;
        List<Message> expectedNewsFeed = Arrays.asList();

        List<Message> paginatedNewsFeed = newsFeedPaginator.paginate(entireNewsFeed, pageNumber, pageSize);

        assertThat(paginatedNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }


    private static Message msg(int time) {
        return new Message(time, "" + time);
    }

}
