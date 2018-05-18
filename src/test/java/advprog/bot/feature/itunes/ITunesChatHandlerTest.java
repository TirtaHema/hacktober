package advprog.bot.feature.itunes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ITunesChatHandlerTest {
    ITunesChatHandler itunesChatHandler;

    @Before
    public void setUp() {
        itunesChatHandler = new ITunesChatHandler(new BaseChatHandler());
    }

    @Test
    public void testContextLoads() {
        assertNotNull(itunesChatHandler);
    }

    @Test
    public void testHandleNoArtistEvent() throws IOException {
        String message = "/itunes_preview sadffasddfasdfas";
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Sorry, your artist is not in iTunes"));
        MessageEvent<TextMessageContent> me =
            ChatHandlerTestUtil.fakeMessageEvent("dsf", message);

        assertEquals(expectedMessages, itunesChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testHandleErrorEvent() throws IOException {
        String message = "/itunes_preview ";
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Please specify your artist"));
        MessageEvent<TextMessageContent> me =
            ChatHandlerTestUtil.fakeMessageEvent("dsf", message);

        assertEquals(expectedMessages, itunesChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testHandleSuccessEvent() throws IOException {
        String message = "/itunes_preview bruno";
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Bruno Mars"));
        MessageEvent<TextMessageContent> me =
            ChatHandlerTestUtil.fakeMessageEvent("dsf", message);

        assertEquals(expectedMessages, itunesChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(itunesChatHandler.canHandleAudioMessage(null));
        assertFalse(itunesChatHandler.canHandleImageMessage(null));
        assertFalse(itunesChatHandler.canHandleStickerMessage(null));
        assertFalse(itunesChatHandler.canHandleLocationMessage(null));
    }








}
