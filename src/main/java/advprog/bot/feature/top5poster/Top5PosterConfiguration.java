package advprog.bot.feature.top5poster;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Top5PosterConfiguration {

    @Bean
    public Top5PosterHandler top5PosterHandler(
            BotController controller, Top5PosterService service) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        Top5PosterHandler handler = new Top5PosterHandler(currenctChatHandler, service);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
