package advprog.bot.feature.uberestimate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UberEstimateChatHandlerTest {
    UberEstimateChatHandler uberEstimateChatHandler;

    @Before
    public void setUp() {
        uberEstimateChatHandler = new UberEstimateChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEventUberWithNoLocationSaved() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("bbb"));
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("bbb"));
        expectedMessages.add(new TextMessage("No location saved"));
        String msg = "/uber";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "ggg", msg
        );
        assertEquals(expectedMessages,
                uberEstimateChatHandler.handleTextMessageEvent(me, messages)
        );
    }

    @Test
    public void testHandleTextMessageEventAddDestinationWithNoName() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("bbb"));
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("bbb"));
        expectedMessages.add(new TextMessage("Please enter /add_destination [name]"));
        String msg = "/add_destination";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "hhh", msg
        );
        assertEquals(expectedMessages,
                uberEstimateChatHandler.handleTextMessageEvent(me, messages)
        );
    }

    @Test
    public void testHandleTextMessageEventAddDestinationWithName() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("bbb"));
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("bbb"));
        expectedMessages.add(new TextMessage("Please share your location"));
        String msg = "/add_destination haha";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(expectedMessages,
                uberEstimateChatHandler.handleTextMessageEvent(me, messages)
        );
    }

    @Test
    public void testHandleTextMessageEventLatitudeAndLongitude() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("bbb"));
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("bbb"));
        expectedMessages.add(new TextMessage("Please share your location"));
        String msg = "lat= 35.701262 lon= 139.787957 jeoang Japan, "
                + "〒111-0053 Tōkyō-to, Taitō-ku, Asakusabashi, 3 Chome−24−４ 高橋歯科医院 haha";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "asfposakkfopas", msg
        );
        assertEquals(expectedMessages,
                uberEstimateChatHandler.handleTextMessageEvent(me, messages)
        );
    }

    @Test
    public void testHandleTextMessageNotValidCommand() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("bbb"));
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("bbb"));
        String msg = "haha";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "yyy", msg
        );
        assertEquals(expectedMessages,
                uberEstimateChatHandler.handleTextMessageEvent(me, messages)
        );
    }

    @Test
    public void testHandleLocationMessageEventLatitudeAndLongitudeWithNoLastQueries() {
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("haha "));
        MessageEvent<LocationMessageContent> me = ChatHandlerTestUtil.fakeLocationEvent(
                "ggg", 1.1, 2.2
        );
        assertEquals(expectedMessages,
                uberEstimateChatHandler.handleLocationMessage(me)
        );
    }

    @Test
    public void testHandleLocationMessageEventLatitudeAndLongitudeWithAddLastQueries() {
        List<Message> messages2 = new LinkedList<>();
        messages2.add(new TextMessage("bbb"));
        String msg = "/add_destination HAHA";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "yyy", msg
        );
        uberEstimateChatHandler.handleTextMessageEvent(me, messages2);

        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Location Saved"));
        MessageEvent<LocationMessageContent> me2 = ChatHandlerTestUtil.fakeLocationEvent(
                "ggg", 1.1, 2.2
        );
        assertEquals(expectedMessages, uberEstimateChatHandler.handleLocationMessage(me2));
    }


    @Test
    public void testEventHandler() {
        assertFalse(uberEstimateChatHandler.canHandleAudioMessage(null));
        assertFalse(uberEstimateChatHandler.canHandleImageMessage(null));
        assertFalse(uberEstimateChatHandler.canHandleStickerMessage(null));
        assertTrue(uberEstimateChatHandler.canHandleLocationMessage(null));
    }

}
