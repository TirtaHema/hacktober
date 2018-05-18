package advprog.bot.feature.yerlandinata.quran.privatechat.interactive.service;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;

public interface InteractiveAyatFetcherService {

    AyatQuran fetchAyat(String userId, int ayat) throws IllegalStateException;

}
