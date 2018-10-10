package advprog.bot.feature.yerlandinata.quran.fetcher;

import advprog.bot.feature.yerlandinata.quran.SurahQuran;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

public interface SurahQuranFetcher {
    List<SurahQuran> fetchSurahQuran()
            throws IOException, JSONException;
}
