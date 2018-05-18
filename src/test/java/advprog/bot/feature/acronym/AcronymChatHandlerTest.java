package advprog.bot.feature.acronym;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AcronymChatHandlerTest {
    //TODO: GROUP CHAT, CAROUSEL INTERACTION
    AcronymChatHandler acronymChatHandler;

    @Before
    public void setUp() {
        acronymChatHandler = new AcronymChatHandler(new BaseChatHandler());
    }

    @Test
    public void testAddAcronym() {
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();

        expectedMessages.add(new TextMessage("Please write your acronym"));
        MessageEvent<TextMessageContent> me = TestUtil.createDummyPrivateTextMessage("/add_scronym");
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        expectedMessages.add(new TextMessage("Please specify the expansion"));
        me = TestUtil.createDummyPrivateTextMessage("ACR");
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        expectedMessages.add(new TextMessage("Your acronym "
                + "\"ACR\" and it's expansion "
                + "\"Acronym\" has been added"));
        me = TestUtil.createDummyPrivateTextMessage("Acronym");
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testUpdateAcronym() {
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Which acronym you want to update?"));
        expectedMessages.add(new TextMessage("Carousel here"));
        MessageEvent<TextMessageContent> me = TestUtil.createDummyPrivateTextMessage("/update_acronym");
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        expectedMessages.add(new TextMessage("Please specify the new expansion"));
        String msg = "Pick ACR from list";
        me = TestUtil.createDummyPrivateTextMessage("ACR");
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        expectedMessages.add(new TextMessage("Acronym "
                + "\"ACR\" has been updated with new expansion "
                + "\"Acronym\""));
        msg = "Acronym";
        me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testDeleteAcronym() {
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Which acronym you want to delete?"));
        expectedMessages.add(new TextMessage("Carousel here"));
        String msg = "/delete_acronym";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        expectedMessages.add(new TextMessage("Are you sure you want to delete ACR?"));
        msg = "Pick ACR from list";
        me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        expectedMessages.add(new TextMessage("Acronym "
                + "\"ACR\" with expansion "
                + "\"Acronym\" has been deleted"));
        msg = "Yes";
        me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(acronymChatHandler.canHandleAudioMessage(null));
        assertFalse(acronymChatHandler.canHandleImageMessage(null));
        assertFalse(acronymChatHandler.canHandleStickerMessage(null));
        assertFalse(acronymChatHandler.canHandleLocationMessage(null));
    }

}
