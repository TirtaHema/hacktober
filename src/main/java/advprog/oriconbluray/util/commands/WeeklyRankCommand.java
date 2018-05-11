package advprog.oriconbluray.util.commands;

import advprog.oriconbluray.util.service.rankscrapper.RankScrapper;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class WeeklyRankCommand implements RankCommand {

    private RankScrapper rankScrapper = new RankScrapper();

    private final String WEEKLY_URL = "https://www.oricon.co.jp/rank/bd/w/";

    @Override
    public TextMessage execute(String date) throws IOException {
        String rankList = rankScrapper
                .scrapRank(WEEKLY_URL + date + "/");

        rankList = rankList.contains("Not a valid URL") ?
                "It seems there's no rank charts on that date"
                + ". Make sure the date you assign is on monday"
                : rankList;
        return new TextMessage(rankList);
    }
}
