package advprog.bot.feature.oriconbluray.util.commands.config.control;

import advprog.bot.feature.oriconbluray.util.commands.control.RankCommandControl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RankCommandControlConfig {

    @Bean
    RankCommandControl rankCommandControl() {
        return new RankCommandControl();
    }
}
