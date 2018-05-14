package advprog.bot.feature.toplaughers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TopLaughersChatHandlerConfigurationTest {

    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        TopLaughersChatHandlerConfiguration configuration = new TopLaughersChatHandlerConfiguration();
        TopLaughersChatHandler echoChatHandler = configuration.topLaughersChatHandler(controller);
        assertEquals(decoratedHandler, echoChatHandler.getDecoratedLineChatHandler());
    }

}
