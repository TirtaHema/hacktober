package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    void testHandleDocumentsSimilarityTextCheck() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/docs_sim "
                        + "'my name is fahmi' 'your name is fahmi'");

        TextMessage reply = echoController.handleTextMessageEvent(event);
        assertEquals("100%", reply.getText());
    }

    @Test
    void testHandleDocumentsSimilarityUrlCheck() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/docs_sim "
                        + "https://www.biography.com/people/adolf-hitler-9340144 "
                        + "https://en.wikipedia.org/wiki/Adolf_Hitler");

        TextMessage reply = echoController.handleTextMessageEvent(event);
        assertEquals("87%", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}