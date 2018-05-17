package advprog.bot.feature.acronym;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AcronymChatHandlerConfiguration {

    @Bean
    AcronymChatHandler acronymChatHandler(BotController controller) {
        LineChatHandler currentChatHandler = controller.getLineChatHandler();
        AcronymChatHandler handler = new AcronymChatHandler(currentChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}
