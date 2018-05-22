package advprog.bot.feature.bikun;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BikunChatHandlerConfiguration {

    @Bean
    BikunChatHandler echoChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        BikunChatHandler handler = new BikunChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}
