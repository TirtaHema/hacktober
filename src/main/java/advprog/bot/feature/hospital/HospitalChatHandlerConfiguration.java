package advprog.bot.feature.hospital;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HospitalChatHandlerConfiguration {

    @Bean
    HospitalChatHandler hospitalChatHandler(BotController controller) {
        LineChatHandler currentChatHandler = controller.getLineChatHandler();
        HospitalChatHandler handler = new HospitalChatHandler(currentChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
