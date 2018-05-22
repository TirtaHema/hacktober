package advprog.bot.feature.yerlandinata.quran.privatechat.interactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.feature.yerlandinata.quran.AyatQuran;
import advprog.bot.feature.yerlandinata.quran.SurahQuran;
import advprog.bot.feature.yerlandinata.quran.fetcher.SurahQuranFetcher;
import advprog.bot.feature.yerlandinata.quran.privatechat.interactive.service.InteractiveAyatFetcherService;
import advprog.bot.line.BaseChatHandler;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InteractivePrivateQuranChatHandlerTest {

    @Mock
    SurahQuranFetcher surahQuranFetcher;

    @Mock
    InteractiveAyatFetcherService interactiveAyatFetcherService;

    InteractivePrivateQuranChatHandler quranChatHandler;

    @Before
    public void setUp() {
        quranChatHandler = new InteractivePrivateQuranChatHandler(
                new BaseChatHandler(), surahQuranFetcher, interactiveAyatFetcherService
        );
    }

    @Test
    public void testShowCarousel10Surah() throws IOException, JSONException {
        List<SurahQuran> expectedSurah =
                IntStream.range(0, 15)
                            .mapToObj(i -> new SurahQuran(i, i, "" + i, "title" + i))
                            .collect(Collectors.toList());

        when(surahQuranFetcher.fetchSurahQuran())
                .thenReturn(expectedSurah);
        CarouselTemplate expectedCarousel = new CarouselTemplate(
                expectedSurah
                        .stream()
                        .limit(10)
                        .map(s -> new CarouselColumn(
                                InteractivePrivateQuranChatHandler.QURAN_IMAGE,
                                s.getEnglishName(),
                                s.getArabName(),
                                Collections.singletonList(
                                        new MessageAction(
                                                "Pilih", "/qsi " + s.getSurahNumber()
                                        )
                                )
                        )).collect(Collectors.toList())
        );
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", "/qs", new UserSource("x")
        );
        List<Message> expectedMessage = Collections.singletonList(
            new TemplateMessage("Quran", expectedCarousel)
        );
        List<Message> actualMessage = quranChatHandler.handleTextMessageEvent(
                me, new LinkedList<>()
        );
        assertEquals(expectedMessage, actualMessage);
        verify(surahQuranFetcher).fetchSurahQuran();
    }

    @Test
    public void testRecordUserSurahSelection() throws IOException, JSONException {
        int surah = 7;
        String userId = "userid";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", "/qsi " + surah, new UserSource(userId)
        );
        List<SurahQuran> surahQurans = IntStream.range(0, 114)
                .mapToObj(i -> new SurahQuran(i, i, "" + i, "title" + i))
                .collect(Collectors.toList());
        String surahArabName = "arab";
        String surahEnglishName = "al araf";
        int numOfAyat = 206;
        surahQurans.set(6, new SurahQuran(7, numOfAyat, surahArabName, surahEnglishName));
        when(surahQuranFetcher.fetchSurahQuran()).thenReturn(surahQurans);
        List<Message> expectedMessage = Collections.singletonList(
                new TextMessage(String.format(
                        "Surah %s terdiri dari %d ayat, ayat keberapa yang Antum ingin lihat?",
                        surahEnglishName, numOfAyat
                ))
        );
        List<Message> actualMessage = quranChatHandler.handleTextMessageEvent(
                me, new LinkedList<>()
        );
        assertEquals(expectedMessage, actualMessage);
        verify(surahQuranFetcher).fetchSurahQuran();
        verify(interactiveAyatFetcherService).recordUserSurahSelection(eq(userId), eq(surah));
    }

    @Test
    public void testShowAyatQuranCorrectly() throws IOException, JSONException {
        int surah = 7;
        String userId = "id";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", "3", new UserSource(userId)
        );
        AyatQuran expectedAyat = new AyatQuran(
                "araaf", 206, "indo", "ar", "as", 3
            );
        when(interactiveAyatFetcherService.fetchAyat(eq(userId), eq(3)))
                .thenReturn(expectedAyat);
        List<Message> expectedMessage = Arrays.asList(
                new TextMessage(
                        String.format("%s : %d", expectedAyat.getSurahName(), 3)
                ),
                new TextMessage(expectedAyat.getAyatArab()),
                new TextMessage(expectedAyat.getAyatIndonesia()),
                new AudioMessage(expectedAyat.getAudioUri(), expectedAyat.getAudioDuration())
        );
        List<Message> actualMessage = quranChatHandler.handleTextMessageEvent(
                me, new LinkedList<>()
        );
        assertEquals(expectedMessage, actualMessage);
        verify(interactiveAyatFetcherService).fetchAyat(eq(userId), eq(3));
    }

    @Test
    public void testHandleShowAyatQuranIllegaly() throws IOException, JSONException {
        when(interactiveAyatFetcherService.fetchAyat(anyString(), anyInt()))
                .thenThrow(new IllegalStateException());
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", "3", new UserSource("x")
        );
        assertEquals(
                Collections.emptyList(),
                quranChatHandler.handleTextMessageEvent(me, new LinkedList<>())
        );
    }

    @Test
    public void testHandleFailFetchAyat() throws IOException, JSONException {
        when(interactiveAyatFetcherService.fetchAyat(anyString(), anyInt()))
                .thenThrow(new JSONException(""));
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", "3", new UserSource("x")
        );
        List<Message> expectedMessage = Collections.singletonList(
                new TextMessage(InteractivePrivateQuranChatHandler.SERVICE_DOWN)
        );
        List<Message> actualMessage = quranChatHandler
                .handleTextMessageEvent(me, new LinkedList<>());
        assertEquals(expectedMessage, actualMessage);
        verify(interactiveAyatFetcherService).fetchAyat("x", 3);
    }

    @Test
    public void testNotRecordUserSurahSelectionIllegaly() throws IOException, JSONException {
        String userId = "id";
        int surah = 2;
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", "/qsi " + surah, new UserSource(userId)
        );
        doThrow(new IllegalStateException())
                .when(interactiveAyatFetcherService)
                .recordUserSurahSelection(anyString(), anyInt());
        assertEquals(
                Collections.emptyList(),
                quranChatHandler.handleTextMessageEvent(me, new LinkedList<>())
        );
        verify(surahQuranFetcher, never()).fetchSurahQuran();
    }

    @Test
    public void testNotHandleNonPrivateChat() {
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "re", "/qsi 3", new GroupSource("x", "a")
        );
        assertEquals(
            Collections.emptyList(),
            quranChatHandler.handleTextMessageEvent(me, new LinkedList<>())
        );
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(quranChatHandler.canHandleAudioMessage(null));
        assertFalse(quranChatHandler.canHandleImageMessage(null));
        assertFalse(quranChatHandler.canHandleStickerMessage(null));
        assertFalse(quranChatHandler.canHandleLocationMessage(null));
    }

}
