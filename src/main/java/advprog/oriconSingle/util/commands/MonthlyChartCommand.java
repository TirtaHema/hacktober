package advprog.oriconSingle.util.commands;

import advprog.oriconSingle.util.service.scrapper.ChartSingle;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class MonthlyChartCommand implements ChartCommand {

    private ChartSingle chartSingle = new ChartSingle();

    private static final String MONTHLY_URL = "https://www.oricon.co.jp/rank/js/m/";

    @Override
    public TextMessage execute(String date) throws IOException {
        String chartList = chartSingle.scrapChart(MONTHLY_URL + date + "/");

        chartList = chartList.contains("Not A valid URL")
                ? "Sorry-masen! There's no chart on that month"
                : chartList;
        return new TextMessage(chartList);
    }

}
