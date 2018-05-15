package advprog.bot.feature.yerlandinata.youtubeinfo.parser;

@SuppressWarnings("serial")
public class InvalidYoutubeVideoUrl extends RuntimeException {

    public InvalidYoutubeVideoUrl(String url) {
        super(url + " is not a valid youtube video url");
    }

}
