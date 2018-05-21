package advprog.bot.feature.dice;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiceBotConfiguration {

    @Bean
    DiceChatHandler diceBotChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        DiceChatHandler diceController = new DiceChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(diceController);
        return diceController;
    }
}

