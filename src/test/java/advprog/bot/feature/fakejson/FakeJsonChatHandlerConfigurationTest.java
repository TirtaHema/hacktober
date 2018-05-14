package advprog.bot.feature.fakejson;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FakeJsonChatHandlerConfigurationTest {

    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        FakeJsonChatHandlerConfiguration configuration = new FakeJsonChatHandlerConfiguration();
        FakeJsonChatHandler fakeJsonChatHandler = configuration.fakeJsonChatHandler(controller);
        assertEquals(decoratedHandler, fakeJsonChatHandler.getDecoratedLineChatHandler());
    }
}
