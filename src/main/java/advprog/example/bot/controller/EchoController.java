package advprog.example.bot.controller;

import advprog.example.bot.resources.UserLaughCounter;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@LineMessageHandler
public class EchoController {
    @Autowired
    private LineMessagingClient lineMessagingClient;
    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());
    private HashMap<String, ArrayList<UserLaughCounter>> groupLaughCounter = new HashMap<>();

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        TextMessage replayMessage;

        switch (contentText.split(" ")[0]) {
            case "/echo" : {
                replayMessage = new TextMessage(contentText.replace("/echo", "").substring(1));
                break;
            }
            case "/toplaughers" : {
                replayMessage = handleTopLaughers(event);
                break;
            }
            default: {
                replayMessage = new TextMessage(contentText);

                Source source = event.getSource();
                String userId = source.getUserId();
                String groupId = getGroupId(event);

                if (groupId == null)
                    break;

                if (!groupLaughCounter.containsKey(groupId)) {
                    groupLaughCounter.put(groupId, new ArrayList<>());
                } else {
                    ArrayList<UserLaughCounter> userList = groupLaughCounter.get(groupId);
                    if (userList.stream().anyMatch(user -> user.getUserId().equals((userId))))
                        userList
                            .stream()
                            .filter(userLaugh -> userLaugh.getUserId().equals(userId))
                            .forEach(userLaugh -> userLaugh.increment());
                    else
                        userList.add(new UserLaughCounter(userId));
                }
                break;
            }
        }

        return replayMessage;
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    public TextMessage handleTopLaughers(Event event) {
        LOGGER.fine(String.format("Giving top 5 laughers in the group"));

        String userId = event.getSource().getUserId();
        String groupId = getGroupId(event);

        String message = "1. \n" +
                "2. \n" +
                "3. \n" +
                "4. \n" +
                "5. \n";

        if (groupId == null || !groupLaughCounter.containsKey(groupId))
            return message;

        message = "";

        if (groupLaughCounter.containsKey(groupId)) {
            ArrayList<UserLaughCounter> userList = groupLaughCounter.get(groupId);
            ArrayList<String>[] rankedLaugh = new ArrayList[6];

            userList.sort(new Comparator<UserLaughCounter>() {
                @Override
                public int compare(UserLaughCounter o1, UserLaughCounter o2) {
                    return o1.getCounter() > o2.getCounter();
                }
            });
        }

        return new TextMessage(message);
    }

    private String getGroupId(Event event) {
        Source source = event.getSource();
        String groupId = null;

        if (source instanceof GroupSource) {
            groupId = ((GroupSource) source).getGroupId();
        } else if (source instanceof RoomSource) {
            groupId = ((RoomSource) source).getRoomId();
        }

        return groupId;
    }
}
