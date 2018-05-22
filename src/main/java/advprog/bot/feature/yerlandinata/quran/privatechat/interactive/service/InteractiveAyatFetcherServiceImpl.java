package advprog.bot.feature.yerlandinata.quran.privatechat.interactive.service;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;
import advprog.bot.feature.yerlandinata.quran.fetcher.AyatQuranFetcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

public class InteractiveAyatFetcherServiceImpl implements InteractiveAyatFetcherService {

    private final AyatQuranFetcher ayatQuranFetcher;
    private final Map<String, Integer> userInteraction;

    public InteractiveAyatFetcherServiceImpl(AyatQuranFetcher ayatQuranFetcher) {
        this.ayatQuranFetcher = ayatQuranFetcher;
        this.userInteraction = new HashMap<>();
    }

    @Override
    public void recordUserSurahSelection(String userId, int surah) throws IllegalStateException {
        if (userInteraction.containsKey(userId)) {
            if (userInteraction.get(userId) != 0) {
                throw new IllegalStateException("User selected surah twice");
            }
        }
        userInteraction.put(userId, surah);
    }

    @Override
    public AyatQuran fetchAyat(String userId, int ayat) throws IllegalStateException, IOException, JSONException {
        if (!userInteraction.containsKey(userId) || userInteraction.get(userId) == 0) {
            throw new IllegalStateException("User haven't selected surah");
        }
        return ayatQuranFetcher.fetchAyatQuran(
                userInteraction.get(userId), ayat
        );
    }
}
