package advprog.oriconSingle.util.commands;

import advprog.oriconSingle.util.service.scrapper.ChartSingle;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class DailyChartCommand implements ChartCommand {

    private ChartSingle chartSingle = new ChartSingle();

    private static final String DAILY_URL = "https://www.oricon.co.jp/rank/js/d/";

    @Override
    public TextMessage execute(String date) throws IOException {
        String chartList = chartSingle.scrapChart(DAILY_URL + date + "/");

        chartList = chartList.contains("Not A valid URL")
                ? "Sorry-masen! There's no chart on that date"
                : chartList;
        return new TextMessage(chartList);
    }
}
