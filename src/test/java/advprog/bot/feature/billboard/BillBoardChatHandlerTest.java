package advprog.bot.feature.billboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;
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
    public void testHandleSuccessEvent() {
        BillBoardOperation operation = new BillBoardOperation();
        ArrayList<String> artists = operation.getArrayArtist();
        String msg = "/billboard bill200 " + artists.get(0);
        List<Message> messages = new LinkedList<>();
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
            "dsf", msg
        );
        assertNotNull(billboardChatHandler.handleTextMessageEvent(me, messages));

    }

    @Test
    public void testHandleFailedEvent() {
        String msg = "/billboard bill200 Regensa";
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(
            new TextMessage(
                "Sorry, your artist is not valid or doesn't make it to Billboard top 200")
        );
        MessageEvent<TextMessageContent> me =
            ChatHandlerTestUtil.fakeMessageEvent("dsf", msg
            );

        List<Message> reply = billboardChatHandler.handleTextMessageEvent(me,messages);

        assertEquals(expectedMessages, reply);
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(billboardChatHandler.canHandleAudioMessage(null));
        assertFalse(billboardChatHandler.canHandleImageMessage(null));
        assertFalse(billboardChatHandler.canHandleStickerMessage(null));
        assertFalse(billboardChatHandler.canHandleLocationMessage(null));
    }
}
