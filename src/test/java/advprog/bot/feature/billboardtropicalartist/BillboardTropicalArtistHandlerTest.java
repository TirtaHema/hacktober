package advprog.bot.feature.billboardtropicalartist;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class BillboardTropicalArtistHandlerTest {

    BillboardTropicalArtistHandler billboardTropicalArtistHandler;

    @Before
    public void setUp() {
        billboardTropicalArtistHandler = new BillboardTropicalArtistHandler
                (new BaseChatHandler());
    }

    @Test
    public void artistNotFound() {
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Peruzzi is not present "
                + "in Billboard's Tropical Songs music chart this week"));

        MessageEvent<TextMessageContent> input = ChatHandlerTestUtil
                .fakeMessageEvent(
                        "fff", "/billboard tropical Peruzzi");

        assertEquals(expectedMessages,
                billboardTropicalArtistHandler.handleTextMessageEvent(input, expectedMessages));
    }

    @Test
    public void artistFound() throws IOException {
        List<Message> expectedMessages = new LinkedList<>();
        String result = billboardTropicalArtistHandler.cekArtist("Fonseca");
        expectedMessages.add(new TextMessage(result));

        MessageEvent<TextMessageContent> input = ChatHandlerTestUtil
                .fakeMessageEvent(
                        "fff", "/billboard tropical Fonseca");

        assertEquals(expectedMessages,
                billboardTropicalArtistHandler.handleTextMessageEvent(input, expectedMessages));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(billboardTropicalArtistHandler.canHandleAudioMessage(null));
        assertFalse(billboardTropicalArtistHandler.canHandleImageMessage(null));
        assertFalse(billboardTropicalArtistHandler.canHandleStickerMessage(null));
        assertFalse(billboardTropicalArtistHandler.canHandleLocationMessage(null));
    }

}
