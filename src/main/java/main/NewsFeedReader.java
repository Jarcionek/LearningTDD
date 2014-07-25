package main;

import thirdparty.DbReader;
import thirdparty.InMemoryDbReader;

import java.util.ArrayList;
import java.util.List;

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
