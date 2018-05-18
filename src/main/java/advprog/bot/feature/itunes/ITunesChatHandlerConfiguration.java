package advprog.bot.feature.itunes;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ITunesChatHandlerConfiguration {
    @Bean
    ITunesChatHandler itunesChatHandler(BotController controller) {
        LineChatHandler currentChatHandler = controller.getLineChatHandler();
        ITunesChatHandler handler = new ITunesChatHandler(currentChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
