package advprog.bot.feature.dice;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiceBotConfiguration {

    @Bean
    DiceChatHandler diceBotChatHandler(BotController controller) {
        LineChatHandler currentChatHandler = controller.getLineChatHandler();
        DiceChatHandler diceController = new DiceChatHandler(currentChatHandler);
        controller.replaceLineChatHandler(diceController);
        return diceController;
    }
}

