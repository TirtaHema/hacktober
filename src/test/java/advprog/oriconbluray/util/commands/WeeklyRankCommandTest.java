package advprog.oriconbluray.util.commands;

import com.linecorp.bot.model.message.TextMessage;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeeklyRankCommandTest {

    private String sampleDate = "2018-05-07";
    private String invalidDate = "2018-05-09";
    private RankCommand command = new WeeklyRankCommand();
    private TextMessage reply;

    private String sampleWeekly = "Haiiii~ Here's your top 10 bluray as you requested~\n"
            + "\n(1) スター・ウォーズ/最後のジェダイ MovieNEX(初回版) - マーク・ハミル - 2018-04-25\n"
            + "(2) ラブライブ!サンシャイン!! 2nd Season 5【特装限定版】 - アニメーション - 2018-04-24\n"
            + "(3) SHOGO HAMADA ON THE ROAD 2015-2016“Journey of a Songwriter” - "
            + "浜田省吾 - 2018-04-25\n"
            + "(4) ラブライブ!サンシャイン!! Aqours 2nd LoveLive! HAPPY PARTY TRAIN TOUR "
            + "Blu-ray Memorial BOX - Aqours - 2018-04-25\n"
            + "(5) THE IDOLM@STER SideM GREETING TOUR 2017 〜BEYOND THE DREAM〜 "
            + "LIVE Blu-ray - アイドルマスターSideM - 2018-04-25\n"
            + "(6) スター・ウォーズ/最後のジェダイ 4K UHD MovieNEX - マーク・ハミル - 2018-04-25\n"
            + "(7) A3! FIRST Blooming FESTIVAL【Blu-ray】 - - - 2018-04-25\n"
            + "(8) アイドリッシュセブン Blu-ray 3【特装限定版】 - アニメーション - 2018-04-24\n"
            + "(9) アウトレイジ 最終章 - ビートたけし - 2018-04-24\n"
            + "(10) アイドルマスター SideM 5(完全生産限定版) - アニメーション - 2018-04-25";

    @Test
    public void testValidDateReturnRank() {
        reply = command.execute(sampleDate);

        assertEquals(sampleWeekly, reply.getText());
    }

    @Test
    public void  testInvalidDateReturnErrorMsg() {
        reply = command.execute(invalidDate);

        assertEquals("It seems there's no rank charts on that date"
                        + ". Make sure the date you assign is on monday"
                , reply.getText());
    }
}
