package advprog.bot.feature.fakenews;

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


public class NewsChatHandlerTest {
    FakeNewsChatHandler fakeNewsChatHandler;

    @Before
    public void setUp() {
        fakeNewsChatHandler = new FakeNewsChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEventFake() {
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("FAKE\n"));
        String msg = "/is_fake http://conservativespirit.com";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(
                expectedMessages,
                fakeNewsChatHandler.handleTextMessageEvent(me, messages)
        );
    }

    @Test
    public void testHandleTextMessageEventSatire() {
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("SATIRE\nSite notes it sometimes contains parody"));
        String msg = "/is_satire http://washingtonsblog.com";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(
                expectedMessages,
                fakeNewsChatHandler.handleTextMessageEvent(me, messages)
        );
    }

    @Test
    public void testHandleTextMessageEventConspiracy() {
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("CONSPIRACY\nglobal warming denial"));
        String msg = "/is_conspiracy http://FreedomForceInternational.com";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(
                expectedMessages,
                fakeNewsChatHandler.handleTextMessageEvent(me, messages)
        );
    }

    @Test
    public void testHandleTextMessageEventAddFilter() {
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Filter successfully added"));
        String msg = "/add_filter http://halo.com FAKE";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(
                expectedMessages,
                fakeNewsChatHandler.handleTextMessageEvent(me, messages)
        );

        messages = new LinkedList<>();
        expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("FAKE\nAdded by user"));
        msg = "/is_fake http://halo.com";
        me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(
                expectedMessages,
                fakeNewsChatHandler.handleTextMessageEvent(me, messages)
        );
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(fakeNewsChatHandler.canHandleAudioMessage(null));
        assertFalse(fakeNewsChatHandler.canHandleImageMessage(null));
        assertFalse(fakeNewsChatHandler.canHandleStickerMessage(null));
        assertFalse(fakeNewsChatHandler.canHandleLocationMessage(null));
    }
}
