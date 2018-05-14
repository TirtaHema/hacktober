package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.Mockito.*;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutionException;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EchoControllerTestConfiguration.class)
public class EchoControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private EchoController echoController;

    private EchoController mock;

    @Test
    void testContextLoads() {
        assertNotNull(echoController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    void testHandleSparseGroupTopLaughers() throws ExecutionException, InterruptedException {
        mock = spy(EchoController.class);
        doReturn("User1")
                .when(mock).getUserDisplayName(new GroupSource("Group1", "User1"));
        doReturn("User2")
                .when(mock).getUserDisplayName(new GroupSource("Group1", "User2"));

        MessageEvent<TextMessageContent> event;
        TextMessage reply;
        String expected;

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("hi hahahah", "Group1", "User1");
        mock.handleTextMessageEvent(event);

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("WKWK", "Group1", "User2");
        mock.handleTextMessageEvent(event);

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("/toplaughers", "Group1", "User1");
        reply = mock.handleTextMessageEvent(event);

        expected = "1. User1(50%), User2(50%)\n" +
                "2. \n" +
                "3. \n" +
                "4. \n" +
                "5. \n";

        assertEquals(expected, reply.getText());
    }

    @Test
    public void testHandleDenseGroupTopLaughers() {
        mock = spy(EchoController.class);
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
            event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("hahahah", "Group1", "User1");
            mock.handleTextMessageEvent(event);
        }

        for (int i = 0; i < 30; i++) {
            event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("HAHAHAH", "Group1", "User2");
            mock.handleTextMessageEvent(event);
        }

        for (int i = 0; i < 20; i++) {
            event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("WKWK", "Group1", "User3");
            mock.handleTextMessageEvent(event);
        }

        for (int i = 0; i < 6; i++) {
            event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("wkwkwk", "Group1", "User4");
            mock.handleTextMessageEvent(event);
        }

        for (int i = 0; i < 4; i++) {
            event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("WKWK HAHA", "Group1", "User5");
            mock.handleTextMessageEvent(event);
        }

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("/toplaughers", "Group1", "User1");
        reply = mock.handleTextMessageEvent(event);

        expected = "1. User1(40%)\n" +
                "2. User2(30%)\n" +
                "3. User3(20%)\n" +
                "4. User4(6%)\n" +
                "5. User5(4%)\n";

        assertEquals(expected, reply.getText());
    }

    @Test
    public void testHandleNotLaughMessage() {
        mock = spy(EchoController.class);
        doReturn("User1")
                .when(mock).getUserDisplayName(new GroupSource("Group1", "User1"));

        MessageEvent<TextMessageContent> event;
        TextMessage reply;
        String expected;

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("test", "Group1", "User1");
        mock.handleTextMessageEvent(event);

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("/toplaughers", "Group1", "User1");
        reply = mock.handleTextMessageEvent(event);

        expected = "1. \n" +
                "2. \n" +
                "3. \n" +
                "4. \n" +
                "5. \n";

        assertEquals(expected, reply.getText());
    }

    @Test
    void testHandleRoomChatTopLaughers() throws ExecutionException, InterruptedException {
        mock = spy(EchoController.class);
        doReturn("User1")
                .when(mock).getUserDisplayName(new RoomSource("User1", "Room1"));
        doReturn("User2")
                .when(mock).getUserDisplayName(new RoomSource("User2", "Room1"));

        MessageEvent<TextMessageContent> event;
        TextMessage reply;
        String expected;

        event = EventTestUtil.createDummyRoomTextMessageWithDummyUser("hahahahh", "User1", "Room1");
        mock.handleTextMessageEvent(event);

        event = EventTestUtil.createDummyRoomTextMessageWithDummyUser("wkwkkwk", "User2", "Room1");
        mock.handleTextMessageEvent(event);

        event = EventTestUtil.createDummyRoomTextMessageWithDummyUser("/toplaughers", "User1", "Room1");
        reply = mock.handleTextMessageEvent(event);

        expected = "1. User1(50%), User2(50%)\n" +
                "2. \n" +
                "3. \n" +
                "4. \n" +
                "5. \n";

        assertEquals(expected, reply.getText());
    }

    @Test
    void testHandlePrivateChatTopLaughers() throws ExecutionException, InterruptedException {
        mock = spy(EchoController.class);
        doReturn("User1")
                .when(mock).getUserDisplayName(new UserSource("User1"));

        MessageEvent<TextMessageContent> event;
        TextMessage reply;
        String expected;

        event = EventTestUtil.createDummyPrivateTextMessageWithDummyUser("haha", "User1");
        mock.handleTextMessageEvent(event);

        event = EventTestUtil.createDummyPrivateTextMessageWithDummyUser("/toplaughers", "User1");
        reply = mock.handleTextMessageEvent(event);

        expected = "1. User1(100%)\n" +
                "2. \n" +
                "3. \n" +
                "4. \n" +
                "5. \n";

        assertEquals(expected, reply.getText());
    }
}