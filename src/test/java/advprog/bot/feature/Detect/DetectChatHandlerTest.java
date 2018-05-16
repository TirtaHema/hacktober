package advprog.bot.feature.Detect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.feature.Language.DetectChatHandler;
import advprog.bot.feature.echo.EchoChatHandler;
import advprog.bot.line.BaseChatHandler;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DetectChatHandlerTest {
   DetectChatHandler detectChatHandler;

    @Before
    public void setUp() {
        detectChatHandler = new DetectChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEvent() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("/detect_lang what"));
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(""));
        expectedMessages.add(new TextMessage(" aaa"));
        String msg = "/detect lang facebook.com";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(expectedMessages, echoChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(echoChatHandler.canHandleAudioMessage(null));
        assertFalse(echoChatHandler.canHandleImageMessage(null));
        assertFalse(echoChatHandler.canHandleStickerMessage(null));
        assertFalse(echoChatHandler.canHandleLocationMessage(null));
    }

}
