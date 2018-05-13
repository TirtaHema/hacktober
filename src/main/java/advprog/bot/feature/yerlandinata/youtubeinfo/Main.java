package advprog.bot.feature.yerlandinata.youtubeinfo;

import okhttp3.HttpUrl;

public class Main {
    public static void main(String[] args) {
        HttpUrl httpUrl = HttpUrl.parse("https://www.youtube.com/watch?v=kJ5PCbtiCpk");
        System.out.println(httpUrl.host());
        System.out.println(httpUrl.queryParameter("v"));
        System.out.println(httpUrl.encodedPath().equals("/watch"));
    }
}
