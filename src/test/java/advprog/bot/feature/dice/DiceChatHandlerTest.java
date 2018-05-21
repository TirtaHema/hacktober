package advprog.bot.feature.dice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;


public class DiceChatHandlerTest {

    private DiceChatHandler diceChatHandler;

    public DiceChatHandlerTest() {
        this.diceChatHandler = new DiceChatHandler();
    }

    @Test
    public void testHandleIsLuckyFail() {
        String result = diceChatHandler.handleIsLucky(0,0,0);
        System.out.println(result);
        String expected = "Please input using the correct format type "
                + "\"is_lucky NUM XdY\" with NUM, x, y > 0";

        assertEquals(expected, result);
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(diceChatHandler.canHandleAudioMessage(null));
        assertFalse(diceChatHandler.canHandleImageMessage(null));
        assertFalse(diceChatHandler.canHandleStickerMessage(null));
        assertFalse(diceChatHandler.canHandleLocationMessage(null));
    }
}
