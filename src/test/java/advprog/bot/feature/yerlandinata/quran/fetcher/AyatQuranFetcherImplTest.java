package advprog.bot.feature.yerlandinata.quran.fetcher;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AyatQuranFetcherImplTest {

    @Mock
    OkHttpClient okHttpClient;

    AyatQuranFetcherImpl ayatQuranFetcher;

    private static final String AYAT_ARAB = "{\n"
            + "    \"code\": 200,\n"
            + "    \"status\": \"OK\",\n"
            + "    \"data\": {\n"
            + "        \"number\": 1,\n"
            + "        \"audio\": "
            + "\"http://cdn.alquran.cloud/media/audio/ayah/ar.abdullahbasfar/1\",\n"
            + "        \"text\": \"بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\",\n"
            + "        \"edition\": {\n"
            + "            \"identifier\": \"quran-simple\",\n"
            + "            \"language\": \"ar\",\n"
            + "            \"name\": \"Simple\",\n"
            + "            \"englishName\": \"Simple\",\n"
            + "            \"format\": \"text\",\n"
            + "            \"type\": \"quran\"\n"
            + "        },\n"
            + "        \"surah\": {\n"
            + "            \"number\": 1,\n"
            + "            \"name\": \"سورة الفاتحة\",\n"
            + "            \"englishName\": \"Al-Faatiha\",\n"
            + "            \"englishNameTranslation\": \"The Opening\",\n"
            + "            \"numberOfAyahs\": 7,\n"
            + "            \"revelationType\": \"Meccan\"\n"
            + "        },\n"
            + "        \"numberInSurah\": 1,\n"
            + "        \"juz\": 1,\n"
            + "        \"manzil\": 1,\n"
            + "        \"page\": 1,\n"
            + "        \"ruku\": 1,\n"
            + "        \"hizbQuarter\": 1,\n"
            + "        \"sajda\": false\n"
            + "    }\n"
            + "}";

    private static final String AYAT_INDO = "{\n"
            + "    \"code\": 200,\n"
            + "    \"status\": \"OK\",\n"
            + "    \"data\": {\n"
            + "        \"number\": 1,\n"
            + "        \"text\": "
            + "\"Dengan menyebut nama Allah Yang Maha Pemurah lagi Maha Penyayang.\",\n"
            + "        \"edition\": {\n"
            + "            \"identifier\": \"id.indonesian\",\n"
            + "            \"language\": \"id\",\n"
            + "            \"name\": \"Bahasa Indonesia\",\n"
            + "            \"englishName\": \"Unknown\",\n"
            + "            \"format\": \"text\",\n"
            + "            \"type\": \"translation\"\n"
            + "        },\n"
            + "        \"surah\": {\n"
            + "            \"number\": 1,\n"
            + "            \"name\": \"سورة الفاتحة\",\n"
            + "            \"englishName\": \"Al-Faatiha\",\n"
            + "            \"englishNameTranslation\": \"The Opening\",\n"
            + "            \"numberOfAyahs\": 7,\n"
            + "            \"revelationType\": \"Meccan\"\n"
            + "        },\n"
            + "        \"numberInSurah\": 1,\n"
            + "        \"juz\": 1,\n"
            + "        \"manzil\": 1,\n"
            + "        \"page\": 1,\n"
            + "        \"ruku\": 1,\n"
            + "        \"hizbQuarter\": 1,\n"
            + "        \"sajda\": false\n"
            + "    }\n"
            + "}";

    private static final String NOT_FOUND = "{\n"
            + "    \"code\": 400,\n"
            + "    \"status\": \"Bad Request\",\n"
            + "    \"data\": \"Please specify a valid surah reference in the "
            +                   "format Surah:Ayat (2:255).\",\n"
            + "    \"audioEdition\": {}\n"
            + "}";

    @Before
    public void setUp() {
        ayatQuranFetcher = new AyatQuranFetcherImpl(okHttpClient);
    }

    @Test
    public void testFetchDataFound() throws IOException, JSONException {
        Call mockCall1 = mock(Call.class);
        Call mockCall2 = mock(Call.class);
        setFakeResponse(mockCall1, AYAT_INDO, 200);
        setFakeResponse(mockCall2, AYAT_ARAB, 200);
        when(okHttpClient.newCall(any(Request.class)))
                .thenReturn(mockCall1)
                .thenReturn(mockCall2);
        AyatQuran actualAyat = ayatQuranFetcher.fetchAyatQuran(1, 1);
        AyatQuran expectedAyat = new AyatQuran(
                "Al-Faatiha",
                1,
                "Dengan menyebut nama Allah Yang Maha Pemurah lagi Maha Penyayang.",
                "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                "https://cdn.alquran.cloud/media/audio/ayah/ar.abdullahbasfar/1",
                3
        );
        assertEquals(expectedAyat, actualAyat);
        assertEquals(expectedAyat.hashCode(), actualAyat.hashCode());
        assertEquals(expectedAyat.toString(), actualAyat.toString());
    }

    @Test(expected = InvalidAyatQuranException.class)
    public void testFetchInvalidSurah() throws IOException, JSONException {
        ayatQuranFetcher.fetchAyatQuran(115, 1);
        verify(okHttpClient, never()).newCall(any(Request.class));
    }

    @Test(expected = InvalidAyatQuranException.class)
    public void testFetchAyatNotFound() throws IOException, JSONException {
        Call mockCall1 = mock(Call.class);
        setFakeResponse(mockCall1, NOT_FOUND, 400);
        Call mockCall2 = mock(Call.class);
        setFakeResponse(mockCall2, NOT_FOUND, 400);
        when(okHttpClient.newCall(any(Request.class)))
                .thenReturn(mockCall1)
                .thenReturn(mockCall2);
        ayatQuranFetcher.fetchAyatQuran(1, 8);
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
