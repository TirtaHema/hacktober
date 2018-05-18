package advprog.bot.feature.yerlandinata.quran;

import advprog.bot.BotController;
import advprog.bot.feature.yerlandinata.quran.fetcher.AyatQuranFetcher;
import advprog.bot.feature.yerlandinata.quran.fetcher.AyatQuranFetcherImpl;
import advprog.bot.feature.yerlandinata.quran.privatechat.noninteractive.PrivateQuranChatHandler;
import advprog.bot.line.LineChatHandler;

import com.squareup.okhttp.OkHttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuranChatHandlerConfiguration {

    @Bean
    PrivateQuranChatHandler privateQuranChatHandler(
            BotController controller, AyatQuranFetcher ayatQuranFetcher
    ) {
        LineChatHandler currentChatHandler = controller.getLineChatHandler();
        PrivateQuranChatHandler handler = new PrivateQuranChatHandler(
                currentChatHandler, ayatQuranFetcher
        );
        controller.replaceLineChatHandler(handler);
        return handler;
    }

    @Bean
    AyatQuranFetcher ayatQuranFetcher(OkHttpClient okHttpClient) {
        return new AyatQuranFetcherImpl(okHttpClient);
    }

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
