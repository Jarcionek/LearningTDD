package system;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class NewsFeed {

    private final List<Message> messages;

    public NewsFeed(Message... messages) {
        this.messages = newArrayList(messages);
    }

    public NewsFeed(List<Message> messages) {
        this.messages = messages;
    }

}
