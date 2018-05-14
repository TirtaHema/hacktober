package advprog.bot.feature.fakejson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import java.util.LinkedList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class FakeJsonChatHandlerTest {
    FakeJsonChatHandler fakeJsonChatHandler;

    @Before
    public void setUp() {
        fakeJsonChatHandler = new FakeJsonChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEvent() {
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("100%"));
        String msg = "/fake_json";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        fakeJsonChatHandler.handleTextMessageEvent(me, messages);
        assertEquals(1, messages.size());

        TextMessage reply = (TextMessage) messages.get(0);
        Assertions.assertThatCode(() -> (new JSONObject(reply.getText())).get("id"))
                .doesNotThrowAnyException();

    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(fakeJsonChatHandler.canHandleAudioMessage(null));
        assertFalse(fakeJsonChatHandler.canHandleImageMessage(null));
        assertFalse(fakeJsonChatHandler.canHandleStickerMessage(null));
        assertFalse(fakeJsonChatHandler.canHandleLocationMessage(null));
    }
}
