package advprog.example.bot.CountryBot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class CountrySongChartBot {

    public List<CountrySong> chart;
    public String artist;
    public String url;

    public CountrySongChartBot() {
        this.url = "https://www.billboard.com/charts/country-songs";
        this.chart = new ArrayList<>();
        this.getChart();
    }

    public CountrySongChartBot(String artis){
        this.url = "https://www.billboard.com/charts/country-songs";
        this.chart = new ArrayList<>();
        this.getChart();
        this.artist = artis;
    }

    public List<CountrySong> getChart(){
        try {
            Document doc = Jsoup.connect(url).get();
            Elements data = doc.getElementsByClass("chart-row");
            for (int i = 0; i < 50; i++) {
                Element elem = data.get(i);
                String title = elem.getElementsByClass("chart-row__song").html();
                String artist = elem.getElementsByClass("chart-row__artist").html();

                String newTitle = Parser.unescapeEntities(title, false);
                String newArtist = Parser.unescapeEntities(artist, false);

                CountrySong alb = new CountrySong(newTitle, newArtist, i + 1);
                this.chart.add(alb);
            }
        } catch (IOException e) {
            System.out.println("IO EXCEPTION");
        }
        return this.chart;
    }

    public boolean isExist(String find){
        for(CountrySong song : chart){
            if (song.getArtist().toLowerCase().contains(find.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public String FavoriteArtist() {
        StringBuilder sb = new StringBuilder();
        if (isExist(this.artist)) {
            for (CountrySong song : chart) {
                if (song.getArtist().toLowerCase().contains(this.artist.toLowerCase())) {
                    if (sb.toString().length() == 0) {
                        sb.append(song.song());
                    } else {
                        sb.append("\n\n" + song.song());
                    }
                }
            }
        }
        if (sb.toString().length() > 0) {
            return sb.toString();
        }
        return "SORRY! Your favorite artist is not on the list!";
    }

    public String getUrl() {
        return this.url;
    }
}
