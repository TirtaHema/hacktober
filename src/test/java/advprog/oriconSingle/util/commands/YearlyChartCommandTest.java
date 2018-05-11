package advprog.oriconSingle.util.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Test;

public class YearlyChartCommandTest {

    private String sampleYear = "2017";
    private String falseYear = "2030";

    private ChartCommand command = new YearlyChartCommand();
    private TextMessage message;

    private String sampleYearlyOutput = "Ryokai desu~ This is your top 10 songs as you "
            + "requested~\n" + "\n(1) 願いごとの持ち腐れ - AKB48 - 2017-05-31\n"
            + "(2) #好きなんだ - AKB48 - 2017-08-30\n"
            + "(3) 11月のアンクレット - AKB48 - 2017-11-22\n"
            + "(4) シュートサイン - AKB48 - 2017-03-15\n"
            + "(5) 逃げ水 - 乃木坂46 - 2017-08-09\n"
            + "(6) インフルエンサー - 乃木坂46 - 2017-03-22\n"
            + "(7) いつかできるから今日できる - 乃木坂46 - 2017-10-11\n"
            + "(8) 不協和音 - 欅坂46 - 2017-04-05\n"
            + "(9) 風に吹かれても - 欅坂46 - 2017-10-25\n"
            + "(10) Doors 〜勇気の軌跡〜 - 嵐 - 2017-11-08";

    @Test
    public void testValidYearlyChart() throws IOException {
        message = command.execute(sampleYear);
        assertEquals(message.getText(), sampleYearlyOutput);
    }

    @Test
    public void testInvalidYearlyChart() throws IOException {
        message = command.execute(falseYear);
        assertEquals(message.getText(), "Sorry-masen! There's no chart on that year");
    }
}
