package advprog.oriconSingle.util.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Test;

public class DailyChartCommandTest {

    private String sampleDate = "2018-05-08";
    private String falseDate = "2030-05-08";

    private ChartCommand command = new DailyChartCommand();
    private TextMessage message;

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

    @Test
    public void testValidDailyChart() throws IOException {
        message = command.execute(sampleDate);
        assertEquals(message, sampleDailyOutput);
    }

    @Test
    public void testInvalidDailyChart() throws IOException {
        message = command.execute(falseDate);
        assertEquals(message, "Sorry-masen! There's no chart on that date");
    }
}
