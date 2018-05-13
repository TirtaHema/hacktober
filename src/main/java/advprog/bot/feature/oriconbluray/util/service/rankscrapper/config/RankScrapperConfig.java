package advprog.bot.feature.oriconbluray.util.service.rankscrapper.config;

import advprog.bot.feature.oriconbluray.util.service.rankscrapper.RankScrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RankScrapperConfig {

    @Bean
    RankScrapper rankScrapper() {
        return new RankScrapper();
    }
}
