package advprog.bot.feature.Language;

import advprog.bot.BotController;
import advprog.bot.feature.echo.EchoChatHandler;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DetectChatHandlerConfiguration {
    @Bean
    DetectChatHandler echoChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        DetectChatHandler handler = new EchoChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
