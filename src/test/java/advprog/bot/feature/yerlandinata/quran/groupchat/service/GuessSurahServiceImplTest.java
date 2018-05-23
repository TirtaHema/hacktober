package advprog.bot.feature.yerlandinata.quran.groupchat.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
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
public class GuessSurahServiceImplTest {

    @Mock
    AyatQuranFetcher ayatQuranFetcher;

    GuessSurahServiceImpl guessSurahService;

    @Before
    public void setUp() {
        guessSurahService = new GuessSurahServiceImpl(ayatQuranFetcher);
    }

    @Test
    public void testStartedGuess_IsGuessingReturnTrue()
            throws IOException, JSONException {
        String groupId = "id";
        when(ayatQuranFetcher.fetchAyatQuran(anyInt(), anyInt()))
                .thenReturn(new AyatQuran("a", 7,"i", "ar", "https", 3));
        guessSurahService.startGuessing(groupId);
        assertTrue(guessSurahService.isGuessing(groupId));
        verify(ayatQuranFetcher).fetchAyatQuran(anyInt(), anyInt());
    }

    @Test
    public void testAfterGuess_IsGuessingREturnFalse()
            throws IOException, JSONException {
        String groupId = "id";
        when(ayatQuranFetcher.fetchAyatQuran(anyInt(), anyInt()))
                .thenReturn(new AyatQuran("a", 7,"i", "ar", "https", 3));
        guessSurahService.startGuessing(groupId);
        guessSurahService.guess(groupId, "d");
        assertFalse(guessSurahService.isGuessing(groupId));
        verify(ayatQuranFetcher).fetchAyatQuran(anyInt(), anyInt());
    }


}
