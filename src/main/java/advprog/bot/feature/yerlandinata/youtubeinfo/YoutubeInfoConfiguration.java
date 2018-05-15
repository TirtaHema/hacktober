package advprog.bot.feature.yerlandinata.youtubeinfo;

import advprog.bot.BotController;
import advprog.bot.feature.yerlandinata.youtubeinfo.fetcher.YoutubeInfoFetcher;
import advprog.bot.feature.yerlandinata.youtubeinfo.fetcher.YoutubeInfoFetcherImpl;
import advprog.bot.feature.yerlandinata.youtubeinfo.parser.AbstractYoutubeVideoIdParser;
import advprog.bot.feature.yerlandinata.youtubeinfo.parser.DefaultYoutubeVideoIdParser;
import advprog.bot.line.LineChatHandler;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeInfoConfiguration {

    private static final String API_KEY = "AIzaSyD3WEn-CKXOOrC36QA7QpGBnO8OnJFxO_Q";

    @Bean
    YoutubeInfoChatHandler youtubeInfoChatHandler(
            BotController controller,
            YoutubeInfoFetcher youtubeInfoFetcher,
            AbstractYoutubeVideoIdParser youtubeVideoIdParser
    ) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        YoutubeInfoChatHandler handler = new YoutubeInfoChatHandler(
                currenctChatHandler, youtubeInfoFetcher, youtubeVideoIdParser);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

    @Bean
    AbstractYoutubeVideoIdParser chainOfYoutubeVideoIdParser() {
        return new DefaultYoutubeVideoIdParser();
    }

    @Bean
    YoutubeInfoFetcherImpl youtubeInfoFetcher(OkHttpClient okHttpClient) {
        return new YoutubeInfoFetcherImpl(API_KEY, okHttpClient);
    }

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
