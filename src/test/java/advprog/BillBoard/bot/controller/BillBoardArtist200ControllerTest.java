package advprog.BillBoard.bot.controller;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class BillBoardArtist200ControllerTest {
    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private BillBoardArtist200Controller billboardartistController;

    @Test
    void testContextLoads() {
        assertNotNull(billboardartistController);
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        billboardartistController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    void testHandleTextMessageEventIfSuccess() {
        MessageEvent<TextMessageContent> event =
            EventTestUtil.createDummyTextMessage("/billboard bill200 Drake");

        TextMessage reply = billboardartistController.handleTextMessageEvent(event);

        assertEquals("Drake\nNice For What\n2", reply.getText());
    }

    @Test
    void testHandleTextMessageEventIfFailed() {
        MessageEvent<TextMessageContent> event =
            EventTestUtil.createDummyTextMessage("/billboard bill200 Coldplay");

        TextMessage reply = billboardartistController.handleTextMessageEvent(event);

        assertEquals("The artist Coldplay is not in Billboard Top 200 chart", reply.getText());

    }
}