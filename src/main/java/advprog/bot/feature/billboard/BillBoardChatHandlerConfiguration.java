package advprog.bot.feature.billboard;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BillBoardChatHandlerConfiguration {
    @Bean
    BillBoardChatHandler billboardChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        BillBoardChatHandler handler = new BillBoardChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
