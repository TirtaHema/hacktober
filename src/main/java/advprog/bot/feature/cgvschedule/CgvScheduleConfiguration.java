package advprog.bot.feature.cgvschedule;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CgvScheduleConfiguration {

    @Bean
    CgvScheduleHandler cgvScheduleHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        CgvScheduleHandler handler = new CgvScheduleHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
