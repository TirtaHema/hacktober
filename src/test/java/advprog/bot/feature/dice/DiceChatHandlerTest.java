package advprog.bot.feature.dice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;



public class DiceChatHandlerTest {

    private DiceChatHandler diceChatHandler;

    public DiceChatHandlerTest() {
        this.diceChatHandler = new DiceChatHandler();
    }

    @Test
    public void testCanHandleTextMessage() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/roll 5d6");

        assertTrue(diceChatHandler.canHandleTextMessage(event));
    }

    @Test
    public void testHandleTextMessageError() {
        List<TextMessage> expectedMessages = new LinkedList<>();
        TextMessage expected = new TextMessage("Please follow the given format\n"
                + "/coin\n/roll XdY\n/multiroll N XdY\n/is_lucky NUM XdY");
        expectedMessages.add(expected);

        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/roll 5d6 asd");

        List<Message> reply = diceChatHandler.handleTextMessage(event);

        assertEquals(expectedMessages, reply);
    }

    @Test
    public void testDiceIterator() {
        int[] arr = {1,2,3,4,5};
        String result = diceChatHandler.diceIterator(arr);

        String expected = "(1, 2, 3, 4, 5)";

        assertEquals(expected, result);
    }

    @Test
    public void testHandleRoll() {
        String result = diceChatHandler.handleRoll(0, 0);
        String expected = "Result: 0d0 ()";

        assertEquals(expected, result);
    }

    @Test
    public void testHandleMultiRoll() {
        String result = diceChatHandler.handleMultiRoll(0, 0, 0);
        String expected = "Please follow the format /multiroll NUM xdy, with NUM, x, y > 0";

        assertEquals(expected, result);
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
