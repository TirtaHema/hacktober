package advprog.bot.feature.top5poster;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

import advprog.bot.line.LineChatHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Top5PosterHandlerTest {

    Top5PosterHandler handler;

    @Before
    public void setUp() {
        handler = new Top5PosterHandler(mock(LineChatHandler.class),
                mock(Top5PosterService.class));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        Poster p = new Poster("", 0);
        p.hashCode();
        p.getName();
        p.getPercentage();
        p.toString();
        assertFalse(handler.canHandleAudioMessage(null));
        assertFalse(handler.canHandleImageMessage(null));
        assertFalse(handler.canHandleStickerMessage(null));
        assertFalse(handler.canHandleLocationMessage(null));
    }


}
