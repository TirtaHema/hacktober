package advprog.bot.feature.yerlandinata.quran.fetcher;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;
import advprog.bot.feature.yerlandinata.quran.InvalidAyatQuranException;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class AyatQuranFetcherImpl implements AyatQuranFetcher {

    private static final String BASE_RESOURCE_URI = "https://api.alquran.cloud/ayah";
    private static final String INDO_EDITION = "id.indonesian";
    private static final String ARAB_EDITION = "ar.abdullahbasfar";

    private final OkHttpClient httpClient;

    public AyatQuranFetcherImpl(OkHttpClient okHttpClient) {
        this.httpClient = okHttpClient;
    }

    @Override
    public AyatQuran fetchAyatQuran(int surah, int ayat)
            throws IOException, JSONException, InvalidAyatQuranException {
        if (!isSurahValid(surah)) {
            throw new InvalidAyatQuranException(InvalidAyatQuranException.INVALID_SURAH);
        }
        return parseAyatQuran(
                fetchAyatQuran(surah, ayat, INDO_EDITION),
                fetchAyatQuran(surah, ayat, ARAB_EDITION),
                surah,
                ayat
        );
    }

    private String fetchAyatQuran(int surah, int ayat, String edition) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_RESOURCE_URI).newBuilder();
        urlBuilder.addPathSegment(String.format("%d:%d", surah, ayat));
        urlBuilder.addPathSegment(edition);
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    private boolean isSurahValid(int surah) {
        return surah >= 1 && surah <= 114;
    }

    private AyatQuran parseAyatQuran(String jsonIndo, String jsonArab, int surah, int ayat)
            throws JSONException {
        JSONObject indo = new JSONObject(jsonIndo);
        JSONObject arab = new JSONObject(jsonArab);
        if (indo.getInt("code") != 200 || arab.getInt("code") != 200) {
            throw new InvalidAyatQuranException(
                    String.format(
                            "Tidak ada ayat Quran surah %d:%d ; Insya Allah program akurat",
                            surah,
                            ayat
                    )
            );
        }
        indo = indo.getJSONObject("data");
        arab = arab.getJSONObject("data");
        String surahName = arab.getJSONObject("surah").getString("englishName");
        String ayatIndo = indo.getString("text");
        String ayatArab = arab.getString("text");
        String audioUri = arab.getString("audio");
        return new AyatQuran(
                surahName,
                ayat,
                ayatIndo,
                ayatArab,
                audioUri,
                3
        );
    }

}
