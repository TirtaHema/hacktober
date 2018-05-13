package advprog.bot.feature.yerlandinata.youtubeinfo.fetcher;

import advprog.bot.feature.yerlandinata.youtubeinfo.YoutubeVideo;

import java.io.IOException;

import org.json.JSONException;

public interface YoutubeInfoFetcher {
    YoutubeVideo fetchData(String videoId) throws IOException, JSONException;
}
