package advprog.oriconbluray.util;

import java.io.IOException;
import java.util.stream.Collectors;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class RankScrapper {

    public String scrapRank(String url) throws IOException {
        try {
            Document docs = Jsoup.connect(url).get();
            Elements blurays = docs.select("section.box-rank-entry");
            String output = "Haiiii~ Here's your top 10 bluray as you requested~\n\n";
            output += blurays.stream().map(bd -> scrapRank(bd))
                    .collect(Collectors.joining("\n"));
            return output;
        } catch (HttpStatusException e) {
            return "Not a valid URL, please use a proper Oricon link";
        }
    }

    private String scrapRank(Element movieHtml) {
        String rank = movieHtml.select("p.num").text();
        Element info = movieHtml.selectFirst("div.wrap-text");
        String title = info.select("h2.title").text();
        String artist = info.select("p.name").text();
        String date = cleanedDate(info.selectFirst("li").text());
        return String.format("(%s) %s - %s - %s",
                rank, title, artist, date);
    }

    private String cleanedDate(String date) {
        return date.replace("発売日： ", "")
                .replace("年", "-")
                .replace("月", "-")
                .replace("日", "");
    }


}
