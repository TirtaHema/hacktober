package advprog.bot.feature.toplaughers;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopLaughersChatHandlerConfiguration {

    @Bean
    TopLaughersChatHandler topLaughersChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        TopLaughersChatHandler handler = new TopLaughersChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}
