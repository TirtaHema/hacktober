package advprog.bot.feature.yerlandinata.youtubeinfo.parser;

public abstract class AbstractYoutubeVideoIdParser {

    private AbstractYoutubeVideoIdParser nextParser;

    public void setNextParser(AbstractYoutubeVideoIdParser nextParser) {
        this.nextParser = nextParser;
    }

    public String parseYoutubeVideoId(String videoUrl) {
        try {
            return parseIdFromUrl(videoUrl);
        } catch (InvalidYoutubeVideoUrl e) {
            if (nextParser != null) {
                return nextParser.parseYoutubeVideoId(videoUrl);
            } else {
                throw e;
            }
        }
    }

    protected abstract String parseIdFromUrl(String url) throws InvalidYoutubeVideoUrl;

}
