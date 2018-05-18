package advprog.bot.feature.uberestimate;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UberEstimateChatHandlerConfiguration {

    @Bean
    UberEstimateChatHandler uberEstimateChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        UberEstimateChatHandler handler = new UberEstimateChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
