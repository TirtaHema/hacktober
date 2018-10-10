package advprog.bot.feature.yerlandinata.quran.groupchat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.feature.yerlandinata.quran.AyatQuran;
import advprog.bot.feature.yerlandinata.quran.SurahQuran;
import advprog.bot.feature.yerlandinata.quran.groupchat.service.GuessSurahService;
import advprog.bot.line.BaseChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QuranGroupChatHandlerTest {

    @Mock
    GuessSurahService guessSurahService;

    QuranGroupChatHandler quranChatHandler;

    @Before
    public void setUp() {
        quranChatHandler = new QuranGroupChatHandler(
                new BaseChatHandler(), guessSurahService
        );
    }

    @Test
    public void testIgnoreNonGroupChat() throws IOException, JSONException {
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", "ngaji", new UserSource("x")
        );
        List<Message> reply = quranChatHandler.handleTextMessageEvent(me, new LinkedList<>());
        assertEquals(Collections.emptyList(), reply);
        verify(guessSurahService, never()).startGuessing(anyString());
        verify(guessSurahService, never()).guess(anyString(), anyString());
        verify(guessSurahService, never()).isGuessing(anyString());
    }

    @Test
    public void testBeginGuess() throws IOException, JSONException {
        String groupId = "x";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", "ngaji", new GroupSource(groupId, "d")
        );
        when(guessSurahService.isGuessing(eq(groupId)))
                .thenReturn(false);
        AyatQuran ayatQuran = new AyatQuran("alfatiha", 1, "a", "a", "e", 3);
        when(guessSurahService.startGuessing(eq(groupId)))
                .thenReturn(ayatQuran);
        List<Message> expected = Arrays.asList(
                new TextMessage(ayatQuran.getAyatArab()),
                new TextMessage(ayatQuran.getAyatIndonesia())
        );
        List<Message> actual = quranChatHandler.handleTextMessageEvent(me, new LinkedList<>());
        assertEquals(expected, actual);
        verify(guessSurahService).startGuessing(eq(groupId));
    }

    @Test
    public void testCorrectGuess() {
        when(guessSurahService.isGuessing(anyString())).thenReturn(true);
        when(guessSurahService.guess(anyString(), anyString())).thenReturn(true);
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", "ngaji", new GroupSource("sd", "d")
        );
        List<Message> expected = Collections.singletonList(
                new TextMessage(QuranGroupChatHandler.BENAR)
        );
        List<Message> actual = quranChatHandler.handleTextMessageEvent(me, new LinkedList<>());
        assertEquals(expected, actual);
        verify(guessSurahService).isGuessing(anyString());
        verify(guessSurahService).guess(anyString(), anyString());
    }

    @Test
    public void testInCorrectGuess() {
        when(guessSurahService.isGuessing(anyString())).thenReturn(true);
        when(guessSurahService.guess(anyString(), anyString())).thenReturn(false);
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", "ngaji", new GroupSource("sd", "d")
        );
        List<Message> expected = Collections.singletonList(
                new TextMessage(QuranGroupChatHandler.SALAH)
        );
        List<Message> actual = quranChatHandler.handleTextMessageEvent(me, new LinkedList<>());
        assertEquals(expected, actual);
        verify(guessSurahService).isGuessing(anyString());
        verify(guessSurahService).guess(anyString(), anyString());
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(quranChatHandler.canHandleAudioMessage(null));
        assertFalse(quranChatHandler.canHandleImageMessage(null));
        assertFalse(quranChatHandler.canHandleStickerMessage(null));
        assertFalse(quranChatHandler.canHandleLocationMessage(null));
    }

}
