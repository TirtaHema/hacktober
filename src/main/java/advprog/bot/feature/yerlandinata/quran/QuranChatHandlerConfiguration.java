package advprog.bot.feature.yerlandinata.quran;

import advprog.bot.BotController;
import advprog.bot.feature.yerlandinata.quran.fetcher.AyatQuranFetcher;
import advprog.bot.feature.yerlandinata.quran.fetcher.AyatQuranFetcherImpl;
import advprog.bot.feature.yerlandinata.quran.fetcher.SurahQuranFetcher;
import advprog.bot.feature.yerlandinata.quran.fetcher.SurahQuranFetcherImpl;
import advprog.bot.feature.yerlandinata.quran.privatechat.interactive.InteractivePrivateQuranChatHandler;
import advprog.bot.feature.yerlandinata.quran.privatechat.interactive.service.InteractiveAyatFetcherService;
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
    InteractivePrivateQuranChatHandler interactivePrivateQuranChatHandler(
            BotController controller,
            SurahQuranFetcher surahQuranFetcher,
            InteractiveAyatFetcherService interactiveAyatFetcherService
    ) {
        LineChatHandler currentChatHandler = controller.getLineChatHandler();
        InteractivePrivateQuranChatHandler handler = new InteractivePrivateQuranChatHandler(
                currentChatHandler, surahQuranFetcher, interactiveAyatFetcherService
        );
        controller.replaceLineChatHandler(handler);
        return handler;
    }

    @Bean
    AyatQuranFetcher ayatQuranFetcher(OkHttpClient okHttpClient) {
        return new AyatQuranFetcherImpl(okHttpClient);
    }

    @Bean
    SurahQuranFetcher surahQuranFetcher(OkHttpClient okHttpClient) {
        return new SurahQuranFetcherImpl(okHttpClient);
    }

    @Bean
    InteractiveAyatFetcherService interactiveAyatFetcherServiceStub() {
        return new InteractiveAyatFetcherService() {
            @Override
            public void recordUserSurahSelection(String userId, int surah) {

            }

            @Override
            public AyatQuran fetchAyat(String userId, int ayat) throws IllegalStateException {
                return null;
            }
        };
    }

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
