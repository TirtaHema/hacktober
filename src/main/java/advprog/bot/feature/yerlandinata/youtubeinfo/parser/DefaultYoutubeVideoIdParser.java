package advprog.bot.feature.yerlandinata.youtubeinfo.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultYoutubeVideoIdParser extends AbstractYoutubeVideoIdParser {

    private static final Pattern URL_PATTERN = Pattern.compile(
            "((https?://)?(www\\.)?youtube\\.com/watch)"
    );

    @Override
    protected String parseIdFromUrl(String url) throws InvalidYoutubeVideoUrl {
        Matcher matcher = URL_PATTERN.matcher(url);
        if (!matcher.find()) {
            throw new InvalidYoutubeVideoUrl(url);
        }
        String nonId = matcher.group();
        return url.replace(nonId, "").replace("?v=", "");
    }
}
