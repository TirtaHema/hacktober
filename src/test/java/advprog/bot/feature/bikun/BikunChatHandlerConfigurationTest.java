package advprog.bot.feature.bikun;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BikunChatHandlerConfigurationTest {

    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        BikunChatHandlerConfiguration configuration = new BikunChatHandlerConfiguration();
        BikunChatHandler bikunChatHandler = configuration.bikunChatHandler(controller);
        assertEquals(decoratedHandler, bikunChatHandler.getDecoratedLineChatHandler());
    }
}
