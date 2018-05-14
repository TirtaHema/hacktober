package advprog.bot.feature.docssimilarity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocsSimilarityChatHandlerTest {
    DocsSimilarityChatHandler docsSimilarityChatHandler;

    @Before
    public void setUp() {
        docsSimilarityChatHandler = new DocsSimilarityChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEventFrom() {
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("100%"));
        String msg = "/docs_sim 'my name is fahmi' 'your name is fahmi'";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(
                expectedMessages,
                docsSimilarityChatHandler.handleTextMessageEvent(me, messages)
        );
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(docsSimilarityChatHandler.canHandleAudioMessage(null));
        assertFalse(docsSimilarityChatHandler.canHandleImageMessage(null));
        assertFalse(docsSimilarityChatHandler.canHandleStickerMessage(null));
        assertFalse(docsSimilarityChatHandler.canHandleLocationMessage(null));
    }
}
