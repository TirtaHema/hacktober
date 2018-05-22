package advprog.bot.feature.vgmdb;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CurrencyConverterHelper {

    public static String convert(String from, String amount) throws IOException {
        Document resp = Jsoup.connect("https://www.xe.com/currencyconverter/convert/?"
                + "Amount=" + amount + "&From=" + from + "&To=IDR").get();
        String result = resp.select(".uccResultAmount").text();
        return result;

    }
}
