package advprog.bot.feature.yerlandinata.quran.privatechat.interactive.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;
import advprog.bot.feature.yerlandinata.quran.fetcher.AyatQuranFetcher;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InteractiveAyatFetcherImplTest {

    @Mock
    AyatQuranFetcher ayatQuranFetcher;

    InteractiveAyatFetcherServiceImpl interactiveAyatFetcherService;

    @Before
    public void setUp() {
        interactiveAyatFetcherService = new InteractiveAyatFetcherServiceImpl(ayatQuranFetcher);
    }

    @Test
    public void testGetCorrectSurah() throws IOException, JSONException {
        int surah = 7;
        int ayat = 10;
        AyatQuran expectedAyatQuran = new AyatQuran(
                "araf", ayat, "indo", "ar", "https", 3
        );
        when(ayatQuranFetcher.fetchAyatQuran(eq(surah), eq(ayat)))
                .thenReturn(expectedAyatQuran);
        String userId1 = "id";
        String userId2 = "id2";
        interactiveAyatFetcherService.recordUserSurahSelection(userId1, surah);
        interactiveAyatFetcherService.recordUserSurahSelection(userId2, 99);
        AyatQuran actualAyatQuran = interactiveAyatFetcherService.fetchAyat(userId1, ayat);
        assertEquals(expectedAyatQuran, actualAyatQuran);
        verify(ayatQuranFetcher).fetchAyatQuran(surah, ayat);
    }

    @Test(expected = IllegalStateException.class)
    public void testRejectRecordWrongTime() {
        interactiveAyatFetcherService.recordUserSurahSelection("a", 1);
        interactiveAyatFetcherService.recordUserSurahSelection("a", 2);
    }

    @Test(expected = IllegalStateException.class)
    public void testRejectGetAyatWithoutChoosingSurah() throws IOException, JSONException {
        interactiveAyatFetcherService.fetchAyat("3", 3);
    }

}
