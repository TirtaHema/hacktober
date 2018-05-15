package advprog.bot.feature.yerlandinata.youtubeinfo.parser;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultYoutubeVideoIdParserTest extends YoutubeVideoIdParserTest{

    @Before
    public void setUp() {
        this.parser = new DefaultYoutubeVideoIdParser();
    }

    @Test
    public void testParseIdCorrectUrl() {
        String id = "id";
        List<String> correctUrls = new ArrayList<>(3);
        correctUrls.add("https://youtube.com/watch?v=" + id);
        correctUrls.add("https://www.youtube.com/watch?v=" + id);
        correctUrls.add("http://youtube.com/watch?v=" + id);
        correctUrls.add("http://www.youtube.com/watch?v=" + id);
        correctUrls.add("youtube.com/watch?v=" + id);
        correctUrls.add("www.youtube.com/watch?v=" + id);
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
