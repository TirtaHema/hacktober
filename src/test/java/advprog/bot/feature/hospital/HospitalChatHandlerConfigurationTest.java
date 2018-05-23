package advprog.bot.feature.hospital;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HospitalChatHandlerConfigurationTest {

    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        HospitalChatHandlerConfiguration configuration = new HospitalChatHandlerConfiguration();
        HospitalChatHandler hospitalChatHandler = configuration.hospitalChatHandler(controller);
        assertEquals(decoratedHandler, hospitalChatHandler.getDecoratedLineChatHandler());
    }
}
