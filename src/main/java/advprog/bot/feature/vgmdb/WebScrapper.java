package advprog.bot.feature.vgmdb;

import static advprog.bot.feature.vgmdb.CurrencyConverterHelper.convert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class WebScrapper {

    public static final String KEYWORD_ORIGINAL1 = "Original";
    public static final String KEYWORD_ORIGINAL2 = "ORIGINAL";
    public static final String KEYWORD_ORIGINAL3 = "original";

    public static List<String> getData() throws IOException {
        Calendar now = Calendar.getInstance();
        Document doc = Jsoup.connect("https://vgmdb.net/db/calendar.php?"
                + "year=" + now.get(Calendar.YEAR)
                + "&month=" + (now.get(Calendar.MONTH) + 1)).timeout(0).get();
        Elements container = doc.select(".album_infobit_detail");
        List<String> result = new ArrayList<String>();
        String hasil = "";
        for (int i = 0; i < container.size(); i++) {
            String[] listTitle = container.get(i).select("a.albumtitle.album-game")
                    .text().split(" /");
            String title = listTitle[0];
            if (title.contains(KEYWORD_ORIGINAL1) || title.contains(KEYWORD_ORIGINAL2)
                        || title.contains(KEYWORD_ORIGINAL3)) {
                String[] listPrice = container.get(i).select("li").get(1).text().split(" ");
                String price = listPrice[2];
                String currency = listPrice[3];
                String priceIdr = convert(currency,price);
                hasil += title + " : " + priceIdr + " IDR";
                result.add(hasil);
            }
            hasil = "";
        }
        return result;
    }
}
