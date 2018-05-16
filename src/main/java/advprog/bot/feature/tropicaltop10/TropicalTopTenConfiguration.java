package advprog.bot.feature.tropicaltop10;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TropicalTopTenConfiguration {

    private BotController controller;

    @Bean
    TropicalTopTenHandler tropicalTopTenHandler(BotController controller) {
        this.controller = controller;
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        TropicalTopTenHandler handler
                = new TropicalTopTenHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}
