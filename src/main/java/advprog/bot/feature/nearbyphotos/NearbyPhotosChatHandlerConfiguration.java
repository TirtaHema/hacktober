package advprog.bot.feature.nearbyphotos;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NearbyPhotosChatHandlerConfiguration {

    @Bean
    NearbyPhotosChatHandler nearbyPhotosChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        NearbyPhotosChatHandler handler = new NearbyPhotosChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }
}
