package advprog.oriconbluray.util.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Test;

public class DailyRankCommandTest {

    private String sampleDate = "2018-05-08";
    private String invalidDate = "2019-05-08";
    private RankCommand command = new DailyRankCommand();
    private TextMessage reply;

    private String sampleDaily = "Haiiii~ Here's your top 10 bluray as you requested~\n"
            + "\n(1) 劇場版「Fate/stay night[Heaven’s Feel]�T.presage flower」"
            + "(完全生産限定版) - アニメーション - 2018-05-09\n"
            + "(2) ナラタージュ Blu-ray 豪華版 - 松本潤 - 2018-05-09\n"
            + "(3) 仮面ライダー平成ジェネレーションズFINAL ビルド&エグゼイドwith"
            + "レジェンドライダー コレクターズパック - 犬飼貴丈 - 2018-05-09\n"
            + "(4) CNBLUE 2017 ARENA LIVE TOUR 〜Starting Over〜 @YOKOHAMA ARENA"
            + " - CNBLUE - 2018-05-09\n"
            + "(5) Little Glee Monster Arena Tour 2018 -juice !!!!!- "
            + "at YOKOHAMA ARENA - Little Glee Monster - 2018-05-09\n"
            + "(6) 劇場版「Fate/stay night[Heaven’s Feel]�T.presage flower」"
            + "(通常版) - アニメーション - 2018-05-09\n"
            + "(7) カードキャプターさくら クリアカード編 Vol.1＜初回仕様版＞ "
            + "- アニメーション - 2018-05-09\n"
            + "(8) スター・ウォーズ/最後のジェダイ MovieNEX(初回版) - "
            + "マーク・ハミル - 2018-04-25\n"
            + "(9) 超英雄祭 KAMEN RIDER×SUPER SENTAI LIVE&SHOW 2018 "
            + "- - - 2018-05-09\n"
            + "(10) ワイルド・スピード ICE BREAK - ヴィン・ディーゼル - 2018-05-09";

    @Test
    public void testRightinstance() {
        assertTrue(command instanceof DailyRankCommand);
    }


    @Test
    public void testValidDateReturnRank() throws IOException {
        reply = command.execute(sampleDate);

        assertEquals(sampleDaily, reply.getText());
    }

    @Test
    public void  testInvalidDateReturnErrorMsg() throws IOException {
        reply = command.execute(invalidDate);

        assertEquals("It seems there's no rank charts on that date",
                reply.getText());
    }
}
