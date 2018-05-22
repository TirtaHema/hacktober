package advprog.bot.feature.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerChatHandlerTest {
    SchedulerChatHandler schedulerChatHandler;

    @Before
    public void setUp() {
        schedulerChatHandler = new SchedulerChatHandler(new BaseChatHandler());
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(schedulerChatHandler.canHandleAudioMessage(null));
        assertFalse(schedulerChatHandler.canHandleImageMessage(null));
        assertFalse(schedulerChatHandler.canHandleStickerMessage(null));
        assertFalse(schedulerChatHandler.canHandleLocationMessage(null));
    }

    @Test
    public void testCanHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event = ChatHandlerTestUtil
                .fakeGroupMessageEvent("group1", "user1", "/create_schedule");

        assertTrue(schedulerChatHandler.canHandleTextMessage(event));

        event = ChatHandlerTestUtil
                .fakeGroupMessageEvent("group1", "user1", "jadwal");

        assertTrue(schedulerChatHandler.canHandleTextMessage(event));
    }

    @Test
    public void testCannotHandleSomeTextMessageEvent() {
        MessageEvent<TextMessageContent> event = ChatHandlerTestUtil
                .fakeGroupMessageEvent("group1", "user1", "hehe");

        assertFalse(schedulerChatHandler.canHandleTextMessage(event));

        event = ChatHandlerTestUtil
                .fakeRoomMessageEvent("room1", "user1", "jadwal");

        assertFalse(schedulerChatHandler.canHandleTextMessage(event));

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "jadwal");

        assertFalse(schedulerChatHandler.canHandleTextMessage(event));
    }

    @Test
    public void testCanCreateSchedule() {
        MessageEvent<TextMessageContent> event = ChatHandlerTestUtil
                .fakeGroupMessageEvent("group1", "user1", "/create_schedule");
        Message reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(),
                "Pilih waktu yang diinginkan pada private chat");

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "2019-03-12");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TemplateMessage) reply).getAltText(), "carousel alt text");

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "00:00-01:00");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(), "Masukan Deskripsi");

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "Birthday");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(), "Jadwal baru telah ditambahkan!");
    }

    @Test
    public void testCanViewScheduleAndScheduleExists() {
        MessageEvent<TextMessageContent> event = ChatHandlerTestUtil
                .fakeGroupMessageEvent("group1", "user1", "/create_schedule");

        Message reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(),
                "Pilih waktu yang diinginkan pada private chat");

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "2019-03-11");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TemplateMessage) reply).getAltText(), "carousel alt text");

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "00:00-01:00");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(), "Masukan Deskripsi");

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "Deadline Adpro");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(), "Jadwal baru telah ditambahkan!");

        event = ChatHandlerTestUtil
                .fakeGroupMessageEvent("group1", "user1", "jadwal");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(), "1. Deadline Adpro\n");
    }

    @Test
    public void testCanViewScheduleAndScheduleIsNotExists() {
        MessageEvent<TextMessageContent> event = ChatHandlerTestUtil
                .fakeGroupMessageEvent("group1", "user1", "jadwal");
        Message reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(),
                "Tidak terdapat jadwal apapun untuk hari ini");
    }

    @Test
    public void testRequestNewScheduleOnUsedTime() {
        MessageEvent<TextMessageContent> event = ChatHandlerTestUtil
                .fakeGroupMessageEvent("group1", "user1", "/create_schedule");

        Message reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(),
                "Pilih waktu yang diinginkan pada private chat");

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "2019-03-12");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TemplateMessage) reply).getAltText(), "carousel alt text");

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "00:00-01:00");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(), "Masukan Deskripsi");

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "Birthday");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(), "Jadwal baru telah ditambahkan!");

        event = ChatHandlerTestUtil
                .fakeGroupMessageEvent("group1", "user1", "/create_schedule");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(),
                "Pilih waktu yang diinginkan pada private chat");

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "2019-03-12");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TemplateMessage) reply).getAltText(), "carousel alt text");

        event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "00:00-01:00");
        reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(),
                "Waktu telah dipakai untuk kegiatan lain.\n"
                        + "Silahkan pilih waktu yang belum terpakai.");
    }

    @Test
    public void testWrongFlowInPrivateChat() {
        MessageEvent<TextMessageContent> event = ChatHandlerTestUtil
                .fakePrivateMessageEvent("user1", "/create_schedule");
        Message reply = schedulerChatHandler.handleTextMessage(event).get(0);
        assertEquals(((TextMessage) reply).getText(),
                "Pastikan input Anda sesuai dengan perintah");
    }
}
