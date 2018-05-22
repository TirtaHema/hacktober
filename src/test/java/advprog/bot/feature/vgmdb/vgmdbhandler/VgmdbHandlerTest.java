package advprog.bot.feature.vgmdb.vgmdbhandler;

import static advprog.bot.feature.vgmdb.WebScrapper.getData;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class VgmdbHandlerTest {

    VgmdbHandler vgmdbHandler;

    @Before
    public void setUp() {
        vgmdbHandler = new VgmdbHandler(new BaseChatHandler());
    }

    @Test
    public void handleTextMessageTest() throws IOException {
        List<String> data = getData();
        String result = "";
        List<Message> expectedMessages = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i != 0 && i % 20 == 0) {
                Message text = new TextMessage(result);
                expectedMessages.add(text);
                result = "";
                result += data.get(i) + "\n\n";
            } else {
                result += data.get(i) + "\n\n";
            }
        }
        result = result.substring(0, result.length() - 2);
        Message text = new TextMessage(result);
        expectedMessages.add(text);
        MessageEvent<TextMessageContent> input = ChatHandlerTestUtil
                .fakeMessageEvent(
                        "fff", "/vgmdb ost this month");

        assertEquals(expectedMessages,
                vgmdbHandler.handleTextMessageEvent(input, expectedMessages));
    }

    @Test
    public void errorHandleTextMessageTest() {
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Keyword salah"));
        String msg = "/vgdbm ost this month";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );
        assertEquals(expectedMessages, vgmdbHandler.handleTextMessageEvent(me, expectedMessages));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(vgmdbHandler.canHandleAudioMessage(null));
        assertFalse(vgmdbHandler.canHandleImageMessage(null));
        assertFalse(vgmdbHandler.canHandleStickerMessage(null));
        assertFalse(vgmdbHandler.canHandleLocationMessage(null));
    }

}
