package advprog.bot.feature.anisong.handler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AnisongChatHandlerConfigTest {

    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        AnisongChatHandlerConfig config = new AnisongChatHandlerConfig();
        AnisongChatHandler handler = config.anisongChatHandler(controller);
        assertEquals(decoratedHandler, handler.getDecoratedLineChatHandler());
    }
}

