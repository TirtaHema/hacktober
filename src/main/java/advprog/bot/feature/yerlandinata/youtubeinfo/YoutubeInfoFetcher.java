package advprog.bot.feature.yerlandinata.youtubeinfo;

import java.io.IOException;

import org.json.JSONException;

public interface YoutubeInfoFetcher {
    YoutubeVideo fetchData(String videoId) throws IOException, JSONException;
}
