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
