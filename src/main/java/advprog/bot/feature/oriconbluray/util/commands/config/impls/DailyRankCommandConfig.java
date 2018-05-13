package advprog.bot.feature.oriconbluray.util.commands.config.impls;

import advprog.bot.feature.oriconbluray.util.commands.impls.DailyRankCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DailyRankCommandConfig {

    @Bean
    DailyRankCommand dailyRankCommand() {
        return new DailyRankCommand();
    }
}