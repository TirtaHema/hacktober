package advprog.bot.line;

import static org.junit.Assert.assertEquals;

import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;

import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BaseChatHandlerTest {


    @Test
    public void testHandlers() {
        BaseChatHandler baseChatHandler = new BaseChatHandler();
        assertEquals(new LinkedList<TextMessage>(),
                baseChatHandler.handleTextMessageEvent(null, new LinkedList<>()));
        assertEquals(new LinkedList<ImageMessage>(),
                baseChatHandler.handleImageMessageEvent(null, new LinkedList<>()));
        assertEquals(new LinkedList<AudioMessage>(),
                baseChatHandler.handleAudioMessageEvent(null, new LinkedList<>()));
        assertEquals(new LinkedList<StickerMessage>(),
                baseChatHandler.handleStickerMessageEvent(null, new LinkedList<>()));
        assertEquals(new LinkedList<LocationMessage>(),
                baseChatHandler.handleLocationMessageEvent(null, new LinkedList<>()));
    }

}
