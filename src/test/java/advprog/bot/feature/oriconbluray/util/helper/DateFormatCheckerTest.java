package advprog.bot.feature.oriconbluray.util.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DateFormatCheckerTest {

    @Autowired
    private DateFormatChecker checker;

    private String invalidFormatDate = "20010101";
    private String absurdDate = "2017-06-32";
    private String okDate = "2018-01-01";

    private String errorFormatMsg = "Hmmmmm, forget about the date format?"
            + " No worries~ I can help you~ Here's the format\n\n"
            + "YYYY-MM-DD\n\nRemember it well, kay?";

    private String absurdDateMsg = "N-nani?! Is such date even exist?! You would"
            + " like to check the calendar... and enter an existing "
            + "date...";

    @Test
    public void testFormatErrorChecking() {
        assertEquals(errorFormatMsg, checker
                .checkAndGetErrorMessage(invalidFormatDate));
    }

    @Test
    public void testAbsurdDateChecking() {
        assertEquals(absurdDateMsg, checker
                .checkAndGetErrorMessage(absurdDate));
    }

    @Test
    public void testOkDateChecking() {
        assertNull(checker.checkAndGetErrorMessage(okDate));
    }

}