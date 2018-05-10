package yerlandinata.youtubeinfo;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeInfoConfiguration {

    private static final String API_KEY = "AIzaSyD3WEn-CKXOOrC36QA7QpGBnO8OnJFxO_Q";

    @Bean
    YoutubeInfoFetcher youtubeInfoFetcher(OkHttpClient okHttpClient) {
        return new YoutubeInfoFetcher(API_KEY, okHttpClient);
    }

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
