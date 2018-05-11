package advprog.oriconbluray.util.commands;

import advprog.oriconbluray.util.service.rankscrapper.RankScrapper;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class DailyRankCommand implements RankCommand {

    private RankScrapper rankScrapper = new RankScrapper();

    private static final String DAILY_URL = "https://www.oricon.co.jp/rank/bd/d/";


    @Override
    public TextMessage execute(String date) throws IOException {
        String rankList = rankScrapper
                .scrapRank(DAILY_URL + date + "/");

        rankList = rankList.contains("Not a valid URL")
                ? "It seems there's no rank charts on that date"
                : rankList;
        return new TextMessage(rankList);
    }
}
