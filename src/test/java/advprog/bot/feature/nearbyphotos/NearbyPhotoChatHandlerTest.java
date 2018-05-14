package advprog.bot.feature.nearbyphotos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.feature.nearbyphotos.NearbyPhotosChatHandler;
import advprog.bot.line.BaseChatHandler;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
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

public class NearbyPhotoChatHandlerTest {

    NearbyPhotosChatHandler nearbyPhotosChatHandler;

    @Before
    public void setUp() {
        nearbyPhotosChatHandler = new NearbyPhotosChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEventNearbyPhotos() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("nearby photos"));
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("nearby photos"));
        expectedMessages.add(new TextMessage("Please share your location"));
        String msg = "nearby photos";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(expectedMessages,
                nearbyPhotosChatHandler.handleTextMessageEvent(
                        me, messages));
    }

    @Test
    public void testHandleTextMessageEventNotNearbyPhotos() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("not a thing"));
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("not a thing"));
        String msg = "not a thing";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(expectedMessages,
                nearbyPhotosChatHandler.handleTextMessageEvent(
                        me, messages));
    }

    @Test
    public void testLocationMessageEventSuccess() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("nearby photos"));
        String msg = "nearby photos";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        MessageEvent<LocationMessageContent> loc = ChatHandlerTestUtil.fakeLocationEvent(
            "dsf",33.1376, 81.8262
        );
        nearbyPhotosChatHandler.handleTextMessageEvent(me, messages);
        List<Message> messages2 = new LinkedList<>();
        messages.add(new TextMessage("No photo found"));
        nearbyPhotosChatHandler.handleLocationMessageEvent(loc, messages2);

    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(nearbyPhotosChatHandler.canHandleAudioMessage(null));
        assertFalse(nearbyPhotosChatHandler.canHandleImageMessage(null));
        assertFalse(nearbyPhotosChatHandler.canHandleStickerMessage(null));
        assertTrue(nearbyPhotosChatHandler.canHandleLocationMessage(null));
    }
}
