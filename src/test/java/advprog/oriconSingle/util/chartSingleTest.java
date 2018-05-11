package advprog.oriconSingle.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Test;

public class chartSingleTest {

    private String sampleDailyUrl = "https://www.oricon.co.jp/rank/js/d/2018-05-09/";
    private String sampleWeeklyUrl = "https://www.oricon.co.jp/rank/js/w/2018-05-07/";
    private String sampleMonthlyUrl = "https://www.oricon.co.jp/rank/js/m/2018-04/";
    private String sampleYearlyUrl = "https://www.oricon.co.jp/rank/js/y/2017/";
    private String falseDailyUrl =  "https://www.oricon.co.jp/rank/js/d/2030-05-09/";
    private String falseWeeklyUrl = "https://www.oricon.co.jp/rank/js/w/2018-05-06/";
    private String falseMonthlyUrl = "https://www.oricon.co.jp/rank/js/m/2030-04/";
    private String falseYearlyUrl = "https://www.oricon.co.jp/rank/js/y/2030/";

    private String sampleDailyOutput = "Ryokai desu~ This is your top 10 songs as you "
            + "requested~\n" + "\n(1) 進化理論 - BOYS AND MEN - 2018-05-09\n"
            + "(2) シンクロニシティ - 乃木坂46 - 2018-04-25\n"
            + "(3) Eclipse - 蒼井翔太 - 2018-05-09\n"
            + "(4) この道を/会いに行く/坂道を上って/小さな風景 - 小田和正 - 2018-05-02"
            + "(5) The IDOLM@STER SideM WORLD TRE@SURE 01 - \n"
            + "天道輝(仲村宗悟),葛之葉雨彦(笠間淳),握野英雄(熊谷健太郎),紅井朱雀(益山武明) - \n"
            + "2018-05-09\n"
            + "(6) 誓い - 雨宮天 - 2018-05-09\n"
            + "(7) アップデート - miwa - 2018-05-09\n"
            + "(8) 泣けないぜ…共感詐欺/Uraha=Lover/君だけじゃないさ...friends"
            + "(2018アコースティックVer.)(初回生産限定盤A) - アンジュルム - 2018-05-09\n"
            + "(9) 泡沫夢幻・胡蝶刃 ～GRANBLUE FANTASY～ - ナルメア(M・A・O) - "
            + "2018-05-02\n"
            + "(10) Crosswalk/リワインド - 鈴木みのり - 2018-05-09";

    private String sampleWeeklyOutput = "Ryokai desu~ This is your top 10 songs as you "
            + "requested~\n" + "\n(1) シンクロニシティ - 乃木坂46 - 2018-04-25\n"
            + "(2) Fandango - THE RAMPAGE from EXILE TRIBE - 2018-04-25\n"
            + "(3) Fiction e.p - sumika - 2018-04-25\n"
            + "(4) Bumblebee - Lead - 2018-04-25\n"
            + "(5) 人間を被る - DIR EN GREY - 2018-04-25\n"
            + "(6) 泣きたいくらい - 大原櫻子 - 2018-04-25\n"
            + "(7) THE IDOLM@STER MILLION THE@TER GENERATION 07 トゥインクルリズム"
            + "(ZETTAI × BREAK!! トゥインクルリズム) - トゥインクルリズム[中谷育(原嶋あかり),"
            + "七尾百合子(伊藤美来),松田亜利沙(村川梨衣)] - 2018-04-25\n"
            + "(8) 春はどこから来るのか? - NGT48 - 2018-04-11\n"
            + "(9) Ask Youself - KAT-TUN - 2018-04-18\n"
            + "(10) 鍵穴 - the Raid. - 2018-04-25";

    private String sampleMonthlyOutput = "Ryokai desu~ This is your top 10 songs as you "
            + "requested~\n" + "\n(1) シンクロニシティ - 乃木坂46 - 2018-04-25\n"
            + "(2) 早送りカレンダー - HKT48 - 2018-05-02\n"
            + "(3) Ask Youself - KAT-TUN - 2018-04-18\n"
            + "(4) 春はどこから来るのか? - NGT48 - 2018-04-11\n"
            + "(5) 君のAchoo! - ラストアイドル(シュークリームロケッツ) - 2018-04-18\n"
            + "(6) SEXY SEXY/泣いていいよ/Vivid Midnight - Juice=Juice - 2018-04-18\n"
            + "(7) ガラスを割れ! - 欅坂46 - 2018-03-07\n"
            + "(8) ONE TIMES ONE - コブクロ - 2018-04-11\n"
            + "(9) ODD FUTURE - UVERworld - 2018-05-02\n"
            + "(10) Shanana ここにおいで - B2takes! - 2018-04-11";

    private String sampleYearlyOutput = "Ryokai desu~ This is your top 10 songs as you "
            + "requested~\n" + "\n(1) 願いごとの持ち腐れ - AKB48 - 2017-05-31\n"
            + "(2) #好きなんだ - AKB48 - 2017-08-30\n"
            + "(3) 11月のアンクレット - AKB48 - 2017-11-22\n"
            + "(4) シュートサイン - AKB48 - 2017-03-15\n"
            + "(5) 逃げ水 - 乃木坂46 - 2017-08-09\n"
            + "(6) インフルエンサー - 乃木坂46 - 2017-03-22\n"
            + "(7) いつかできるから今日できる - 乃木坂46 - 2017-10-11\n"
            + "(8) 不協和音 - 乃木坂46 - 2017-04-05\n"
            + "(9) 風に吹かれても - 乃木坂46 - 2017-10-25\n"
            + "(10) Doors ～勇気の軌跡～ - 嵐 - 2017-11-08";

    private String output;
    private String errorMessage = chartSingle.scrapChart("https://www.oricon.co.jp/rank/js/w/2018-05-06/");

    public chartSingleTest() throws IOException {

    }

    @Test
    public void testScrapDaily() throws IOException {
        output = chartSingle.scrapChart(sampleDailyUrl);
        assertEquals(sampleDailyOutput,output);
    }

    @Test
    public void testScrapWeekly() throws IOException {
        output = chartSingle.scrapChart(sampleWeeklyUrl);
        assertEquals(sampleWeeklyOutput, output);
    }

    @Test
    public void testScrapMonthly() throws IOException {
        output = chartSingle.scrapChart(sampleMonthlyUrl);
        assertEquals(sampleMonthlyOutput, output);
    }

    @Test
    public void testScrapYearly() throws IOException {
        output = chartSingle.scrapChart(sampleYearlyUrl);
        assertEquals(sampleYearlyOutput, output);
    }

    @Test
    public void testFalseScrapDaily() throws IOException {
        output = chartSingle.scrapChart(falseDailyUrl);
        assertEquals(errorMessage, output);
    }

    @Test
    public void testFalseScrapWeekly() throws IOException {
        output = chartSingle.scrapChart(falseWeeklyUrl);
        assertEquals(errorMessage, output);
    }

    @Test
    public void testFalseScrapMonthly() throws IOException {
        output = chartSingle.scrapChart(falseMonthlyUrl);
        assertEquals(errorMessage, output);
    }

    @Test
    public void testFalseScrapYearly() throws IOException {
        output = chartSingle.scrapChart(falseYearlyUrl);
        assertEquals(errorMessage, output);
    }
}
