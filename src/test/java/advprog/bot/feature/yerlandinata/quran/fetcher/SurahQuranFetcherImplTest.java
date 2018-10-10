package advprog.bot.feature.yerlandinata.quran.fetcher;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import advprog.bot.feature.yerlandinata.quran.SurahQuran;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SurahQuranFetcherImplTest {

    private static final String SURAH_RESPONSE = "{\n"
            + "    \"code\": 200,\n"
            + "    \"status\": \"OK\",\n"
            + "    \"data\": [\n"
            + "        {\n"
            + "            \"number\": 1,\n"
            + "            \"name\": \"سورة الفاتحة\",\n"
            + "            \"englishName\": \"Al-Faatiha\",\n"
            + "            \"englishNameTranslation\": \"The Opening\",\n"
            + "            \"numberOfAyahs\": 7,\n"
            + "            \"revelationType\": \"Meccan\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"number\": 2,\n"
            + "            \"name\": \"سورة البقرة\",\n"
            + "            \"englishName\": \"Al-Baqara\",\n"
            + "            \"englishNameTranslation\": \"The Cow\",\n"
            + "            \"numberOfAyahs\": 286,\n"
            + "            \"revelationType\": \"Medinan\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"number\": 3,\n"
            + "            \"name\": \"سورة آل عمران\",\n"
            + "            \"englishName\": \"Aal-i-Imraan\",\n"
            + "            \"englishNameTranslation\": \"The Family of Imraan\",\n"
            + "            \"numberOfAyahs\": 200,\n"
            + "            \"revelationType\": \"Medinan\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"number\": 4,\n"
            + "            \"name\": \"سورة النساء\",\n"
            + "            \"englishName\": \"An-Nisaa\",\n"
            + "            \"englishNameTranslation\": \"The Women\",\n"
            + "            \"numberOfAyahs\": 176,\n"
            + "            \"revelationType\": \"Medinan\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"number\": 5,\n"
            + "            \"name\": \"سورة المائدة\",\n"
            + "            \"englishName\": \"Al-Maaida\",\n"
            + "            \"englishNameTranslation\": \"The Table\",\n"
            + "            \"numberOfAyahs\": 120,\n"
            + "            \"revelationType\": \"Medinan\"\n"
            + "        }\n"
            + "    ]\n"
            + "}\n";

    private static final String SURAH_RESPONSE_FAIL = "{\n"
            + "    \"code\": 500,\n"
            + "    \"status\": \"OK\",\n"
            + "    \"data\": [\n"
            + "\n"
            + "    ]\n"
            + "}\n";

    @Mock
    OkHttpClient httpClient;

    SurahQuranFetcherImpl surahQuranFetcher;

    @Before
    public void setUp() {
        surahQuranFetcher = new SurahQuranFetcherImpl(httpClient);
    }

    @Test
    public void testFetchSurah() throws IOException, JSONException {
        Call mockCall = mock(Call.class);
        setFakeResponse(mockCall, SURAH_RESPONSE, 200);
        when(httpClient.newCall(any(Request.class)))
                .thenReturn(mockCall);
        List<SurahQuran> expectedSurahs = Arrays.asList(
                new SurahQuran(1, 7, "سورة الفاتحة", "Al-Faatiha"),
                new SurahQuran(2, 286, "سورة البقرة", "Al-Baqara"),
                new SurahQuran(3, 200, "سورة آل عمران", "Aal-i-Imraan"),
                new SurahQuran(4, 176, "سورة النساء", "An-Nisaa"),
                new SurahQuran(5, 120, "سورة المائدة", "Al-Maaida")
        );
        List<SurahQuran> actualSurahs = surahQuranFetcher.fetchSurahQuran();
        assertEquals(expectedSurahs, actualSurahs);
        assertEquals(expectedSurahs.get(0).hashCode(), actualSurahs.get(0).hashCode());
        assertEquals(expectedSurahs.get(0).toString(), actualSurahs.get(0).toString());
    }

    @Test(expected = IllegalStateException.class)
    public void testHandleFailFetchSurah() throws IOException, JSONException {
        Call mockCall = mock(Call.class);
        setFakeResponse(mockCall, SURAH_RESPONSE_FAIL, 500);
        when(httpClient.newCall(any(Request.class)))
                .thenReturn(mockCall);
        surahQuranFetcher.fetchSurahQuran();
    }

    private void setFakeResponse(Call mockCall, String responseStr, int code) throws IOException {
        ResponseBody mockRb = ResponseBody.create(MediaType.parse("text"), responseStr);
        Request request = new Request.Builder()
                .url("http://example.com")
                .build();
        Response response = new Response.Builder()
                .body(mockRb)
                .code(code)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("asd")
                .build();
        when(mockCall.execute()).thenReturn(response);
    }

}
