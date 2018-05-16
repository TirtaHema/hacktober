package advprog.bot.feature.newage;

import static org.mockito.Mockito.mock;

import advprog.bot.line.LineChatHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NewAgeChatHandlerTest {

    @Test
    public void testConstruct() {
        NewAgeChatHandler handler = new NewAgeChatHandler(mock(LineChatHandler.class));
    }

}
