package yerlandinata.youtubeinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = YoutubeInfoConfigurationTest.class)
public class YoutubeInfoFetcherTest {

    @Autowired
    OkHttpClient okHttpClient;

    @Autowired
    YoutubeInfoFetcher youtubeInfoFetcher;

    private static final String SAMPLE_VIDEO_ID = "Il-an3K9pjg";

    private static final String VIDEO_CORRECT_RESPONSE = "{\n"
            + "    \"items\": [\n"
            + "        {\n"
            + "            \"id\": \"Il-an3K9pjg\",\n"
            + "            \"snippet\": {\n"
            + "                \"publishedAt\": \"2018-05-08T11:05:08.000Z\",\n"
            + "                \"channelId\": \"UCBL7ZxVX4GvW4CFiES_-0YA\",\n"
            + "                \"title\": \"Anne-Marie - 2002 [Official Video]\",\n"
            + "                \"channelTitle\": \"Anne-Marie\"\n"
            + "            },\n"
            + "            \"statistics\": {\n"
            + "                \"viewCount\": \"2188652\",\n"
            + "                \"likeCount\": \"151698\",\n"
            + "                \"dislikeCount\": \"1929\",\n"
            + "                \"favoriteCount\": \"0\",\n"
            + "                \"commentCount\": \"9363\"\n"
            + "            }\n"
            + "        }\n"
            + "    ]\n"
            + "}";

    @Test
    public void testConstruct() {
        assertNotNull(youtubeInfoFetcher);
    }

    @Test
    public void testFetchData() throws IOException, JSONException {
        Call mockCall1 = mock(Call.class);
        setFakeResponse(mockCall1, VIDEO_CORRECT_RESPONSE);
        when(okHttpClient.newCall(any(Request.class)))
                .thenReturn(mockCall1);
        YoutubeVideo actualVideo = youtubeInfoFetcher.fetchData(SAMPLE_VIDEO_ID);
        YoutubeVideo expectedVideo = new YoutubeVideo("Anne-Marie - 2002 [Official Video]",
                "Anne-Marie",
                2188652, 151698, 1929);
        assertEquals(expectedVideo, actualVideo);
        assertEquals(expectedVideo.hashCode(), actualVideo.hashCode());
    }

    private void setFakeResponse(Call mockCall, String responseStr) throws IOException {
        ResponseBody mockRb = ResponseBody.create(okhttp3.MediaType.parse("text"), responseStr);
        System.out.println(mockRb);
        System.out.flush();
        Request request = new Request.Builder()
                .url("http://example.com")
                .build();
        Response response = new Response.Builder()
                .body(mockRb)
                .code(200)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("asd")
                .build();
        when(mockCall.execute()).thenReturn(response);
    }

}
