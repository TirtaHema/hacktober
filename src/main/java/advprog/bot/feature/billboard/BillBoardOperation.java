package advprog.bot.feature.billboard;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class BillBoardOperation {
    private ArrayList<String>  top100Artist = new ArrayList<>();
    private ArrayList<String> top100Song = new ArrayList<>();

    public BillBoardOperation() {
        Document doc = getHtml("https://www.billboard.com/charts/japan-hot-100");
        Elements artists = doc.select("article.chart-row");
        for (Element artist: artists) {
            String song = artist.select("h2.chart-row__song").text();
            String artisA = artist.select("a.chart-row__artist").text();
            String artisSpan = artist.select("span.chart-row__artist").text();
            if (artisA.equals("")) {
                top100Artist.add(artisSpan.toLowerCase());
            } else {
                top100Artist.add(artisA.toLowerCase());
            }
            top100Song.add(song);
        }
    }

    public ArrayList<String> getArrayArtist() {
        return top100Artist;
    }

    public ArrayList<String> getArraySong() {
        return top100Song;
    }

    public Document getHtml(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            doc = null;
        }
        return doc;
    }


}
