package advprog.oriconbluray.util.commands;

import advprog.oriconbluray.util.service.rankscrapper.RankScrapper;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DailyRankCommand implements RankCommand {

    private RankScrapper rankScrapper = new RankScrapper();

    private static final String DAILY_URL = "https://www.oricon.co.jp/rank/bd/d/";


    @Override
    public TextMessage execute(String date) throws IOException {
        String errorMsg = dateCheck(date);
        if (errorMsg != null) {
            return new TextMessage(errorMsg);
        }

        String rankList = rankScrapper
                .scrapRank(DAILY_URL + date + "/");

        rankList = rankList.contains("Not a valid URL")
                ? "It seems there's no rank charts on that date"
                : rankList;
        return new TextMessage(rankList);
    }

    private String dateCheck(String date) {
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
