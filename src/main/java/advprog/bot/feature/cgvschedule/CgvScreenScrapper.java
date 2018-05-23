package advprog.bot.feature.cgvschedule;

import java.util.Collections;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CgvScreenScrapper {

    private static String url = "https://www.cgv.id/en/schedule/cinema/";

    public static String screenScrapper(String studio) throws Exception {
        final Document document = Jsoup.connect(url).get();
        String output = "";

        for (Element row : document.select("div.schedule-lists li")) {
            String title = row.select(".schedule-title a").text();
            String audio;
            LinkedList<String> showTime;


            for (Element schedule : row.select("li.schedule-type")) {
                audio = schedule.text();
                if (!audio.contains(studio)) {
                    continue;
                }
                showTime = new LinkedList<>();

                for (Element time : schedule.nextElementSibling().select("li ul li")) {
                    if (!showTime.contains(time.text())) {
                        showTime.add(time.text());
                    }
                }

                if (title.length() > 1 && showTime.size() != 0) {
                    output = output + "('" + title + "', " + printShowTime(showTime) + ")\n";
                }
            }
        }

        if (output.length() > 1) {
            output = output.substring(0, output.length() - 1);
        }

        return output;
    }

    private static String printShowTime(LinkedList<String> showTime) {
        String output = "[";
        Collections.sort(showTime);

        for (String time : showTime) {
            output = output + time + ", ";
        }

        output = output.substring(0, output.length() - 2) + "]";

        return output;
    }

    public static void setUrl(String url) throws Exception {
        if (!url.contains("www.cgv.id/en/schedule/cinema/")) {
            throw new Exception();
        }

        Jsoup.connect(url).get();
        CgvScreenScrapper.url = url;
    }
}
