package advprog.oriconbluray.util.service.rankscrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RankScrapperConfig {

    @Bean
    RankScrapper rankScrapper() {
        return new RankScrapper();
    }
}
