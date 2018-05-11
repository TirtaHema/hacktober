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
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutionException;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
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
    void testHandleTopLaughers() throws ExecutionException, InterruptedException {
        mock = spy(EchoController.class);
        doReturn("User1").when(mock).getUserDisplayName("Group1", "User1");
        doReturn("User2").when(mock).getUserDisplayName("Group1", "User2");
        doReturn("User3").when(mock).getUserDisplayName("Group1", "User3");

        MessageEvent<TextMessageContent> event;
        TextMessage reply;
        String expected;

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("hi hahahah", "Group1", "User1");
        mock.handleTextMessageEvent(event);

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("/toplaughers", "Group1", "User1");
        reply = mock.handleTextMessageEvent(event);

        expected = "1. User1\n" +
                "2. \n" +
                "3. \n" +
                "4. \n" +
                "5. \n";

        assertEquals(expected, reply.getText());

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("wkwkwk*", "Group1", "User1");
        mock.handleTextMessageEvent(event);

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("test", "Group1", "User2");
        mock.handleTextMessageEvent(event);

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("haha aja", "Group1", "User2");
        mock.handleTextMessageEvent(event);

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("wkwk lah", "Group1", "User3");
        mock.handleTextMessageEvent(event);

        event = EventTestUtil.createDummyGroupTextMessageWithDummyUser("/toplaughers", "Group1", "User1");
        reply = mock.handleTextMessageEvent(event);

        expected = "1. User1\n" +
                "2. User2, User3\n" +
                "3. \n" +
                "4. \n" +
                "5. \n";

        assertEquals(expected, reply.getText());
    }
}