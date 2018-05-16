package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import advprog.example.bot.EventTestUtil;
import com.linecorp.bot.model.event.MessageEvent;

import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import com.linecorp.bot.model.message.TextMessage;

import java.util.logging.Logger;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



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
    private static final Logger LOGGER = Logger.getLogger(TwitterBotController.class.getName());


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
        LOGGER.info("This is debugging process");
        LOGGER.info(reply.getText());
        String expected = "asuuuu\n"
                + "really frustating to have you in my life\n"
                + "no no no no\n"
                + "yo what's up people\n"
                +  "Lol i didn't see that coming\n";

        assertEquals(expected, reply.getText());
    }

    @Test
    public void canHandleTextMessage() {
        assertFalse(twitterController.canHandleTextMessage(null));
    }

    @Test
    public void canHandleImageMessage() {
        assertFalse(twitterController.canHandleImageMessage(null));
    }

    @Test
    public void canHandleAudioMessage() {
        assertFalse(twitterController.canHandleAudioMessage(null));
    }

    @Test
    public void canHandleStickerMessage() {
        assertFalse(twitterController.canHandleStickerMessage(null));
    }

    @Test
    public void canHandleLocationMessage() {
        assertFalse(twitterController.canHandleLocationMessage(null));
    }
}