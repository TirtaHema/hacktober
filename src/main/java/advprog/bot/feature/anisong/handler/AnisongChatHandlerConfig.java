package advprog.bot.feature.anisong.handler;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnisongChatHandlerConfig {

    @Bean
    AnisongChatHandler anisongChatHandler(BotController botController) {
        LineChatHandler handler = botController.getLineChatHandler();
        AnisongChatHandler anisongChatHandler =
                new AnisongChatHandler(handler);
        botController.replaceLineChatHandler(anisongChatHandler);
        return  anisongChatHandler;

    }
}