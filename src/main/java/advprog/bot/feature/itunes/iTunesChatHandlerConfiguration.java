package advprog.bot.feature.itunes;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class iTunesChatHandlerConfiguration {
    @Bean
    iTunesChatHandler itunesChatHandler(BotController controller) {
        LineChatHandler currentChatHandler = controller.getLineChatHandler();
        iTunesChatHandler handler = new iTunesChatHandler(currentChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
