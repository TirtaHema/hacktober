package advprog.example.bot.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwitterControllerTestConfiguration {
    @Bean
    TwitterBotController twitterController() {
        return new TwitterBotController();
    }
}
