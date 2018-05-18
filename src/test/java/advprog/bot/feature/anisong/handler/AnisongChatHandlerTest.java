package advprog.bot.feature.anisong.handler;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {AnisongChatHandler.class})
public class AnisongChatHandlerTest {

    AnisongChatHandler handler = new AnisongChatHandler(
            new BaseChatHandler());

    private String url = "https://audio-ssl.itunes.apple.com/apple-assets-us-std-000001"
            + "/AudioPreview20/v4/cc/0a/90/cc0a907f-46b8-32b2-1bb7-d35786490f57/mzaf_63"
            + "82086061014043981.plus.aac.p.m4a";

    private String invalidMessage = "Your Input is not valid";

    @Test
    public void testReply() {
        List<Message> expectedMessage = new LinkedList<>();
        expectedMessage.add(new AudioMessage(url,29000));

        MessageEvent<AudioMessageContent> input = ChatHandlerTestUtil
                .fakeAudioEvent("ff","/listen_song torikoriko please");

        assertEquals(expectedMessage,
                handler.handleAudioMessageEvent(input, expectedMessage));
    }

    @Test
    public void testInvalidMessage() {
        List<Message> expectedMessage = new LinkedList<>();
        expectedMessage.add(new TextMessage(invalidMessage));

        MessageEvent<TextMessageContent> input = ChatHandlerTestUtil
                .fakeMessageEvent("sda", "sdad");

        assertEquals(expectedMessage,
                handler.handleTextMessageEvent(input, expectedMessage));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(handler.canHandleAudioMessage(null));
        assertFalse(handler.canHandleImageMessage(null));
        assertFalse(handler.canHandleStickerMessage(null));
        assertFalse(handler.canHandleLocationMessage(null));
    }
}
