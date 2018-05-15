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

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import advprog.example.bot.controller.TwitterBotController;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TwitterControllerTestConfiguration.class)
public class TwitterControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private TwitterBotController twitterController;

    public TwitterControllerTest() {
        twitterController = new TwitterBotController();
    }

    @Test
    public void testContextLoads() {
        assertNotNull(twitterController);
    }

    @Test
    public void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/tweet recent williamrumanta");

        TextMessage reply = twitterController.handleTextMessageEvent(event);
        System.out.println("heloooo");
        System.out.println(reply.getText());
    }

//    @Test
//    public void testHandleDefaultMessage() {
//        Event event = mock(Event.class);
//
//        twitterController.handleDefaultMessage(event);
//
//        verify(event, atLeastOnce()).getSource();
//        verify(event, atLeastOnce()).getTimestamp();
//    }

//    @Test
//    public void testStubFunction() {
//        String result = twitterController.stubFunction();
//        String expected = "Ok";
//
//        assertEquals(expected, result);
//    }
}