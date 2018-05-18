package advprog.bot.feature.fakenews;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakeNewsChatHandlerConfiguration {

    @Bean
    FakeNewsChatHandler fakeNewsChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        FakeNewsChatHandler handler = new FakeNewsChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
