package advprog.bot.feature.hangoutplace;

/**
 * Created by fazasaffanah on 22/05/2018.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
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

@RunWith(MockitoJUnitRunner.class)
public class HangoutPlaceChatHandlerTest {

    HangoutPlaceChatHandler hangoutPlaceChatHandler;

    @Before
    public void setUp() {
        hangoutPlaceChatHandler = new HangoutPlaceChatHandler(new BaseChatHandler());
    }

    @Test
    public void testCanHandleTextMessage() {
        MessageEvent<TextMessageContent> me =
                ChatHandlerTestUtil.fakeMessageEvent("dsf", "/hangout_kuy");
        MessageEvent<TextMessageContent> me2 =
                ChatHandlerTestUtil.fakeMessageEvent("dsf", "/random_hangout_kuy");
        MessageEvent<TextMessageContent> me3 =
                ChatHandlerTestUtil.fakeMessageEvent("dsf", "/nearby_hangout_kuy 9");
        assertTrue(hangoutPlaceChatHandler.canHandleTextMessage(me));
        assertTrue(hangoutPlaceChatHandler.canHandleTextMessage(me2));
        assertTrue(hangoutPlaceChatHandler.canHandleTextMessage(me3));
        MessageEvent<TextMessageContent> fal =
                ChatHandlerTestUtil.fakeMessageEvent("dsf", "/hangout");
        assertFalse(hangoutPlaceChatHandler.canHandleTextMessage(fal));
    }

    @Test
    public void testHandleTextMessage() {
        MessageEvent<TextMessageContent> me =
                ChatHandlerTestUtil.fakeMessageEvent("dsf", "/hangout_kuy");
        List<Message> ret = hangoutPlaceChatHandler.handleTextMessage(me);
        assertEquals(ret, Collections.singletonList(new TextMessage("Silahkan kirim lokasi Anda")));
        me = ChatHandlerTestUtil.fakeMessageEvent("dsf", "/random_hangout_kuy");
        ret = hangoutPlaceChatHandler.handleTextMessage(me);
        assertEquals(ret, Collections.singletonList(new TextMessage("Silahkan kirim lokasi Anda")));
        me = ChatHandlerTestUtil.fakeMessageEvent("dsf", "/nearby_hangout_kuy 9");
        ret = hangoutPlaceChatHandler.handleTextMessage(me);
        assertEquals(ret, Collections.singletonList(new TextMessage("Silahkan kirim lokasi Anda")));

    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(hangoutPlaceChatHandler.canHandleAudioMessage(null));
        assertFalse(hangoutPlaceChatHandler.canHandleImageMessage(null));
        assertFalse(hangoutPlaceChatHandler.canHandleStickerMessage(null));
    }

}
