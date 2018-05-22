package advprog.bot.feature.vgmdb.vgmdbhandler;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VgmdbConfiguration {

    @Bean
    VgmdbHandler vgmdbHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        VgmdbHandler handler = new VgmdbHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}
