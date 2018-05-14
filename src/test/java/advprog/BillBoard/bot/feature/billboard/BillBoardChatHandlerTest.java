package advprog.BillBoard.bot.feature.billboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


import advprog.BillBoard.bot.feature.billboard.BillBoardChatHandler;
import advprog.bot.ChatHandlerTestUtil;
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
public class BillBoardChatHandlerTest {

    BillBoardChatHandler billboardChatHandler;

    @Before
    public void setUp() {
        billboardChatHandler = new BillBoardChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEvent() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("bbb"));
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("bbb"));
        expectedMessages.add(new TextMessage(" aaa"));
        String msg = "/echo aaa";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
            "dsf", msg
        );
        assertEquals(expectedMessages, billboardChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(billboardChatHandler.canHandleAudioMessage(null));
        assertFalse(billboardChatHandler.canHandleImageMessage(null));
        assertFalse(billboardChatHandler.canHandleStickerMessage(null));
        assertFalse(billboardChatHandler.canHandleLocationMessage(null));
    }
}
