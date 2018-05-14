package advprog.bot.feature.toplaughers;

import advprog.bot.feature.toplaughers.resources.UserLaughCounter;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import advprog.example.bot.controller.EchoController;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class TopLaughersChatHandler extends AbstractLineChatHandlerDecorator {
    @Autowired
    private LineMessagingClient lineMessagingClient;

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());

    private HashMap<String, ArrayList<UserLaughCounter>> groupLaughCounter = new HashMap<>();
    private HashMap<String, Integer> groupTotalLaughCounter = new HashMap<>();

    public TopLaughersChatHandler() {

    }

    public TopLaughersChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Top Laughers chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return true;
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event)
            throws ExecutionException, InterruptedException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        TextMessage replayMessage = new TextMessage("");

        switch (contentText.split(" ")[0]) {
            case "/toplaughers" : {
                replayMessage = handleTopLaughers(event);
                break;
            }
            default: {
                Source source = event.getSource();
                String groupId = getGroupId(source);

                if (!contentText.toLowerCase().contains("haha")
                        && !contentText.toLowerCase().contains("wkwk")) {
                    break;
                }

                if (!groupLaughCounter.containsKey(groupId)) {
                    groupLaughCounter.put(groupId, new ArrayList<>());
                    groupTotalLaughCounter.put(groupId, 0);
                }

                groupTotalLaughCounter.put(groupId, groupTotalLaughCounter.get(groupId) + 1);
                ArrayList<UserLaughCounter> userList = groupLaughCounter.get(groupId);

                String userId = source.getUserId();
                if (userList.stream().anyMatch(user -> user.getUserId().equals((userId)))) {
                    userList
                            .stream()
                            .filter(userLaugh -> userLaugh.getUserId().equals(userId))
                            .forEach(userLaugh -> userLaugh.increment());
                } else {
                    userList.add(new UserLaughCounter(userId));
                }

                break;
            }
        }

        return Collections.singletonList(
                replayMessage
        );
    }

    @Override
    protected boolean canHandleImageMessage(MessageEvent<ImageMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleAudioMessage(MessageEvent<AudioMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleStickerMessage(MessageEvent<StickerMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleLocationMessage(MessageEvent<LocationMessageContent> event) {
        return false;
    }

    public TextMessage handleTopLaughers(Event event)
            throws ExecutionException, InterruptedException {
        LOGGER.fine(String.format("Giving top 5 laughers in the group"));

        Source source = event.getSource();
        String groupId = getGroupId(source);

        String message = "1. \n"
                + "2. \n"
                + "3. \n"
                + "4. \n"
                + "5. \n";

        if (!groupLaughCounter.containsKey(groupId) || groupLaughCounter.get(groupId).size() == 0) {
            return new TextMessage(message);
        }

        message = "";

        if (groupLaughCounter.containsKey(groupId)) {
            ArrayList<UserLaughCounter> userList = groupLaughCounter.get(groupId);
            ArrayList<ArrayList<String>> rankedLaugh = new ArrayList<>();
            ArrayList<ArrayList<Integer>> laughCounts = new ArrayList<>();

            Integer groupTotalLaugh = groupTotalLaughCounter.get(groupId);

            for (int i = 0; i < 10; i++) {
                rankedLaugh.add(new ArrayList<>());
                laughCounts.add(new ArrayList<>());
            }

            userList.sort(new Comparator<UserLaughCounter>() {
                @Override
                public int compare(UserLaughCounter o1, UserLaughCounter o2) {
                    return o2.getCounter() - o1.getCounter();
                }
            });

            int prevLaughCounter = userList.get(0).getCounter();
            int rankCounter = 1;

            for (int i = 0; i < userList.size() && rankCounter < 6; i++) {
                UserLaughCounter currentUser = userList.get(i);

                if (prevLaughCounter > currentUser.getCounter()) {
                    prevLaughCounter = currentUser.getCounter();
                    rankCounter++;
                }

                String userId = currentUser.getUserId();
                if (source instanceof GroupSource) {
                    rankedLaugh.get(rankCounter)
                            .add(getUserDisplayName(new GroupSource(groupId, userId)));
                } else if (source instanceof RoomSource) {
                    rankedLaugh.get(rankCounter)
                            .add(getUserDisplayName(new RoomSource(userId, groupId)));
                } else {
                    rankedLaugh.get(rankCounter)
                            .add(getUserDisplayName(new UserSource(userId)));
                }

                laughCounts.get(rankCounter)
                        .add(currentUser.getCounter());
            }

            for (int i = 1; i <= 5; i++) {
                message += i + ". ";
                for (int j = 0; j < rankedLaugh.get(i).size(); j++) {
                    if (j > 0) {
                        message += ", ";
                    }
                    message += rankedLaugh.get(i).get(j)
                            + "(" + getLaughPercentage(laughCounts.get(i).get(j), groupTotalLaugh)
                            + ")";
                }
                message += "\n";
            }
        }

        return new TextMessage(message);
    }

    public String getGroupId(Source source) {
        String groupId;

        if (source instanceof GroupSource) {
            groupId = ((GroupSource) source).getGroupId();
        } else if (source instanceof RoomSource) {
            groupId = ((RoomSource) source).getRoomId();
        } else {
            groupId = source.getUserId();
        }

        return groupId;
    }

    public String getUserDisplayName(Source source)
            throws ExecutionException, InterruptedException {
        String groupId = getGroupId(source);
        String userId = source.getUserId();
        String displayName = "";

        if (source instanceof GroupSource) {
            displayName = lineMessagingClient
                    .getGroupMemberProfile(groupId, userId).get().getDisplayName();
        } else if (source instanceof RoomSource) {
            displayName = lineMessagingClient
                    .getRoomMemberProfile(groupId, userId).get().getDisplayName();
        } else {
            displayName = lineMessagingClient.getProfile(userId).get().getDisplayName();
        }

        return displayName;
    }

    public String getLaughPercentage(Integer laughCounter, Integer groupTotalLaugh) {
        Double nominator = Double.parseDouble(laughCounter.toString());
        Double denominator = Double.parseDouble(groupTotalLaugh.toString());

        Double doublePercentage = nominator / denominator * 100;
        Integer intPercentage = doublePercentage.intValue();

        return intPercentage.toString() + "%";
    }
}
