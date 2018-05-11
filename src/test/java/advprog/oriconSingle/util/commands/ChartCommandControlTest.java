package advprog.oriconSingle.util.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import com.linecorp.bot.model.message.TextMessage;
import org.junit.Test;

public class ChartCommandControlTest {

    private ChartCommandControl controller = new ChartCommandControl();
    private ChartCommand emptyCommand = new DailyChartCommand();

    private String dailyDate = "2018-05-09";
    private String weeklyDate = "2018-05-07";
    private String monthlyDate = "2018-04";
    private String yearlyDate = "2017";

    private String sampleDailyOutput = "Ryokai desu~ This is your top 10 songs as you "
            + "requested~\n" + "\n(1) 進化理論 - BOYS AND MEN - 2018-05-09 - Not Given\n"
            + "(2) シンクロニシティ - 乃木坂46 - 2018-04-25 - Not Given\n"
            + "(3) Eclipse - 蒼井翔太 - 2018-05-09 - Not Given\n"
            + "(4) この道を/会いに行く/坂道を上って/小さな風景 - 小田和正 - 2018-05-02 - Not Given\n"
            + "(5) THE IDOLM@STER SideM WORLD TRE@SURE 01(永遠(とわ)なる四銃士) - "
            + "天道輝(仲村宗悟),葛之葉雨彦(笠間淳),握野英雄(熊谷健太郎),紅井朱雀(益山武明) - "
            + "2018-05-09 - Not Given\n"
            + "(6) 誓い - 雨宮天 - 2018-05-09 - Not Given\n"
            + "(7) アップデート - miwa - 2018-05-09 - Not Given\n"
            + "(8) 泣けないぜ…共感詐欺/Uraha=Lover/君だけじゃないさ...friends"
            + "(2018アコースティックVer.) - アンジュルム - 2018-05-09 - Not Given\n"
            + "(9) 泡沫夢幻・胡蝶刃 〜GRANBLUE FANTASY〜 - ナルメア(M・A・O) - "
            + "2018-05-02 - Not Given\n"
            + "(10) Crosswalk/リワインド - 鈴木みのり - 2018-05-09 - Not Given";

    private String sampleWeeklyOutput = "Ryokai desu~ This is your top 10 songs as you "
            + "requested~\n" + "\n(1) シンクロニシティ - 乃木坂46 - 2018-04-25 - Not Given\n"
            + "(2) Fandango - THE RAMPAGE from EXILE TRIBE - 2018-04-25 - Not Given\n"
            + "(3) Fiction e.p - sumika - 2018-04-25 - Not Given\n"
            + "(4) Bumblebee - Lead - 2018-04-25 - Not Given\n"
            + "(5) 人間を被る - DIR EN GREY - 2018-04-25 - Not Given\n"
            + "(6) 泣きたいくらい - 大原櫻子 - 2018-04-25 - Not Given\n"
            + "(7) THE IDOLM@STER MILLION THE@TER GENERATION 07 トゥインクルリズム"
            + "(ZETTAI × BREAK!! トゥインクルリズム) - トゥインクルリズム[中谷育(原嶋あかり),"
            + "七尾百合子(伊藤美来),松田亜利沙(村川梨衣)] - 2018-04-25 - Not Given\n"
            + "(8) 春はどこから来るのか? - NGT48 - 2018-04-11 - Not Given\n"
            + "(9) Ask Yourself - KAT-TUN - 2018-04-18 - Not Given\n"
            + "(10) 鍵穴 - the Raid. - 2018-04-25 - Not Given";

    private String sampleMonthlyOutput = "Ryokai desu~ This is your top 10 songs as you "
            + "requested~\n" + "\n(1) シンクロニシティ - 乃木坂46 - 2018-04-25 - 1214510\n"
            + "(2) 早送りカレンダー - HKT48 - 2018-05-02 - 165176\n"
            + "(3) Ask Yourself - KAT-TUN - 2018-04-18 - 149081\n"
            + "(4) 春はどこから来るのか? - NGT48 - 2018-04-11 - 128565\n"
            + "(5) 君のAchoo! - ラストアイドル(シュークリームロケッツ) - 2018-04-18 - 58198\n"
            + "(6) SEXY SEXY/泣いていいよ/Vivid Midnight - Juice=Juice - 2018-04-18 - 54728\n"
            + "(7) ガラスを割れ! - 欅坂46 - 2018-03-07 - 54131\n"
            + "(8) ONE TIMES ONE - コブクロ - 2018-04-11 - 39395\n"
            + "(9) ODD FUTURE - UVERworld - 2018-05-02 - 37347\n"
            + "(10) Shanana ここにおいで - B2takes! - 2018-04-11 - 36455";

    private String sampleYearlyOutput = "Ryokai desu~ This is your top 10 songs as you "
            + "requested~\n" + "\n(1) 願いごとの持ち腐れ - AKB48 - 2017-05-31 - Not Given\n"
            + "(2) #好きなんだ - AKB48 - 2017-08-30 - Not Given\n"
            + "(3) 11月のアンクレット - AKB48 - 2017-11-22 - Not Given\n"
            + "(4) シュートサイン - AKB48 - 2017-03-15 - Not Given\n"
            + "(5) 逃げ水 - 乃木坂46 - 2017-08-09 - Not Given\n"
            + "(6) インフルエンサー - 乃木坂46 - 2017-03-22 - Not Given\n"
            + "(7) いつかできるから今日できる - 乃木坂46 - 2017-10-11 - Not Given\n"
            + "(8) 不協和音 - 欅坂46 - 2017-04-05 - Not Given\n"
            + "(9) 風に吹かれても - 欅坂46 - 2017-10-25 - Not Given\n"
            + "(10) Doors 〜勇気の軌跡〜 - 嵐 - 2017-11-08 - Not Given";

    @Test
    public void testKeyReturnNull() {
        assertNull(controller.addCommand("sample", emptyCommand));
    }

    @Test
    public void testExecuteDailyCommandIsWorking() throws IOException {
        TextMessage message = controller.execute("daily", dailyDate);
        assertEquals(sampleDailyOutput, message.getText());
    }

    @Test
    public void testExecuteWeeklyCommandIsWorking() throws IOException {
        TextMessage message = controller.execute("weekly", weeklyDate);
        assertEquals(sampleWeeklyOutput, message.getText());
    }

    @Test
    public void testExecuteMonthlyCommandIsWorking() throws IOException {
        TextMessage message = controller.execute("monthly", monthlyDate);
        assertEquals(sampleMonthlyOutput, message.getText());
    }

    @Test
    public void testExecuteYearlyCommandIsWorking() throws IOException {
        TextMessage message = controller.execute("yearly", yearlyDate);
        assertEquals(sampleYearlyOutput, message.getText());
    }
}
