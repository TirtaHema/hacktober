package advprog.bot.feature.helper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import advprog.bot.BotController;
import advprog.bot.feature.zonk.ZonkChatHandler;
import advprog.bot.feature.zonk.ZonkChatHandlerConfiguration;
import advprog.bot.line.LineChatHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZonkChatConfigurationTest {

    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        ZonkChatHandlerConfiguration configuration = new ZonkChatHandlerConfiguration();
        ZonkChatHandler zonkChatHandler = configuration.zonkChatHandler(controller);
        assertEquals(decoratedHandler, zonkChatHandler.getDecoratedLineChatHandler());
    }

}
