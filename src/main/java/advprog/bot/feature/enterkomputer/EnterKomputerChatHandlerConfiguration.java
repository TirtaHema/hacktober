package advprog.bot.feature.enterkomputer;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnterKomputerChatHandlerConfiguration {
    @Bean
    EnterKomputerChatHandler enterkomputerChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        EnterKomputerChatHandler handler = new EnterKomputerChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
