package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import advprog.example.bot.EventTestUtil;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
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
        String expected = "asuuuu(Tue May 15 19:43:50 ICT 2018)\n"
                + "really frustating to have you in my life(Mon May 14 16:27:09 ICT 2018)\n"
                + "no no no no(Mon May 14 16:26:50 ICT 2018)\n"
                + "yo what's up people(Mon May 14 16:26:45 ICT 2018)\n"
                +  "Lol i didn't see that coming(Mon May 14 16:26:34 ICT 2018)\n";

        assertEquals(expected, reply.getText());
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