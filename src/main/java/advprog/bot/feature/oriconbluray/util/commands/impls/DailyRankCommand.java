package advprog.bot.feature.oriconbluray.util.commands.impls;

import advprog.bot.feature.oriconbluray.util.commands.interfaces.RankCommand;
import advprog.bot.feature.oriconbluray.util.service.rankscrapper.RankScrapper;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component
public class DailyRankCommand implements RankCommand {

    private RankScrapper rankScrapper = new RankScrapper();

    private final String dailyUrl = "https://www.oricon.co.jp/rank/bd/d/";

    @Override
    public TextMessage execute(String date) throws IOException {
        String errorMsg = dateCheck(date);
        if (errorMsg != null) {
            return new TextMessage(errorMsg);
        }

        String rankList = rankScrapper
                .scrapRank(dailyUrl + date + "/");

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
