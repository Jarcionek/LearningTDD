package system;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class NewsFeed {

    private final List<Message> messages;

    public NewsFeed(Message... messages) {
        this.messages = ImmutableList.of(messages);
    }

}
