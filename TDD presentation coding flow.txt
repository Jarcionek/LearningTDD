Test code
---------

src/test/java/main/NewsFeedAcceptanceTest.java

package main;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class NewsFeedAcceptanceTest {

    private static final int TIME_1 = 15;
    private static final int TIME_2 = 30;

    private final UserId userId = new UserId(12345);

    private final Application application = Config.getApplication();

    @Test
    public void returnsAllPostedMessages() {
        application.post(userId, new Message(TIME_1, "msg1"));
        application.post(userId, new Message(TIME_2, "msg2"));

        List<Message> newsFeed = application.getNewsFeed(userId);

        assertThat(newsFeed, is(sameBeanAs(Arrays.asList(
                new Message(TIME_1, "msg1"),
                new Message(TIME_2, "msg2")))));
    }

}


Implementation code
--------------------


Application.java

package main;

import java.util.Arrays;
import java.util.List;

public class Application {

    public void post(UserId userId, Message msg) {

    }

    public List<Message> getNewsFeed(UserId userId) {
        return Arrays.asList(new Message(15, "msg1"), new Message(30, "msg2"));
    }
}

Config.java

package main;

public class Config {

    public static Application getApplication() {
        return new Application();
    }
}


Message.Java

package main;

public class Message {

    private final long time;
    private final String msg;

    public Message(long time, String msg) {

        this.time = time;
        this.msg = msg;
    }
}


package main;

public class UserId {

    public UserId(int id) {

    }
}


---------x------------

Test code
---------

ApplicationTest.java

package main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    private static final int ANY_TIME = 10;
    private static final UserId ANY_USER_ID = new UserId(5);

    @Mock private NewsFeedReader newsFeedReader;

    private Application application;

    @Before
    public void setup() {
        application = new Application(newsFeedReader);
    }

    @Test
    public void delegatesQueryToNewsFeedReader() {
        List<Message> expectedNewsFeed = Arrays.asList(new Message(ANY_TIME, "any message"));

        when(newsFeedReader.getNewsFeed(ANY_USER_ID)).thenReturn(expectedNewsFeed);

        List<Message> actualNewsFeed = application.getNewsFeed(ANY_USER_ID);

        assertThat(actualNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

}


Implementation code
-------------------

NewsFeedReader.java

package main;

import java.util.Arrays;
import java.util.List;

public class NewsFeedReader {

    public List<Message> getNewsFeed(UserId userId) {
        return Arrays.asList(new Message(15, "msg1"), new Message(30, "msg2"));
    }

}


Application.java

package main;

import java.util.List;

public class Application {

    private NewsFeedReader newsFeedReader;

    public Application(NewsFeedReader newsFeedReader) {
        this.newsFeedReader = newsFeedReader;
    }

    public void post(UserId userId, Message msg) {

    }

    public List<Message> getNewsFeed(UserId userId) {
        return newsFeedReader.getNewsFeed(userId);
    }
}


Config.java

package main;

public class Config {

    public static Application getApplication() {
        return new Application(new NewsFeedReader());
    }
}

----------x---------

InMemoryDbReader.java

public class InMemoryDbReader implements DbReader {
.
.
.
    @Override
    public List<String[]> get(String key) {
        List<String[]> result = new ArrayList<String[]>();
        if (DATA.containsKey(key)) {
            for (String[] data : DATA.get(key)) {
                insertAtRandomPosition(result, copyOf(data));
            }
        }
        return result;
    }
.
.
.
}

----------x---------

Test code
---------

NewsFeedReaderIntegrationTest.java

package main;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class NewsFeedReaderIntegrationTest {

    private UserId userId1 = new UserId(1001);
    private UserId userId2 = new UserId(1002);

    private NewsFeedReader newsFeedReader = new NewsFeedReader();

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


ApplicationTest.java
.
.
.
    private static final UserId USER_ID = new UserId(5);
    private static final Message MESSAGE = new Message(10, "any message");
.
.
.
    @Test
    public void delegatesGetQueryToNewsFeedReader() {
        List<Message> expectedNewsFeed = Arrays.asList(MESSAGE);

        when(newsFeedReader.getNewsFeed(USER_ID)).thenReturn(expectedNewsFeed);

        List<Message> actualNewsFeed = application.getNewsFeed(USER_ID);

        assertThat(actualNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }
.
.
.
}


Implementation code
-------------------

Message.java

public class Message {

.
.
.
    public long getTime() {
        return time;
    }

    public String getMsg() {
        return msg;
    }

}


Application.java
public class Application {
.
.
.
    private final NewsFeedReader newsFeedReader;
.
.
.
    public void post(UserId userId, Message msg) {
        newsFeedReader.post(userId, msg);
    }
.
.
.
}


NewsfeedReader.java

.
.
.
public class NewsFeedReader {

    private final DbReader dbReader = new InMemoryDbReader();

    public List<Message> getNewsFeed(UserId userId) {
        List<String[]> entriesList = dbReader.get("" + userId.getUserId());

        List<Message> result = new ArrayList<Message>(entriesList.size());

        for (String[] entry : entriesList) {
            result.add(new Message(Long.parseLong(entry[0]), entry[1]));
        }

        return result;
    }

    public void post(UserId userId, Message msg) {
        dbReader.put("" + userId.getUserId(), "" + msg.getTime(), msg.getMsg());
    }
}


UserId.java

package main;

public class UserId {

    private final int id;

    public UserId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return id;
    }

}

--------x---------

Test code
---------

NewsFeedReaderIntegrationTest.java

package main;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class NewsFeedReaderIntegrationTest {

    private UserId userId1 = new UserId(1001);
    private UserId userId2 = new UserId(1002);

    private NewsFeedReader newsFeedReader = new NewsFeedReader();

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


ApplicationTest.java
.
.
.
public class ApplicationTest {

    private static final UserId USER_ID = new UserId(5);
    private static final Message MESSAGE = new Message(10, "any message");
.
.
.
    @Test
    public void delegatesGetQueryToNewsFeedReader() {
        List<Message> expectedNewsFeed = Arrays.asList(MESSAGE);

        when(newsFeedReader.getNewsFeed(USER_ID)).thenReturn(expectedNewsFeed);

        List<Message> actualNewsFeed = application.getNewsFeed(USER_ID);

        assertThat(actualNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

    @Test
    public void delegatesPostQueryToNewsFeedReader() {
        application.post(USER_ID, MESSAGE);

        verify(newsFeedReader, times(1)).post(USER_ID, MESSAGE);
    }

}

Implementation code
-------------------

public class Application {
    private final NewsFeedReader newsFeedReader;
.
.
.
    public void post(UserId userId, Message msg) {
        newsFeedReader.post(userId, msg);
    }
.
.
.
}


--------x---------

NewsFeedAcceptanceTest.java
.
.
.
    @Test
    public void returnsAllPostedMessages() {
        Message msg1 = new Message(15, "msg1");
        Message msg2 = new Message(30, "msg2");
        Message msg3 = new Message(45, "msg3");
        Message msg4 = new Message(60, "msg4");
        Message msg5 = new Message(75, "msg5");

        application.post(userId, msg1);
        application.post(userId, msg2);
        application.post(userId, msg3);
        application.post(userId, msg4);
        application.post(userId, msg5);

        List<Message> newsFeed = application.getNewsFeed(userId);

        assertThat(newsFeed, is(sameBeanAs(Arrays.asList(msg1, msg2, msg3, msg4, msg5))));
    }
.
.
.
}

--------x---------

Test code
---------
Rename NewsFeedReaderIntegrationTest to ThirdPartyNewsFeedReaderIntegrationTest


Implementation code
-------------------

ThirdPartyNewsFeedReader.java

package main;

import thirdparty.DbReader;
import thirdparty.InMemoryDbReader;

import java.util.ArrayList;
import java.util.List;

public class ThirdPartyNewsFeedReader implements NewsFeedReader {

    private final DbReader dbReader = new InMemoryDbReader();

    @Override
    public List<Message> getNewsFeed(UserId userId) {
        List<String[]> entriesList = dbReader.get("" + userId.getUserId());

        List<Message> result = new ArrayList<Message>(entriesList.size());

        for (String[] entry : entriesList) {
            result.add(new Message(Long.parseLong(entry[0]), entry[1]));
        }

        return result;
    }

    @Override
    public void post(UserId userId, Message msg) {
        dbReader.put("" + userId.getUserId(), "" + msg.getTime(), msg.getMsg());
    }

}

Config.java

package main;

public class Config {

    public static Application getApplication() {
        return new Application(new ThirdPartyNewsFeedReader());
    }
}


NewsFeedReader (turn it into an interface)

package main;

import java.util.List;

public interface NewsFeedReader {

    List<Message> getNewsFeed(UserId userId);

    void post(UserId userId, Message msg);

}


--------x---------

Test code
---------

NewsFeedAcceptanceTest.java

.
.
.

@Test
    public void returnsAllPostedMessages() {
        Message msg1 = new Message(15, "msg1");
        Message msg2 = new Message(30, "msg2");
        Message msg3 = new Message(45, "msg3");
        Message msg4 = new Message(60, "msg4");
        Message msg5 = new Message(75, "msg5");

        application.post(userId, msg1);
        application.post(userId, msg2);
        application.post(userId, msg3);
        application.post(userId, msg4);
        application.post(userId, msg5);

        List<Message> newsFeed = application.getNewsFeed(userId);

        assertThat(newsFeed, is(sameBeanAs(Arrays.asList(msg1, msg2, msg3, msg4, msg5))));
    }
.
.
.
}

--------x---------

Test code
---------

SortingNewsFeedReaderTest.java

package main;

import com.shazam.shazamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SortingNewsFeedReaderTest {

    private static final UserId USER_ID = new UserId(672);

    @Mock private NewsFeedReader delegateNewsFeedReader;

    private NewsFeedReader sortingNewsFeedReader;

    @Before
    public void setup() {
        sortingNewsFeedReader = new SortingNewsFeedReader(delegateNewsFeedReader);
    }

    @Test
    public void returnsSortedMessagesReturnedByDelegate() {
        when(delegateNewsFeedReader.getNewsFeed(USER_ID)).thenReturn(
                Arrays.asList(msg(5), msg(4), msg(6), msg(3), msg(7), msg(2), msg(8))
        );

        List<Message> newsFeed = sortingNewsFeedReader.getNewsFeed(USER_ID);

        assertThat(newsFeed, is(sameBeanAs(
                Arrays.asList(msg(2), msg(3), msg(4), msg(5), msg(6), msg(7), msg(8))
        )));
    }

    @Test
    public void delegatesPost() {
        Message message = new Message(12345, "3");

        sortingNewsFeedReader.post(USER_ID, message);

        verify(delegateNewsFeedReader, times(1)).post(USER_ID, message);
    }

    private static Message msg(int time) {
        return new Message(time, "" + time);
    }

}


Implementation code
-------------------

SortingNewsFeedReader.java

package main;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortingNewsFeedReader implements NewsFeedReader {

    private final NewsFeedReader delegateNewsFeedReader;

    public SortingNewsFeedReader(NewsFeedReader delegateNewsFeedReader) {
        this.delegateNewsFeedReader = delegateNewsFeedReader;
    }

    @Override
    public List<Message> getNewsFeed(UserId userId) {
        List<Message> newsFeed = delegateNewsFeedReader.getNewsFeed(userId);
        Collections.sort(newsFeed, new Comparator<Message>() {
            @Override
            public int compare(Message msg1, Message msg2) {
                return msg1.getTime() > msg2.getTime() ? 1 : msg1.getTime() < msg2.getTime() ? -1 : 0;
            }
        });
        return newsFeed;
    }

    @Override
    public void post(UserId userId, Message msg) {
        delegateNewsFeedReader.post(userId, msg);
    }

}


Config.java

package main;

public class Config {

    public static Application getApplication() {
        return new Application(new SortingNewsFeedReader(new ThirdPartyNewsFeedReader()));
    }
}

----------x----------

Move NewsFeedTest.java to test-acceptance/java/main/....

Move ThirdPartyNewsFeedReaderTest.java to test-integration/java/main/....

----------x----------


Test code
---------

NewsFeedTest.java

    @Test
    public void returnsLastFivePostedMessages() {
        Message msg1 = new Message(10, "msg1");
        Message msg2 = new Message(20, "msg2");
        Message msg3 = new Message(30, "msg3");
        Message msg4 = new Message(40, "msg4");
        Message msg5 = new Message(50, "msg5");
        Message msg6 = new Message(60, "msg6");
        Message msg7 = new Message(70, "msg7");

        application.post(userId, msg1);
        application.post(userId, msg2);
        application.post(userId, msg3);
        application.post(userId, msg4);
        application.post(userId, msg5);
        application.post(userId, msg6);
        application.post(userId, msg7);

        List<Message> newsFeed = application.getNewsFeed(userId, 5);

        assertThat(newsFeed, is(sameBeanAs(Arrays.asList(msg3, msg4, msg5, msg6, msg7))));
    }


Implementation code
-------------------

Application.java

.
.
.
public class Application {
.
.
.
    public List<Message> getNewsFeed(UserId userId, int size) {
        Message msg3 = new Message(30, "msg3");
        Message msg4 = new Message(40, "msg4");
        Message msg5 = new Message(50, "msg5");
        Message msg6 = new Message(60, "msg6");
        Message msg7 = new Message(70, "msg7");

        return Arrays.asList(msg3, msg4, msg5, msg6, msg7);
    }
}

----------x----------

Test code
---------
ApplicationTest.java

public class ApplicationTest {
.
.
.
    @Before
    public void setup() {
        application = new Application(newsFeedReader, newsFeedReducer);
    }
.
.
.
   @Test
    public void delegatesSizedGetQuery() {
        List<Message> expectedNewsFeed = Arrays.asList(new Message(2, "2"));
        List<Message> entireNewFeed = Arrays.asList(new Message(1, "1"), new Message(2, "2"));

        when(newsFeedReader.getNewsFeed(USER_ID)).thenReturn(entireNewFeed);
        when(newsFeedReducer.reduce(entireNewFeed, 1)).thenReturn(expectedNewsFeed);

        List<Message> actualNewsFeed = application.getNewsFeed(USER_ID, 1);

        assertThat(actualNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

}


Implementation code
-------------------

Application.java

public class Application {
.
.
.
    private final NewsFeedReducer newsFeedReducer;

    public Application(NewsFeedReader newsFeedReader, NewsFeedReducer newsFeedReducer) {
        this.newsFeedReader = newsFeedReader;
        this.newsFeedReducer = newsFeedReducer;
    }
.
.
.
    @SuppressWarnings("UnnecessaryLocalVariable")
    public List<Message> getNewsFeed(UserId userId, int size) {
        List<Message> entireNewsFeed = newsFeedReader.getNewsFeed(userId);

        List<Message> reducedNewsFeed = newsFeedReducer.reduce(entireNewsFeed, size);

        return reducedNewsFeed;
    }
}


Config.java

package main;

public class Config {

    public static Application getApplication() {
        return new Application(new SortingNewsFeedReader(new ThirdPartyNewsFeedReader()), new NewsFeedReducer());
    }
}


NewsFeedReducer.java

package main;

import java.util.Arrays;
import java.util.List;

public class NewsFeedReducer {

    public List<Message> reduce(List<Message> newsFeed, int size) {
        Message msg3 = new Message(30, "msg3");
        Message msg4 = new Message(40, "msg4");
        Message msg5 = new Message(50, "msg5");
        Message msg6 = new Message(60, "msg6");
        Message msg7 = new Message(70, "msg7");

        return Arrays.asList(msg3, msg4, msg5, msg6, msg7);
    }

}


--------- x -----------

Test code
---------
NewsFeedReducerTest.java

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


NewsFeedTest.java
.
.
.
public class NewsFeedTest {

    @Test
    public void returnsAllPostedMessages() {
        final UserId userId = new UserId(5001);
.
.
.
}

Implementation code
-------------------

package main;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedReducer {

    public List<Message> reduce(List<Message> newsFeed, int size) {
        List<Message> reducedNewsFeed = new ArrayList<Message>(size);

        for (int i = newsFeed.size() - size; i < newsFeed.size(); i++) {
            reducedNewsFeed.add(newsFeed.get(i));
        }

        return reducedNewsFeed;
    }

}


----------x----------

Test code
---------

ApplicationTest.java

public class ApplicationTest {
.
.
.
    @Test
    public void delegatesSizedGetQuery() {
        List<Message> expectedNewsFeed = Arrays.asList(msg(2));
        List<Message> entireNewFeed = Arrays.asList(msg(1), msg(2));
.
.
.
    }


    private static Message msg(int time) {
        return new Message(time, "" + time);
    }
}

----------x----------

Test code
---------

ApplicationTest.java

public class ApplicationTest {
.
.
.
    @Mock private NewsFeedPaginator newsFeedPaginator;

    private Application application;

    @Before
    public void setup() {
        application = new Application(newsFeedReader, newsFeedReducer, newsFeedPaginator);
    }
.
.
.
    @Test
    public void delegatesSizedGetQuery() {
        List<Message> expectedNewsFeed = Arrays.asList(msg(2));
        List<Message> entireNewsFeed = Arrays.asList(msg(1), msg(2));

        when(newsFeedReader.getNewsFeed(USER_ID)).thenReturn(entireNewsFeed);
        when(newsFeedReducer.reduce(entireNewsFeed, 1)).thenReturn(expectedNewsFeed);

        List<Message> actualNewsFeed = application.getNewsFeed(USER_ID, 1);

        assertThat(actualNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }

    @Test
    public void delegatesPaginatedSizedGetQuery() {
        List<Message> expectedNewsFeed = Arrays.asList(msg(2), msg(3));
        List<Message> entireNewsFeed = Arrays.asList(msg(1), msg(2), msg(3), msg(4), msg(5));

        when(newsFeedReader.getNewsFeed(USER_ID)).thenReturn(entireNewsFeed);
        when(newsFeedPaginator.paginate(entireNewsFeed, 1, 2)).thenReturn(expectedNewsFeed);

        List<Message> actualNewsFeed = application.getNewsFeed(USER_ID, 1, 2);

        assertThat(actualNewsFeed, is(sameBeanAs(expectedNewsFeed)));
    }
.
.
.
}

Implementation code
-------------------

NewsFeedPaginator.java

package main;

import java.util.Arrays;
import java.util.List;

public class NewsFeedPaginator {

    public List<Message> paginate(List<Message> newsFeed, int pageNumber, int pageSize) {
        return Arrays.asList(new Message(4, "msg+4"));
    }

}


Config.java
package main;

public class Config {

    public static Application getApplication() {
        return new Application(new SortingNewsFeedReader(new ThirdPartyNewsFeedReader()), new NewsFeedReducer(), new NewsFeedPaginator());
    }
}


Application.java

public class Application {
.
.
.
    private final NewsFeedPaginator newsFeedPaginator;

    public Application(NewsFeedReader newsFeedReader, NewsFeedReducer newsFeedReducer, NewsFeedPaginator newsFeedPaginator) {
        this.newsFeedReader = newsFeedReader;
        this.newsFeedReducer = newsFeedReducer;
        this.newsFeedPaginator = newsFeedPaginator;
    }

.
.
.
    @SuppressWarnings("UnnecessaryLocalVariable")
    public List<Message> getNewsFeed(UserId userId, int pageNumber, int pageSize) {
        List<Message> entireNewsFeed = newsFeedReader.getNewsFeed(userId);

        List<Message> paginatedNewsFeed = newsFeedPaginator.paginate(entireNewsFeed, pageNumber, pageSize);

        return paginatedNewsFeed;
    }
}


----------x----------

Test code
---------

NewsFeedPaginatorTest.java

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


Implementation code
-------------------

NewsFeedPaginator.java

package main;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedPaginator {

    public List<Message> paginate(List<Message> newsFeed, int pageNumber, int pageSize) {
        List<Message> paginatedNewsFeed = new ArrayList<Message>(pageSize);

        for (int i = newsFeed.size() - 1 - pageNumber * pageSize; i >= newsFeed.size() - (pageNumber + 1) * pageSize && i >= 0; i--) {
            paginatedNewsFeed.add(0, newsFeed.get(i));
        }

        return paginatedNewsFeed;
    }
}


----------x----------

Test code
---------
public class NewsFeedTest {

    private final NewsFeedEndpoints endpoint = Config.getNewsFeedEndpoints();

    @Test
    public void returnsAllPostedMessages() {
.
.
.
        endpoint.post(userId, msg1);
        endpoint.post(userId, msg2);
        endpoint.post(userId, msg3);
        endpoint.post(userId, msg4);
        endpoint.post(userId, msg5);

        List<Message> newsFeed = endpoint.getNewsFeed(userId);

        assertThat(newsFeed, is(sameBeanAs(Arrays.asList(msg1, msg2, msg3, msg4, msg5))));
    }

    @Test
    public void returnsLastFivePostedMessages() {
.
.
.
        endpoint.post(userId, msg1);
        endpoint.post(userId, msg2);
        endpoint.post(userId, msg3);
        endpoint.post(userId, msg4);
        endpoint.post(userId, msg5);
        endpoint.post(userId, msg6);
        endpoint.post(userId, msg7);

        List<Message> newsFeed = endpoint.getNewsFeed(userId, 5);

        assertThat(newsFeed, is(sameBeanAs(Arrays.asList(msg3, msg4, msg5, msg6, msg7))));
    }

    @Test
    public void returnsSecondPageOfPaginatedNewsFeed() {
.
.
.
        endpoint.post(userId, msg1);
        endpoint.post(userId, msg2);
        endpoint.post(userId, msg3);
        endpoint.post(userId, msg4);
        endpoint.post(userId, msg5);
        endpoint.post(userId, msg6);
        endpoint.post(userId, msg7);

        List<Message> newsFeed = endpoint.getNewsFeed(userId, 5);

        assertThat(newsFeed, is(sameBeanAs(Arrays.asList(msg3, msg4, msg5, msg6, msg7))));
    }


Implementation code
-------------------

NewsFeedEndpoints.java

package main;

import java.util.List;

public interface NewsFeedEndpoints {

    void post(UserId userId, Message msg);

    List<Message> getNewsFeed(UserId userId);

    List<Message> getNewsFeed(UserId userId, int size);

    List<Message> getNewsFeed(UserId userId, int pageNumber, int pageSize);

}


Config.java

package main;

public class Config {

    public static NewsFeedEndpoints getNewsFeedEndpoints() {
        return new Application(new SortingNewsFeedReader(new ThirdPartyNewsFeedReader()), new NewsFeedReducer(), new NewsFeedPaginator());
    }

}

Application.java

public class Application implements NewsFeedEndpoints {

@Override - on a number of implementation methods


---------x----------

Test code
---------

PersistentDbReaderTest.java 

package thirdparty;

import org.junit.Test;

public class PersistentDbReaderTest {

    private PersistentDbReader persistentDbReader = new PersistentDbReader();

    @Test
    public void test() {
        System.out.println(persistentDbReader.get("x"));

        System.out.println("putting x->abc");
        persistentDbReader.put("x", "abc");

        System.out.println(persistentDbReader.get("x"));
    }

}


Implementation code
-------------------

PersistentDbReader.java

package thirdparty;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
public class PersistentDbReader implements DbReader {

    private static final Logger LOG = Logger.getAnonymousLogger();
    private static final File FILE = new File(System.getProperty("user.dir"), "news-feed-db.ser");
    private static final File README_FILE = new File(System.getProperty("user.dir"), "news-feed-db-readme.txt");

    private static final InMemoryDbReader inMemoryDbReader = new InMemoryDbReader();

    static {
        if (!FILE.exists()) {
            try {
                LOG.info("Creating db file: " + FILE);
                FILE.createNewFile();
                LOG.info("Creating readme file: " + README_FILE);
                README_FILE.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(README_FILE));
                bw.write("Users' news feed created by LearningTDD exercise. Safe to delete.");
                bw.close();
            } catch (IOException e) {
                LOG.severe("Could not create db file");
                throw new RuntimeException(e);
            }
        } else {
            LOG.info("loading from existing db file: " + FILE);
            try {
                FileInputStream fis = new FileInputStream(FILE);
                ObjectInputStream ois = new ObjectInputStream(fis);
                inMemoryDbReader.setData((Map<String, List<String[]>>) ois.readObject());
                ois.close();
                fis.close();
            } catch(Exception exception) {
                LOG.severe("Could not load db file");
                throw new RuntimeException(exception);
            }
        }
    }


    @Override
    public void put(String key, String... data) {
        inMemoryDbReader.put(key, data);
        save();
    }

    @Override
    public void delete(String key) {
        inMemoryDbReader.delete(key);
        save();
    }

    @Override
    public List<String[]> get(String key) {
        return inMemoryDbReader.get(key);
    }

    private void save() {
        try {
            FileOutputStream fos = new FileOutputStream(FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(inMemoryDbReader.getData());
            oos.close();
            fos.close();
        } catch(IOException exception) {
            LOG.severe("Could not save to db file");
            throw new RuntimeException(exception);
        }
    }

}


InMemoryDbReader.java

public class InMemoryDbReader implements DbReader {

    private static Map<String, List<String[]>> data = new HashMap<String, List<String[]>>();
.
.
.

    void setData(Map<String, List<String[]>> data) {
        InMemoryDbReader.data = data;
    }

    Map<String, List<String[]>> getData() {
        return data;
    }
.
.
.
}

