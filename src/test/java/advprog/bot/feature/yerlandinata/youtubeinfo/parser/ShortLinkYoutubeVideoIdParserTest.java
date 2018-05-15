package advprog.bot.feature.yerlandinata.youtubeinfo.parser;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShortLinkYoutubeVideoIdParserTest extends YoutubeVideoIdParserTest {

    @Before
    public void setUp() {
        this.parser = new ShortLinkYoutubeVideoIdParser();
    }

    @Test
    public void testParseIdCorrectUrl() {
        String id = "id";
        List<String> correctUrls = new ArrayList<>(2);
        correctUrls.add("https://youtu.be/" + id);
        correctUrls.add("http://youtu.be/" + id);
        correctUrls.forEach(c -> testParseIdCorrectUrl(c, id));
    }

    @Test(expected = InvalidYoutubeVideoUrl.class)
    public void testParseIdIncorrectUrlNoNext() {
        testParseIdIncorrectUrlNoNext("http://invalid_url");
    }

    @Test
    public void testParseIdIncorrectUrlHasNext() {
        testParseIdIncorrectUrlHasNext("http://invalid_url");
    }

}
