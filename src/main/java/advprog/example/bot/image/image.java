package advprog.example.bot.image;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.net.URL;
import java.util.List;

public class Image {

    private static String URL = "https://xkcd.com/";


    public static String gambar(String input) {
        URL += input ;
        return URL;
    }
    private static proses (){
        WebClient webClient = new WebClient();
        HtmlPage currentPage = (HtmlPage) webClient.getPage(new URL(URL));
        final List<?> images = currentPage.getByXPath("//img");
        for (Object imageObject : images) {
            HtmlImage image = (HtmlImage) imageObject;
            System.out.println(image.getSrcAttribute());
        }
    }
}
