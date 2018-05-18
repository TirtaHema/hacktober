package advprog.bot.feature.itunes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.feature.itunes.iTunesChatHandler;
import advprog.bot.line.BaseChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class iTunesChatHandlerTest {
    iTunesChatHandler itunesChatHandler;

    @Before
    public void setUp() {
        itunesChatHandler = new iTunesChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEvent() {
        String msg = "/billboard japan100";
        List<Message> messages = new LinkedList<>();
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
            "dsf", msg
        );
        assertNotNull(itunesChatHandler.handleTextMessageEvent(me, messages));
    }
}
