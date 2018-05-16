package advprog.bot.feature.tropicaltop10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class TropicalTopTenHandlerTest {

    TropicalTopTenHandler tropicalTopTenHandler;

    @Before
    public void setUp() {
        tropicalTopTenHandler = new TropicalTopTenHandler(new BaseChatHandler());
    }

    @Test
    public void getTopTenTest() throws IOException {
        List<Message> expectedMessages = new LinkedList<>();
        String result = tropicalTopTenHandler.getTopTen();
        expectedMessages.add(new TextMessage(result));

        MessageEvent<TextMessageContent> input = ChatHandlerTestUtil
                .fakeMessageEvent(
                        "fff", "/billboard tropical");

        assertEquals(expectedMessages,
                tropicalTopTenHandler.handleTextMessageEvent(input, expectedMessages));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(tropicalTopTenHandler.canHandleAudioMessage(null));
        assertFalse(tropicalTopTenHandler.canHandleImageMessage(null));
        assertFalse(tropicalTopTenHandler.canHandleStickerMessage(null));
        assertFalse(tropicalTopTenHandler.canHandleLocationMessage(null));
    }

}
