package advprog.oriconSingle.util.commands;

import advprog.oriconSingle.util.service.scrapper.ChartSingle;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class WeeklyChartCommand implements ChartCommand {

    private ChartSingle chartSingle = new ChartSingle();

    private static final String WEEKLY_URL = "https://www.oricon.co.jp/rank/js/w/";

    @Override
    public TextMessage execute(String date) throws IOException {
        String chartList = chartSingle.scrapChart(WEEKLY_URL+ date + "/");

        chartList = chartList.contains("Not A valid URL")
                ? "There's no chart on that date"
                + ". Make sure the date you assing in on monday"
                : chartList;
        return new TextMessage(chartList);
    }
}
