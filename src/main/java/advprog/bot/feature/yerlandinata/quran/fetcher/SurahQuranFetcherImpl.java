package advprog.bot.feature.yerlandinata.quran.fetcher;

import advprog.bot.feature.yerlandinata.quran.SurahQuran;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SurahQuranFetcherImpl implements SurahQuranFetcher {

    private static final String RESOURCE_URI = "http://api.alquran.cloud/surah";
    private final OkHttpClient httpClient;

    public SurahQuranFetcherImpl(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<SurahQuran> fetchSurahQuran() throws IOException, JSONException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(RESOURCE_URI).newBuilder();
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();
        Response response = httpClient.newCall(request).execute();
        return parseSurahQuran(response.body().string());
    }

    private List<SurahQuran> parseSurahQuran(String surahJson) throws JSONException {
        JSONObject surah = new JSONObject(surahJson);
        if (surah.getInt("code") != 200) {
            throw new IllegalStateException("Quran API not available");
        }
        JSONArray surahs = surah.getJSONArray("data");
        return IntStream.range(0, surahs.length())
                .mapToObj(i -> {
                    try {
                        return surahs.getJSONObject(i);
                    } catch (JSONException e) {
                        return null;
                    }
                })
                .map(s -> {
                    try {
                        return new SurahQuran(
                                s.getInt("number"),
                                s.getInt("numberOfAyahs"),
                                s.getString("name"),
                                s.getString("englishName")
                        );
                    } catch (JSONException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }
}
