package advprog.oriconSingle.util;

import java.io.IOException;
import java.util.stream.Collectors;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class chartSingle {

    public String scrapChart(String url) throws IOException {
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("section.box-rank-entry");
            String output = "Ryokai desu~ This is your top 10 songs as you "
                    + "requested\n\n";
            output += elements.stream().map(js -> scrapChartElement(js))
                    .collect(Collectors.joining("\n"));
            return output;
        } catch (HttpStatusException e) {
            return "Not a valid URL, please use a proper Oricon link";
        }
    }

    private String scrapChartElement(Element song) {
        String rank = song.select("p.num").text();
        Element info = song.selectFirst("div.wrap-text");
        String title = info.select("h2.title").text();
        String artist = info.select("p.name").text();
        String date = FormatDate(info.selectFirst("li").text());
        return String.format("(%s) %s - %s - %s", rank, title, artist, date);
    }

    private String FormatDate(String date) {
        return date.replace("発売日： ", "")
                .replace("年", "-")
                .replace("月", "-")
                .replace("日", "");

    }
}
