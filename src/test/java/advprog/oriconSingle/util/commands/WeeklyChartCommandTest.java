package advprog.oriconSingle.util.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Test;

public class WeeklyChartCommandTest {

    private String sampleDate = "2018-05-07";
    private String falseDate = "2018-05-06";

    private ChartCommand command = new WeeklyChartCommand();
    private TextMessage message;

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
            + "(9) Ask Yourself - KAT-TUN - 2018-04-18\n"
            + "(10) 鍵穴 - the Raid. - 2018-04-25";

    @Test
    public void testValidWeeklyChart() throws IOException {
        message = command.execute(sampleDate);
        assertEquals(message.getText(), sampleWeeklyOutput);
    }

    @Test
    public void testInvalidWeeklyChart() throws IOException {
        message = command.execute(falseDate);
        assertEquals(message.getText(), "Sorry-masen! There's no chart on that date"
                + ". Make sure the date you assign in on monday");
    }
}
