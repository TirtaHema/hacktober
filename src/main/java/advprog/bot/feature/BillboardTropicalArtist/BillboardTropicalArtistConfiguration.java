package advprog.bot.feature.BillboardTropicalArtist;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BillboardTropicalArtistConfiguration {

    @Bean
    BillboardTropicalArtistHandler billboardTropicalArtistHandler(BotController controller) {
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        BillboardTropicalArtistHandler handler = new BillboardTropicalArtistHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}
