package advprog.bot.feature.yerlandinata.quran.privatechat.interactive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.feature.yerlandinata.quran.SurahQuran;
import advprog.bot.feature.yerlandinata.quran.fetcher.SurahQuranFetcher;
import advprog.bot.feature.yerlandinata.quran.privatechat.interactive.service.InteractiveAyatFetcherService;
import advprog.bot.line.BaseChatHandler;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
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
        List<SurahQuran> expectedSurah = IntStream.range(0, 15)
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
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(quranChatHandler.canHandleAudioMessage(null));
        assertFalse(quranChatHandler.canHandleImageMessage(null));
        assertFalse(quranChatHandler.canHandleStickerMessage(null));
        assertFalse(quranChatHandler.canHandleLocationMessage(null));
    }

}
