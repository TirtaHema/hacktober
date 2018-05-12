package advprog.bot;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import advprog.bot.line.LineChatHandler;
import advprog.bot.line.LineMessageReplyService;

@RunWith(MockitoJUnitRunner.class)
public class BotControllerTest {

    @Mock
    LineMessageReplyService lineMessageReplyService;

    @Mock
    LineChatHandler baseChatHandler;

    BotController botController;

    @Before
    public void setUp() {
        botController = new BotController(lineMessageReplyService, baseChatHandler);
    }

}
