package advprog.bot.feature.yerlandinata.quran.privatechat.noninteractive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.feature.yerlandinata.quran.AyatQuran;
import advprog.bot.feature.yerlandinata.quran.fetcher.AyatQuranFetcher;
import advprog.bot.feature.yerlandinata.quran.fetcher.InvalidAyatQuranException;
import advprog.bot.line.BaseChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
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
public class PrivateQuranChatHandlerTest {

    @Mock
    AyatQuranFetcher ayatQuranFetcher;

    PrivateQuranChatHandler privateQuranChatHandler;

    @Before
    public void setUp() {
        privateQuranChatHandler = new PrivateQuranChatHandler(
                new BaseChatHandler(), ayatQuranFetcher
        );
    }

    @Test
    public void testHandleCorrectCommandCorrectArgument() throws IOException, JSONException {
        int surah = 1;
        int ayat = 1;
        AyatQuran expectedAyat = new AyatQuran(
                "Al-Faatiha",
                ayat,
                "Dengan menyebut nama Allah Yang Maha Pemurah lagi Maha Penyayang.",
                "بِسْمِ اللَّهِ الرّ",
                "http://cdn.alquran.cloud/media/audio/ayah/ar.abdullahbasfar/1",
                3
        );
        when(ayatQuranFetcher.fetchAyatQuran(eq(surah), eq(ayat)))
                .thenReturn(expectedAyat);
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(
                String.format("%s : %d", expectedAyat.getSurahName(), ayat)
        ));
        expectedMessages.add(new TextMessage(expectedAyat.getAyatArab()));
        expectedMessages.add(new TextMessage(expectedAyat.getAyatIndonesia()));
        expectedMessages.add(
                new AudioMessage(expectedAyat.getAudioUri(), 3)
        );
        String text = String.format("/qs %d:%d", surah, ayat);
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", text, new UserSource("x")
        );
        List<Message> actualMessage = privateQuranChatHandler.handleTextMessageEvent(
                me, new LinkedList<>()
        );
        assertEquals(expectedMessages, actualMessage);
        verify(ayatQuranFetcher).fetchAyatQuran(eq(surah), eq(ayat));
    }

    @Test
    public void testNotHandleCorrectCommandCorrectArgumentInGroup()
            throws IOException, JSONException {
        String text = "/qs 1:1";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", text, new GroupSource("x", "xx")
        );
        assertEquals(Collections.emptyList(),
                privateQuranChatHandler.handleTextMessageEvent(me, new LinkedList<>()));
        verify(ayatQuranFetcher, never()).fetchAyatQuran(anyInt(), anyInt());
    }

    @Test
    public void testHandleCorrectCommandIncorrectArgument() throws IOException, JSONException {
        String text = "/qs aaa";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", text, new UserSource("x")
        );
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(PrivateQuranChatHandler.HELP_MESSAGE));
        List<Message> actualMessages = privateQuranChatHandler
                .handleTextMessageEvent(me, new LinkedList<>());
        assertEquals(expectedMessages, actualMessages);
        verify(ayatQuranFetcher, never()).fetchAyatQuran(anyInt(), anyInt());
    }

    @Test
    public void testHandleInvalidAyat() throws IOException, JSONException {
        String text = "/qs 115:1";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", text, new UserSource("x")
        );
        when(ayatQuranFetcher.fetchAyatQuran(eq(115), eq(1)))
                .thenThrow(new InvalidAyatQuranException(InvalidAyatQuranException.INVALID_SURAH));
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(InvalidAyatQuranException.INVALID_SURAH));
        List<Message> actualMessages = privateQuranChatHandler
                .handleTextMessageEvent(me, new LinkedList<>());
        assertEquals(expectedMessages, actualMessages);
        verify(ayatQuranFetcher).fetchAyatQuran(eq(115), eq(1));
    }

    @Test
    public void testHandleFetcherFail()  throws IOException, JSONException {
        String text = "/qs 114:1";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", text, new UserSource("x")
        );
        when(ayatQuranFetcher.fetchAyatQuran(eq(114), eq(1)))
                .thenThrow(new IOException());
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage(PrivateQuranChatHandler.FETCHER_FAIL));
        List<Message> actualMessages = privateQuranChatHandler
                .handleTextMessageEvent(me, new LinkedList<>());
        assertEquals(expectedMessages, actualMessages);
        verify(ayatQuranFetcher).fetchAyatQuran(eq(114), eq(1));
    }

    @Test
    public void testNotHandleIncorrectCommand() throws IOException, JSONException {
        String text = "/youtube asdf";
        MessageEvent<TextMessageContent>  me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", text
        );
        List<Message> expectedMessages = Collections.singletonList(
                new TextMessage("Invalud Youtube Video URL")
        );
        List<Message> actualMessages = privateQuranChatHandler.handleTextMessageEvent(
                me, expectedMessages
        );
        assertEquals(expectedMessages, actualMessages);
        verify(ayatQuranFetcher, never()).fetchAyatQuran(anyInt(), anyInt());
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(privateQuranChatHandler.canHandleAudioMessage(null));
        assertFalse(privateQuranChatHandler.canHandleImageMessage(null));
        assertFalse(privateQuranChatHandler.canHandleStickerMessage(null));
        assertFalse(privateQuranChatHandler.canHandleLocationMessage(null));
    }

}
