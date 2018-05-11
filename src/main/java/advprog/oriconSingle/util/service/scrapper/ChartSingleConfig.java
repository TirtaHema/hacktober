package advprog.oriconSingle.util.service.scrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChartSingleConfig {

    @Bean
    ChartSingle chartSingle() {
        return new ChartSingle();
    }
}
