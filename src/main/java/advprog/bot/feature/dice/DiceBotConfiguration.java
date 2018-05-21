package advprog.bot.feature.dice;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiceBotConfiguration {

    @Bean
    DiceController diceBotChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        DiceController diceController = new DiceController(currenctChatHandler);
        controller.replaceLineChatHandler(diceController);
        return diceController;
    }
}

