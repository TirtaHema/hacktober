package advprog.bot.feature.hangoutplace;

/**
 * Created by fazasaffanah on 22/05/2018.
 */
import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HangoutPlaceChatHandlerConfiguration {

    @Bean
    HangoutPlaceChatHandler hangoutPlaceChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        HangoutPlaceChatHandler handler = new HangoutPlaceChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}
