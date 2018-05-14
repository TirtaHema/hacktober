package advprog.bot.feature.oriconbluray.util.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatChecker {

    public String checkAndGetErrorMessage(String date) {
        SimpleDateFormat formatChecker = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatChecker.parse(date);
        } catch (ParseException e) {
            return "Hmmmmm, forget about the date format?"
                    + " No worries~ I can help you~ Here's the format\n\n"
                    + "YYYY-MM-DD\n\nRemember it well, kay?";
        }

        formatChecker.setLenient(false);

        try {
            formatChecker.parse(date);
        } catch (ParseException e) {
            return "N-nani?! Is such date even exist?! You would"
                    + " like to check the calendar... and enter an existing "
                    + "date...";
        }

        return null;
    }
}
