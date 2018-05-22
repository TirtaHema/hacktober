package advprog.bot.feature.scheduler;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerChatHandlerConfiguration {

    @Bean
    SchedulerChatHandler schedulerChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        SchedulerChatHandler handler = new SchedulerChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}
