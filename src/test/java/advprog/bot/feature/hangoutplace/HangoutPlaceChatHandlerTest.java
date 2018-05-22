package advprog.bot.feature.hangoutplace;

/**
 * Created by fazasaffanah on 22/05/2018.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
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

@RunWith(MockitoJUnitRunner.class)
public class HangoutPlaceChatHandlerTest {

    HangoutPlaceChatHandler hangoutPlaceChatHandler;

    @Before
    public void setUp() {
        hangoutPlaceChatHandler = new HangoutPlaceChatHandler(new BaseChatHandler());
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(hangoutPlaceChatHandler.canHandleAudioMessage(null));
        assertFalse(hangoutPlaceChatHandler.canHandleImageMessage(null));
        assertFalse(hangoutPlaceChatHandler.canHandleStickerMessage(null));
    }

}
