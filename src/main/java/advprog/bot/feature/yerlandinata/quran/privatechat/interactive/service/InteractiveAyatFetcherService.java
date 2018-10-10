package advprog.bot.feature.yerlandinata.quran.privatechat.interactive.service;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;

import java.io.IOException;

import org.json.JSONException;

public interface InteractiveAyatFetcherService {

    void recordUserSurahSelection(String userId, int surah) throws IllegalStateException;

    AyatQuran fetchAyat(String userId, int ayat)
            throws IllegalStateException, IOException, JSONException;

}
