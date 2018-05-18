package advprog.bot.feature.enterkomputer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.feature.enterkomputer.EnterKomputerChatHandler;
import advprog.bot.line.BaseChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EnterKomputerChatHandlerTest {
    EnterKomputerChatHandler enterkomputerChatHandler;

    @Before
    public void setUp() {
        enterkomputerChatHandler = new EnterKomputerChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEvent() {
        String msg = "/billboard japan100";
        List<Message> messages = new LinkedList<>();
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
            "dsf", msg
        );
        assertNotNull(enterkomputerChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testContextLoad() {
        assertNotNull(enterkomputerChatHandler);
    }

    @Test
    public void testHandleErrorCategoryEvent() throws IOException {
        String msg = "/enterkomputer CATEGORY name";
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Sorry, we don't have the category"));
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
            "dsf", msg
        );
        assertEquals(expectedMessages, enterkomputerChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testHandleNoResultEvent() throws IOException {
        String msg = "/enterkomputer ssd thisisatest";
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(
            "Sorry, the product name is not available"));
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
            "dsf", msg
        );
        assertEquals(expectedMessages, enterkomputerChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testHandleSuccessEvent() throws IOException {
        String msg = "/enterkomputer ssd right";
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(
            "this is if the format is right"
        ));
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
            "dsf", msg
        );
        assertEquals(expectedMessages, enterkomputerChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testHandleNoCategoryEvent() throws IOException {
        String msg = "/enterkomputer ";
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(
            "Please input the category name"
        ));
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
            "dsf", msg
        );
        assertEquals(expectedMessages, enterkomputerChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testHandleNoNameEvent() throws IOException {
        String msg = "/enterkomputer ssd";
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(
            "Please input the name of the product"
        ));
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
            "dsf", msg
        );
        assertEquals(expectedMessages, enterkomputerChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(enterkomputerChatHandler.canHandleAudioMessage(null));
        assertFalse(enterkomputerChatHandler.canHandleImageMessage(null));
        assertFalse(enterkomputerChatHandler.canHandleStickerMessage(null));
        assertFalse(enterkomputerChatHandler.canHandleLocationMessage(null));
    }


}
