package advprog.example.bot.billboardcharts100;

import java.io.IOException;
import java.net.SocketTimeoutException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Hot100 {

    public String getChart() {
        String concate = "";
        try {
            String url = "https://www.billboard.com/charts/hot-100";
            Document document = Jsoup.connect(url).timeout(5000).get();
            Elements link = document.getElementsByClass("chart-row");

            for (int i = 0; i < 10; i++) {
                concate += "(" + (i + 1) + ") ";
                concate += link.get(i).getElementsByClass("chart-row__song").html()
                        .replaceAll("&amp;","&") + " - ";
                concate += link.get(i).getElementsByClass("chart-row__artist").html()
                        .replaceAll("&amp;","&") + "\n";
            }

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return concate;
    }
}
