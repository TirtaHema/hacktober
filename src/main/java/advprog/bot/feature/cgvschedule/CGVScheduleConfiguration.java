package advprog.bot.feature.cgvschedule;

import advprog.bot.BotController;
import advprog.bot.feature.echo.EchoChatHandler;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CGVScheduleConfiguration {

    @Bean
    CGVScheduleHandler CGVScheduleConfiguration(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        CGVScheduleHandler handler = new CGVScheduleHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
