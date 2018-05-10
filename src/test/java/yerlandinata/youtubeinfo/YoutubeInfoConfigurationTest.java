package yerlandinata.youtubeinfo;

import static org.mockito.Mockito.mock;

import okhttp3.OkHttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeInfoConfigurationTest {

    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    private static final String API_KEY = "AIzaSyD3WEn-CKXOOrC36QA7QpGBnO8OnJFxO_Q";

    @Bean
    YoutubeInfoFetcher youtubeInfoFetcher(OkHttpClient okHttpClient) {
        return new YoutubeInfoFetcher(API_KEY, okHttpClient);
    }

    @Bean
    OkHttpClient okHttpClient() {
        return mock(OkHttpClient.class);
    }

}
