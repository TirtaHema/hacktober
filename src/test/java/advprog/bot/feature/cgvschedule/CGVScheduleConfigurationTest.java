package advprog.bot.feature.cgvschedule;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CGVScheduleConfigurationTest {

    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        CGVScheduleConfiguration config = new CGVScheduleConfiguration();
        CGVScheduleHandler handler = config.CGVScheduleConfiguration(controller);
        assertEquals(decoratedHandler, handler.getDecoratedLineChatHandler());
    }
}
