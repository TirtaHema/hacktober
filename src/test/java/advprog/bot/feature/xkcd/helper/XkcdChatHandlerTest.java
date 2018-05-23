package advprog.bot.feature.xkcd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class XkcdChatHandlerTest {

    XkcdChatHandler xkcdChatHandler;

    @Before
    public void setUp() {
        xkcdChatHandler = new XkcdChatHandler(new BaseChatHandler());
    }


    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(xkcdChatHandler.canHandleAudioMessage(null));
        assertFalse(xkcdChatHandler.canHandleImageMessage(null));
        assertFalse(xkcdChatHandler.canHandleStickerMessage(null));
        assertFalse(xkcdChatHandler.canHandleLocationMessage(null));
    }

}
