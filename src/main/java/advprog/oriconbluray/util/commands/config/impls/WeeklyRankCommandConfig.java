package advprog.oriconbluray.util.commands.config.impls;

import advprog.oriconbluray.util.commands.impls.WeeklyRankCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyRankCommandConfig {

    @Bean
    WeeklyRankCommand weeklyRankCommand() {
        return new WeeklyRankCommand();
    }
}
