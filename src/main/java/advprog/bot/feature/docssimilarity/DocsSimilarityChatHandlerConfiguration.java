package advprog.bot.feature.docssimilarity;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocsSimilarityChatHandlerConfiguration {
    @Bean
    DocsSimilarityChatHandler docsSimilarityChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        DocsSimilarityChatHandler handler = new DocsSimilarityChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
