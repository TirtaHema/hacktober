package advprog.oriconbluray.util.commands.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import advprog.oriconbluray.util.commands.impls.DailyRankCommand;
import advprog.oriconbluray.util.commands.interfaces.RankCommand;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Test;

public class RankCommandControlTest {

    private RankCommandControl controller = new RankCommandControl();
    private RankCommand mockCommand = new DailyRankCommand();

    private String dailyDate = "2018-05-08";
    private String weeklyDate = "2018-05-07";

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
    public void testAddNewKeyReturnsNull() {
        assertNull(controller.addCommand("sample", mockCommand));
    }

    @Test
    public void testAddReplaceKeyReturnsCommand() {
        assertTrue(controller.addCommand("daily", mockCommand)
                instanceof RankCommand);
    }

    @Test
    public void testDeleteExistingCommandReturnsCommand() {
        assertTrue(controller.removeCommand("weekly")
                instanceof RankCommand);
    }

    @Test
    public void testDeleteUnknownCommandReturnsNull() {
        assertNull(controller.removeCommand("monthly"));
    }

    @Test
    public void testExecuteDailyCommandIsWorking() throws IOException {
        TextMessage message = controller.execute("daily", dailyDate);
        assertEquals(sampleDaily, message.getText());

    }

    @Test
    public void testExecuteWeeklyCommandIsWorking() throws IOException {
        TextMessage message = controller.execute("weekly", weeklyDate);
        assertEquals(sampleWeekly, message.getText());
    }

}