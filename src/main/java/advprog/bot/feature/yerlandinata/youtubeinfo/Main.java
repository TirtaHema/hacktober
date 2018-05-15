package advprog.bot.feature.yerlandinata.youtubeinfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println(" ss s ");
        System.out.println(" ss s ".trim());

        Pattern pattern = Pattern.compile("((https?:\\/\\/)?youtube\\.com)");

        Matcher m = pattern.matcher("youtube.com");
        m.find();
        System.out.println(m.group());

        m = pattern.matcher("http://youtube.com");
        m.find();
        System.out.println(m.group());

        m = pattern.matcher("https://youtube.com");
        m.find();
        System.out.println(m.group());

//        HttpUrl httpUrl = HttpUrl.parse("https://www.youtube.com/watch?v=kJ5PCbtiCpk");
//        System.out.println(httpUrl.host());
//        System.out.println(httpUrl.queryParameter("v"));
//        System.out.println(httpUrl.encodedPath().equals("/watch"));
    }
}
