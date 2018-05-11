package advprog.example.bot;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.UserSource;

import java.time.Instant;

public class EventTestUtil {

    private EventTestUtil() {
        // Default private constructor
    }

    public static MessageEvent<TextMessageContent> createDummyTextMessage(String text) {
        return new MessageEvent<>("replyToken", new UserSource("userId"),
                new TextMessageContent("id", text),
                Instant.parse("2018-01-01T00:00:00.000Z"));
    }

    public static MessageEvent<TextMessageContent> createDummyGroupTextMessageWithDummyUser(String text, String groupId, String userId) {
        return new MessageEvent<>("replyToken", new GroupSource(groupId, userId),
                new TextMessageContent("id", text),
                Instant.parse("2018-01-01T00:00:00.000Z"));
    }

    public static MessageEvent<TextMessageContent> createDummyRoomTextMessageWithDummyUser(String text, String userId, String roomId) {
        return new MessageEvent<>("replyToken", new RoomSource(userId, roomId),
                new TextMessageContent("id", text),
                Instant.parse("2018-01-01T00:00:00.000Z"));
    }

    public static MessageEvent<TextMessageContent> createDummyPrivateTextMessageWithDummyUser(String text, String userId) {
        return new MessageEvent<>("replyToken", new UserSource(userId),
                new TextMessageContent("id", text),
                Instant.parse("2018-01-01T00:00:00.000Z"));
    }
}
