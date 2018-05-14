package advprog.bot.feature.docssimilarity;

import advprog.bot.BotController;
import advprog.bot.line.LineChatHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocsSimilarityChatHandlerConfigurationTest {
    @Test
    public void testConstruction() {
        BotController controller = mock(BotController.class);
        LineChatHandler decoratedHandler = mock(LineChatHandler.class);
        when(controller.getLineChatHandler()).thenReturn(decoratedHandler);
        DocsSimilarityChatHandlerConfiguration configuration = new DocsSimilarityChatHandlerConfiguration();
        DocsSimilarityChatHandler docsSimilarityChatHandler = configuration.docsSimilarityChatHandler(controller);
        assertEquals(decoratedHandler, docsSimilarityChatHandler.getDecoratedLineChatHandler());
    }
}
