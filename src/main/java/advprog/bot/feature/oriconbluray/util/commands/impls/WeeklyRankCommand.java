package advprog.bot.feature.oriconbluray.util.commands.impls;

import advprog.bot.feature.oriconbluray.util.commands.interfaces.RankCommand;
import advprog.bot.feature.oriconbluray.util.helper.DateFormatChecker;
import advprog.bot.feature.oriconbluray.util.service.rankscrapper.RankScrapper;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class WeeklyRankCommand implements RankCommand {

    private RankScrapper rankScrapper = new RankScrapper();
    private DateFormatChecker dateChecker = new DateFormatChecker();

    private final String weeklyUrl = "https://www.oricon.co.jp/rank/bd/w/";

    @Override
    public TextMessage execute(String date) throws IOException {
        String errorMsg = dateChecker.checkAndGetErrorMessage(date);
        if (errorMsg != null) {
            return new TextMessage(errorMsg);
        }

        String rankList = rankScrapper
                .scrapRank(weeklyUrl + date + "/");

        rankList = rankList.contains("Not a valid URL")
                ? "It seems there's no rank charts on that date"
                + ". Make sure the date you assign is on monday"
                : rankList;
        return new TextMessage(rankList);
    }
}
