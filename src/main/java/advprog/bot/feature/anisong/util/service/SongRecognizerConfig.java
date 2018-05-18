package advprog.bot.feature.anisong.util.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SongRecognizerConfig {

    @Bean
    SongRecognizer songRecognizer() {
        return new SongRecognizer();
    }
}
