package advprog.bot.feature.image;

import advprog.bot.feature.image.Image;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.stereotype.Service;

@RunWith(MockitoJUnitRunner.class)
public class ImageChatHandlerTest {

    ImageChatHandler imageChatHandler;

    @Before
    public void setUp() {
        imageChatHandler = new ImageChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEvent() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("/xkcd apa"));
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Formatnya salah kakak /xkcd [nomor id]"));
        expectedMessages.add(new TextMessage("komik tidak ada"));
        String msg = "/xkcd 20000";
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