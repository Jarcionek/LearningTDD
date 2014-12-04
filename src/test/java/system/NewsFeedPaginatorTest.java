package system;

import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewsFeedPaginatorTest {

    private UserId userId = new UserId(5);

    private NewsFeedDbAdapter newsFeedDbAdapter = mock(NewsFeedDbAdapter.class);

    private NewsFeedPaginator newsFeedPaginator = new NewsFeedPaginator(newsFeedDbAdapter);

    @Test
    public void retrievesEntireNewsFeedWhenPageNumberIsZeroAndPageSizeIsEqualToNumberOfMessages() {
        NewsFeed expectedNewsFeed = new NewsFeed(msg(0), msg(1), msg(2));
        when(newsFeedDbAdapter.getNewsFeed(userId)).thenReturn(expectedNewsFeed);

        NewsFeed actualNewsFeed = newsFeedPaginator.fetch(userId, new PageSize(3), new PageNumber(0));

        assertThat(actualNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

    @Test
    public void retrievesFirstPageOfFullSizeWhenThereAreMoreMessagesAvailable() {
        when(newsFeedDbAdapter.getNewsFeed(userId)).thenReturn(new NewsFeed(msg(0), msg(1), msg(2), msg(3)));

        NewsFeed actualNewsFeed = newsFeedPaginator.fetch(userId, new PageSize(2), new PageNumber(0));

        assertThat(actualNewsFeed, is(sameBeanAs(new NewsFeed(msg(2), msg(3)))));
    }

    @Test
    public void retrievesSecondPageOfFullSizeWhenSecondPageIsRequestedAndThereAreMoreMessagesAvailable() {
        when(newsFeedDbAdapter.getNewsFeed(userId))
                .thenReturn(new NewsFeed(msg(0), msg(1), msg(2), msg(3), msg(4), msg(5), msg(7)));

        NewsFeed actualNewsFeed = newsFeedPaginator.fetch(userId, new PageSize(3), new PageNumber(1));

        assertThat(actualNewsFeed, is(sameBeanAs(new NewsFeed(msg(1), msg(2), msg(3)))));
    }

    @Test
    public void retrievesLastPageOfSmallerSizeThanRequestedWhenThereAreNotEnoughMessages() {
        when(newsFeedDbAdapter.getNewsFeed(userId))
                .thenReturn(new NewsFeed(msg(0), msg(1), msg(2), msg(3), msg(4), msg(5)));

        NewsFeed actualNewsFeed = newsFeedPaginator.fetch(userId, new PageSize(4), new PageNumber(1));

        assertThat(actualNewsFeed, is(sameBeanAs(new NewsFeed(msg(0), msg(1)))));

    }

    @Test
    public void returnsEmptyNewsFeedWhenThereAreNoMessagesAvailableAtRequestedPageNumber() {
        when(newsFeedDbAdapter.getNewsFeed(userId)).thenReturn(new NewsFeed(msg(0), msg(1), msg(2)));

        NewsFeed actualNewsFeed = newsFeedPaginator.fetch(userId, new PageSize(4), new PageNumber(20));

        assertThat(actualNewsFeed, is(sameBeanAs(new NewsFeed())));
    }
    

    private static Message msg(int timestamp) {
        return new Message(timestamp, "message - " + timestamp);
    }

}