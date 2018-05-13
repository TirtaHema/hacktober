package advprog.tropical_top_10.bot;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotTropicalTopTenApplication {

    private static final Logger LOGGER = Logger.getLogger(BotTropicalTopTenApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(BotTropicalTopTenApplication.class, args);
    }
}
