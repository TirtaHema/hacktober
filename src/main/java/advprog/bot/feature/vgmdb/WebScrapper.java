package advprog.bot.feature.vgmdb;


import static advprog.bot.feature.vgmdb.CurrencyConverterHelper.convert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WebScrapper {

    public static final String KEYWORD_ORIGINAL1 = "Original";
    public static final String KEYWORD_ORIGINAL2 = "ORIGINAL";
    public static final String KEYWORD_ORIGINAL3 = "original";


    public static List<String> getData(){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://vgmdb.net/db/calendar.php?year=2018&month=5").timeout(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements container = doc.select(".album_infobit_detail");
        List<String> result = new ArrayList<String>();
        String hasil = "";
        for(int i = 0; i < container.size(); i++){
            String[] listTitle = container.get(i).select("a.albumtitle.album-game").text().split(" /");
            String title = listTitle[0];
            if(title.contains(KEYWORD_ORIGINAL1) || title.contains(KEYWORD_ORIGINAL2)
                        || title.contains(KEYWORD_ORIGINAL3)){
                    String[] listPrice = container.get(i).select("li").get(1).text().split(" ");
                    String price = listPrice[2];
                    String currency = listPrice[3];
                String priceIDR = null;
                try {
                    priceIDR = convert(currency,price);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                hasil += title + " : " + priceIDR + " IDR";
                    result.add(hasil);
            }
            hasil = "";
        }
        return result;
    }
}
