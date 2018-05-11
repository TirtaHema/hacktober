package advprog.oriconSingle.util.commands;

import advprog.oriconSingle.util.service.scrapper.ChartSingle;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class YearlyChartCommand implements ChartCommand {

    private ChartSingle chartSingle = new ChartSingle();

    private static final String YEARLY_URL = "https://www.oricon.co.jp/rank/js/y/";

    @Override
    public TextMessage execute(String date) throws IOException {
        String chartList = chartSingle.scrapChart(YEARLY_URL + date + "/");

        chartList = chartList.contains("Not a valid URL")
                ? "Sorry-masen! There's no chart on that year"
                : chartList;
        return new TextMessage(chartList);
    }
}
