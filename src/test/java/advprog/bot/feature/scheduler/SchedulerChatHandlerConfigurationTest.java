package advprog.bot.feature.scheduler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerChatHandlerConfigurationTest {

    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        SchedulerChatHandlerConfiguration configuration = new SchedulerChatHandlerConfiguration();
        SchedulerChatHandler schedulerChatHandler = configuration.schedulerChatHandler(controller);
        assertEquals(decoratedHandler, schedulerChatHandler.getDecoratedLineChatHandler());
    }

}
