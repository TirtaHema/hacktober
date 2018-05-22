package advprog.bot.feature.vgmdb;

import static advprog.bot.feature.vgmdb.CurrencyConverterHelper.convert;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;





@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterHelperTest {

    @Test
    public void convertTest() throws IOException {
        Document resp = Jsoup.connect("https://www.xe.com/currencyconverter/convert/?"
                + "Amount=" + 5 + "&From=" + "USD" + "&To=IDR").get();
        String result = resp.select(".uccResultAmount").text();
        assertEquals(result, convert("USD","5"));
    }

}