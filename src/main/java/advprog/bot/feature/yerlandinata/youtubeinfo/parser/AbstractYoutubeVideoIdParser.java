package advprog.bot.feature.yerlandinata.youtubeinfo.parser;

public abstract class AbstractYoutubeVideoIdParser {

    protected AbstractYoutubeVideoIdParser nextParser;

    public String parseYoutubeVideoId(String videoUrl) {
        try {
            return parseIdFromUrl(videoUrl);
        } catch (InvalidYoutubeVideoUrl e) {
            if (nextParser != null) {
                return nextParser.parseIdFromUrl(videoUrl);
            } else {
                throw e;
            }
        }
    }

    protected abstract String parseIdFromUrl(String url) throws InvalidYoutubeVideoUrl;

}
