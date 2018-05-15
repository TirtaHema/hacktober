package advprog.bot.feature.billboardtropicalartist;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BillboardTropicalArtistConfiguration {

    private BotController controller;

    @Bean
    BillboardTropicalArtistHandler billboardTropicalArtistHandler(BotController controller) {
        this.controller = controller;
        LineChatHandler currenctChatHandler = controller.getLineChatHandler();
        BillboardTropicalArtistHandler handler
                = new BillboardTropicalArtistHandler(currenctChatHandler);
        controller.replaceLineChatHandler(handler);
        return handler;
    }

}
