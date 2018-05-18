package advprog.bot.feature.itunes;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import advprog.bot.BotController;
import advprog.bot.feature.itunes.iTunesChatHandler;
import advprog.bot.feature.itunes.iTunesChatHandlerConfiguration;
import advprog.bot.line.LineChatHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class iTunesChatHandlerConfigurationTest {
    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        iTunesChatHandlerConfiguration configuration = new iTunesChatHandlerConfiguration();
        iTunesChatHandler itunesChatHandler = configuration.billboardChatHandler(controller);
        assertEquals(decoratedHandler, itunesChatHandler.getDecoratedLineChatHandler());
    }
}
