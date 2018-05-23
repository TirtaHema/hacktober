package advprog.bot.feature.helper;

import static org.junit.Assert.assertFalse;


import advprog.bot.feature.zonk.ZonkChatHandler;
import advprog.bot.line.BaseChatHandler;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZonkChatHandlerTest {

    ZonkChatHandler zonkChatHandler;

    @Before
    public void setUp() {
        zonkChatHandler = new ZonkChatHandler(new BaseChatHandler());
    }


    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(zonkChatHandler.canHandleAudioMessage(null));
        assertFalse(zonkChatHandler.canHandleImageMessage(null));
        assertFalse(zonkChatHandler.canHandleStickerMessage(null));
        assertFalse(zonkChatHandler.canHandleLocationMessage(null));
    }

}
