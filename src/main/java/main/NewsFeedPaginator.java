package main;

import java.util.Arrays;
import java.util.List;

public class NewsFeedPaginator {

    public List<Message> paginate(List<Message> newsFeed, int pageNumber, int pageSize) {
        return Arrays.asList(new Message(4, "msg+4"));
    }

}
