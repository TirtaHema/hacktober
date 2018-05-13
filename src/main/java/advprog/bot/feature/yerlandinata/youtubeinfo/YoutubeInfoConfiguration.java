package advprog.bot.feature.yerlandinata.youtubeinfo;

import advprog.bot.feature.yerlandinata.youtubeinfo.fetcher.YoutubeInfoFetcherImpl;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeInfoConfiguration {

    public static final String API_KEY = "AIzaSyD3WEn-CKXOOrC36QA7QpGBnO8OnJFxO_Q";

    @Bean
    YoutubeInfoFetcherImpl youtubeInfoFetcher(OkHttpClient okHttpClient) {
        return new YoutubeInfoFetcherImpl(API_KEY, okHttpClient);
    }

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
