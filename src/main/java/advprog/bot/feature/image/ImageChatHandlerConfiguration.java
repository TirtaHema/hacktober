package advprog.bot.feature.image;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import advprog.bot.feature.image.ImageChatHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageChatHandlerConfiguration {

    @Bean
    ImageChatHandler imageChatHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        ImageChatHandler handler = new ImageChatHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}
