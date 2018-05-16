package advprog.bot.feature.Detect;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import advprog.bot.BotController;
import advprog.bot.feature.echo.EchoChatHandler;
import advprog.bot.feature.echo.EchoChatHandlerConfiguration;
import advprog.bot.line.LineChatHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DetectChatHandlerConfigurationTest {
    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        DetectChatHandlerConfiguration configuration = new DetectChatHandlerConfiguration();
        DetectChatHandler detectChatHandler = configuration.detectChatHandler(controller);
        assertEquals(decoratedHandler, detectChatHandler.getDecoratedLineChatHandler());
    }
}
