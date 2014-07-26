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
