package advprog.bot.feature.hangoutplace;

/**
 * Created by fazasaffanah on 22/05/2018.
 */
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HangoutPlaceChatHandlerConfigurationTest {

    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        HangoutPlaceChatHandlerConfiguration configuration =
                new HangoutPlaceChatHandlerConfiguration();
        HangoutPlaceChatHandler hangoutPlaceChatHandler =
                configuration.hangoutPlaceChatHandler(controller);
        assertEquals(decoratedHandler, hangoutPlaceChatHandler.getDecoratedLineChatHandler());
    }

}