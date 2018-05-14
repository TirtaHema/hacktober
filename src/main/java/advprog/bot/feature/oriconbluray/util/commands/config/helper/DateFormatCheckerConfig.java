package advprog.bot.feature.oriconbluray.util.commands.config.helper;

import advprog.bot.feature.oriconbluray.util.helper.DateFormatChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateFormatCheckerConfig {

    @Bean
    DateFormatChecker dateFormatChecker() {
        return new DateFormatChecker();
    }
}
