package advprog.bot.feature.oriconbluray.handler;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OriconBlurayChatHandlerConfig {

    @Bean
    OriconBlurayChatHandler oriconBlurayChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        OriconBlurayChatHandler handler =
                new OriconBlurayChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
