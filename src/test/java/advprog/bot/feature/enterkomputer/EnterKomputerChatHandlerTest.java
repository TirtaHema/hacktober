package advprog.bot.feature.enterkomputer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.feature.enterkomputer.EnterKomputerChatHandler;
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
public class EnterKomputerChatHandlerTest {
    EnterKomputerChatHandler enterkomputerChatHandler;

    @Before
    public void setUp() {
        enterkomputerChatHandler = new EnterKomputerChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEvent() {
        String msg = "/billboard japan100";
        List<Message> messages = new LinkedList<>();
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
            "dsf", msg
        );
        assertNotNull(enterkomputerChatHandler.handleTextMessageEvent(me, messages));
    }
}
