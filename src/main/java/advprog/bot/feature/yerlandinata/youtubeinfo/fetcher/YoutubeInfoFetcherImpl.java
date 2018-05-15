package advprog.bot.feature.yerlandinata.youtubeinfo.fetcher;

import advprog.bot.feature.yerlandinata.youtubeinfo.YoutubeVideo;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class YoutubeInfoFetcherImpl implements YoutubeInfoFetcher {

    private final String apiKey;
    private final OkHttpClient okHttpClient;
    private static final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/videos/";

    public YoutubeInfoFetcherImpl(String apiKey, OkHttpClient okHttpClient) {
        this.apiKey = apiKey;
        this.okHttpClient = okHttpClient;
    }

    public YoutubeVideo fetchData(String videoId) throws IOException, JSONException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(YOUTUBE_API_URL).newBuilder();
        urlBuilder.addQueryParameter("key", apiKey);
        urlBuilder.addQueryParameter("part", "snippet,statistics");
        urlBuilder.addQueryParameter("id", videoId);
        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return parseJson(response.body().string(), videoId);
    }

    private YoutubeVideo parseJson(String jsonResponse, String videoId) throws JSONException {
        JSONObject video = new JSONObject(jsonResponse);
        if (video.getJSONObject("pageInfo")
                .getInt("totalResults") == 0) {
            throw new YoutubeVideoNotFoundException(
                    String.format("Video with id %s yields 0 result", videoId)
            );
        }
        String title = video.getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("snippet")
                .getString("title");
        String channel = video.getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("snippet")
                .getString("channelTitle");
        int views = video.getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("statistics")
                .getInt("viewCount");
        int likes = video.getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("statistics")
                .getInt("likeCount");
        int dislike = video.getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("statistics")
                .getInt("dislikeCount");
        return new YoutubeVideo(title, channel, views, likes, dislike);
    }

}
