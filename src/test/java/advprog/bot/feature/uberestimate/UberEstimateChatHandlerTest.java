package advprog.bot.feature.uberestimate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

public class UberEstimateChatHandlerTest {
    UberEstimateChatHandler uberEstimateChatHandler;

    @Before
    public void setUp() {
        uberEstimateChatHandler = new UberEstimateChatHandler(new BaseChatHandler());
    }

    @Test
    public void testEventHandler() {
        assertFalse(uberEstimateChatHandler.canHandleAudioMessage(null));
        assertFalse(uberEstimateChatHandler.canHandleImageMessage(null));
        assertFalse(uberEstimateChatHandler.canHandleStickerMessage(null));
        assertTrue(uberEstimateChatHandler.canHandleLocationMessage(null));
        assertTrue(uberEstimateChatHandler.canHandleTextMessage(null));
    }

}
