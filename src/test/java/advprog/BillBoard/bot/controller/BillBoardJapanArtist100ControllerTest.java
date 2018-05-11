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
public class BillBoardJapanArtist100ControllerTest {
    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private BillBoardJapanArtist100Controller billboardjapanArtistController;

    @Test
    void testContextLoads() {
        assertNotNull(billboardjapanArtistController);
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        billboardjapanArtistController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    void testHandleTextMessageEventIfSuccess() {
        MessageEvent<TextMessageContent> event =
            EventTestUtil.createDummyTextMessage("/billboard japan100 Drake");

        TextMessage reply = billboardjapanArtistController.handleTextMessageEvent(event);

        assertEquals("Drake\nNice For What\n2", reply.getText());
    }

    @Test
    void testHandleTextMessageEventIfFailed() {
        MessageEvent<TextMessageContent> event =
            EventTestUtil.createDummyTextMessage("/billboard japan100 Coldplay");

        TextMessage reply = billboardjapanArtistController.handleTextMessageEvent(event);

        assertEquals("The artist Coldplay is not in Japan Top 100 chart", reply.getText());

    }

}
