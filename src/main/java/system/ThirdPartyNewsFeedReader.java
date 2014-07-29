package system;

import thirdparty.DbReader;
import thirdparty.PersistentDbReader;

import java.util.ArrayList;
import java.util.List;

public class ThirdPartyNewsFeedReader implements NewsFeedReader {

    private final DbReader dbReader = new PersistentDbReader();

    @Override
    public NewsFeed getNewsFeed(UserId userId) {
        List<String[]> entireNewsFeed = dbReader.get("" + userId.getId());

        List<Message> messages = new ArrayList<Message>();

        for (String[] stringMessage : entireNewsFeed) {
            messages.add(new Message(Long.valueOf(stringMessage[0]), stringMessage[1]));
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
