package advprog.example.bot;

import org.springframework.boot.SpringApplication;

import java.util.logging.Logger;

public class CountrySongChartBot {

    private static final Logger LOGGER = Logger.getLogger(BotExampleApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(BotExampleApplication.class, args);
    }
}
