package advprog.bot.feature.yerlandinata.quran.fetcher;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;

import java.io.IOException;

import org.json.JSONException;

public interface AyatQuranFetcher {
    AyatQuran fetchAyatQuran(int surah, int ayat)
            throws IOException, JSONException, InvalidAyatQuranException;
}
