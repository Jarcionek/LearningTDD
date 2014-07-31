package system;

import thirdparty.DbReader;
import thirdparty.PersistentDbReader;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;

public class ThirdPartyNewsFeedDbAdapter implements NewsFeedDbAdapter {

    private final DbReader dbReader = new PersistentDbReader();

    @Override
    public NewsFeed getNewsFeed(UserId userId) {
        List<String[]> entireNewsFeed = dbReader.get("" + userId.getId());

        List<Message> messages = new ArrayList<Message>();

        for (String[] messageAsString : entireNewsFeed) {
            messages.add(new Message(parseLong(messageAsString[0]), messageAsString[1]));
        }

        return new NewsFeed(messages);
    }

    @Override
    public void post(UserId userId, Message message) {
        dbReader.put("" + userId.getId(), "" + message.getTimestamp(), message.getMessage());
    }

    @Override
    public void removeAll(UserId userId) {
        dbReader.delete("" + userId.getId());
    }

}
