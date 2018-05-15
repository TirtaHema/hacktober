package advprog.bot.feature.yerlandinata.youtubeinfo.parser;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class YoutubeVideoIdParserTest {

    protected AbstractYoutubeVideoIdParser parser;

    protected void testParseIdCorrectUrl(String url, String expectedId) {
        assertEquals(expectedId, parser.parseYoutubeVideoId(url));
    }

    protected void testParseIdIncorrectUrlNoNext(String url) {
        parser.parseYoutubeVideoId(url);
    }

    protected void testParseIdIncorrectUrlHasNext(String url) {
        AbstractYoutubeVideoIdParser nextParser = mock(AbstractYoutubeVideoIdParser.class);
        parser.setNextParser(nextParser);
        parser.parseYoutubeVideoId(url);
        verify(nextParser).parseYoutubeVideoId(eq(url));
    }

}
