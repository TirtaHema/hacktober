package advprog.bot.feature.toplaughers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutionException;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class TopLaughersChatHandlerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private TopLaughersChatHandler topLaughersController;

    private TopLaughersChatHandler mock;

    @Test
    void testContextLoads() {
        assertNotNull(topLaughersController);
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        MessageEvent<TextMessageContent> event;

        event = EventTestUtil
                .createDummyGroupTextMessageWithDummyUser("/toplaughers", "Group1", "User1");
        assertTrue(topLaughersController.canHandleTextMessage(event));
        assertFalse(topLaughersController.canHandleAudioMessage(null));
        assertFalse(topLaughersController.canHandleImageMessage(null));
        assertFalse(topLaughersController.canHandleStickerMessage(null));
        assertFalse(topLaughersController.canHandleLocationMessage(null));
    }

    @Test
    void testHandleSparseGroupTopLaughers() throws ExecutionException, InterruptedException {
        mock = spy(TopLaughersChatHandler.class);
        doReturn("User1")
                .when(mock).getUserDisplayName(new GroupSource("Group1", "User1"));
        doReturn("User2")
                .when(mock).getUserDisplayName(new GroupSource("Group1", "User2"));

        MessageEvent<TextMessageContent> event;
        TextMessage reply;
        String expected;

        event = EventTestUtil
                .createDummyGroupTextMessageWithDummyUser("hi hahahah", "Group1", "User1");
        mock.handleTextMessage(event);

        event = EventTestUtil
                .createDummyGroupTextMessageWithDummyUser("WKWK", "Group1", "User2");
        mock.handleTextMessage(event);

        event = EventTestUtil
                .createDummyGroupTextMessageWithDummyUser("/toplaughers", "Group1", "User1");
        reply = (TextMessage) mock.handleTextMessage(event).get(0);

        expected = "1. User1(50%), User2(50%)\n"
                + "2. \n"
                + "3. \n"
                + "4. \n"
                + "5. \n";

        assertEquals(expected, reply.getText());
    }

    @Test
    public void testHandleDenseGroupTopLaughers()
            throws ExecutionException, InterruptedException {
        mock = spy(TopLaughersChatHandler.class);
        doReturn("User1")
                .when(mock).getUserDisplayName(new GroupSource("Group1", "User1"));
        doReturn("User2")
                .when(mock).getUserDisplayName(new GroupSource("Group1", "User2"));
        doReturn("User3")
                .when(mock).getUserDisplayName(new GroupSource("Group1", "User3"));
        doReturn("User4")
                .when(mock).getUserDisplayName(new GroupSource("Group1", "User4"));
        doReturn("User5")
                .when(mock).getUserDisplayName(new GroupSource("Group1", "User5"));

        MessageEvent<TextMessageContent> event;
        TextMessage reply;
        String expected;

        for (int i = 0; i < 40; i++) {
            event = EventTestUtil
                    .createDummyGroupTextMessageWithDummyUser("hahahah", "Group1", "User1");
            mock.handleTextMessage(event);
        }

        for (int i = 0; i < 30; i++) {
            event = EventTestUtil
                    .createDummyGroupTextMessageWithDummyUser("HAHAHAH", "Group1", "User2");
            mock.handleTextMessage(event);
        }

        for (int i = 0; i < 20; i++) {
            event = EventTestUtil
                    .createDummyGroupTextMessageWithDummyUser("WKWK", "Group1", "User3");
            mock.handleTextMessage(event);
        }

        for (int i = 0; i < 6; i++) {
            event = EventTestUtil
                    .createDummyGroupTextMessageWithDummyUser("wkwkwk", "Group1", "User4");
            mock.handleTextMessage(event);
        }

        for (int i = 0; i < 4; i++) {
            event = EventTestUtil
                    .createDummyGroupTextMessageWithDummyUser("WKWK HAHA", "Group1", "User5");
            mock.handleTextMessage(event);
        }

        event = EventTestUtil
                .createDummyGroupTextMessageWithDummyUser("/toplaughers", "Group1", "User1");
        reply = (TextMessage) mock.handleTextMessage(event).get(0);

        expected = "1. User1(40%)\n"
                + "2. User2(30%)\n"
                + "3. User3(20%)\n"
                + "4. User4(6%)\n"
                + "5. User5(4%)\n";

        assertEquals(expected, reply.getText());
    }

    @Test
    public void testHandleNotLaughMessage() throws ExecutionException, InterruptedException {
        mock = spy(TopLaughersChatHandler.class);
        doReturn("User1")
                .when(mock).getUserDisplayName(new GroupSource("Group1", "User1"));

        MessageEvent<TextMessageContent> event;
        TextMessage reply;
        String expected;

        event = EventTestUtil
                .createDummyGroupTextMessageWithDummyUser("test", "Group1", "User1");
        mock.handleTextMessage(event);

        event = EventTestUtil
                .createDummyGroupTextMessageWithDummyUser("/toplaughers", "Group1", "User1");
        reply = (TextMessage) mock.handleTextMessage(event).get(0);

        expected = "1. \n"
                + "2. \n"
                + "3. \n"
                + "4. \n"
                + "5. \n";

        assertEquals(expected, reply.getText());
    }

    @Test
    void testHandleRoomChatTopLaughers() throws ExecutionException, InterruptedException {
        mock = spy(TopLaughersChatHandler.class);
        doReturn("User1")
                .when(mock).getUserDisplayName(new RoomSource("User1", "Room1"));
        doReturn("User2")
                .when(mock).getUserDisplayName(new RoomSource("User2", "Room1"));

        MessageEvent<TextMessageContent> event;
        TextMessage reply;
        String expected;

        event = EventTestUtil
                .createDummyRoomTextMessageWithDummyUser("hahahahh", "User1", "Room1");
        mock.handleTextMessage(event);

        event = EventTestUtil
                .createDummyRoomTextMessageWithDummyUser("wkwkkwk", "User2", "Room1");
        mock.handleTextMessage(event);

        event = EventTestUtil
                .createDummyRoomTextMessageWithDummyUser("/toplaughers", "User1", "Room1");
        reply = (TextMessage) mock.handleTextMessage(event).get(0);

        expected = "1. User1(50%), User2(50%)\n"
                + "2. \n"
                + "3. \n"
                + "4. \n"
                + "5. \n";

        assertEquals(expected, reply.getText());
    }

    @Test
    void testHandlePrivateChatTopLaughers() throws ExecutionException, InterruptedException {
        mock = spy(TopLaughersChatHandler.class);
        doReturn("User1")
                .when(mock).getUserDisplayName(new UserSource("User1"));

        MessageEvent<TextMessageContent> event;
        TextMessage reply;
        String expected;

        event = EventTestUtil
                .createDummyPrivateTextMessageWithDummyUser("haha", "User1");
        mock.handleTextMessage(event);

        event = EventTestUtil
                .createDummyPrivateTextMessageWithDummyUser("/toplaughers", "User1");
        reply = (TextMessage) mock.handleTextMessage(event).get(0);

        expected = "1. User1(100%)\n"
                + "2. \n"
                + "3. \n"
                + "4. \n"
                + "5. \n";

        assertEquals(expected, reply.getText());
    }
}
