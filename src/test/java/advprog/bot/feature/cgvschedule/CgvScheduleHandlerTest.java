package advprog.bot.feature.cgvschedule;

import advprog.bot.line.LineChatHandler;
import advprog.example.bot.EventTestUtil;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class CgvScheduleHandlerTest {

    private CgvScheduleHandler handler;

    @Before
    public void setUp() {
        handler = new CgvScheduleHandler(mock(LineChatHandler.class));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(handler.canHandleAudioMessage(null));
        assertFalse(handler.canHandleImageMessage(null));
        assertFalse(handler.canHandleStickerMessage(null));
        assertFalse(handler.canHandleLocationMessage(null));
    }

    @Test
    public void testCanHandleTextMessageEvent() {
        assertTrue(handler.canHandleTextMessage(null));
    }

    @Test
    public void testCgvGoldClass() {
        MessageEvent<TextMessageContent> messageEvent =
                EventTestUtil.createDummyTextMessage("/cgv_gold_class");
        List<Message> reply = handler.handleTextMessage(messageEvent);
        assertNotNull(reply);
    }

    @Test
    public void testCgvRegular2D() {
        MessageEvent<TextMessageContent> messageEvent =
                EventTestUtil.createDummyTextMessage("/cgv_regular_2d");
        List<Message> reply = handler.handleTextMessage(messageEvent);
        assertNotNull(reply);
    }

    @Test
    public void testCgv4dx3DCinema() {
        MessageEvent<TextMessageContent> messageEvent =
                EventTestUtil.createDummyTextMessage("/cgv_4dx_3d_cinema");
        List<Message> reply = handler.handleTextMessage(messageEvent);
        assertNotNull(reply);
    }

    @Test
    public void testCgvVelvet() {
        MessageEvent<TextMessageContent> messageEvent =
                EventTestUtil.createDummyTextMessage("/cgv_velvet");
        List<Message> reply = handler.handleTextMessage(messageEvent);
        assertNotNull(reply);
    }

    @Test
    public void testCgvSweetBox() {
        MessageEvent<TextMessageContent> messageEvent =
                EventTestUtil.createDummyTextMessage("/cgv_sweet_box");
        List<Message> reply = handler.handleTextMessage(messageEvent);
        assertNotNull(reply);
    }

    @Test
    public void testCgvChangeCinema() {
        MessageEvent<TextMessageContent> messageEvent;
        List<Message> reply;

        messageEvent = EventTestUtil.createDummyTextMessage(
                "/cgv_change_cinema https://www.cgv.id/en/schedule/cinema/002");
        reply = handler.handleTextMessage(messageEvent);

        assertNotNull(reply);

        messageEvent = EventTestUtil.createDummyTextMessage(
                "/cgv_change_cinema https://www.cgv.id/en/schedule/cinema/200");
        reply = handler.handleTextMessage(messageEvent);

        assertNotNull(reply);
    }
}
