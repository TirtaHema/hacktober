package advprog.bot.feature.fakejson;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakeJsonChatHandlerConfiguration {
    @Bean
    FakeJsonChatHandler fakeJsonChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        FakeJsonChatHandler handler = new FakeJsonChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
