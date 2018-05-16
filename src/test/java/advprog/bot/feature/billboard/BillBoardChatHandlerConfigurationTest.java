package advprog.bot.feature.billboard;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BillBoardChatHandlerConfigurationTest {
    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        BillBoardChatHandlerConfiguration configuration = new BillBoardChatHandlerConfiguration();
        BillBoardChatHandler billboardChatHandler = configuration.billboardChatHandler(controller);
        assertEquals(decoratedHandler, billboardChatHandler.getDecoratedLineChatHandler());
    }
}
