package advprog.BillBoard.bot;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BillBoardBotApplication {
    private static final Logger LOGGER = Logger.getLogger(BillBoardBotApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application starting ...");
        SpringApplication.run(BillBoardBotApplication.class, args);
    }
}
