package advprog.bot.feature.yerlandinata.quran.privatechat.interactive.service;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;

public interface InteractiveAyatFetcherService {

    void recordUserSurahSelection(String userId, int surah);

    AyatQuran fetchAyat(String userId, int ayat) throws IllegalStateException;

}
