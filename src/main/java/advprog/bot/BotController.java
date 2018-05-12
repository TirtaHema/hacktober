package advprog.bot;

import advprog.bot.line.LineChatHandler;
import advprog.bot.line.LineMessageReplyService;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler
public class BotController {

    private static final Logger LOGGER = Logger.getLogger(BotController.class.getName());
    private final LineMessageReplyService lineMessageReplyService;
    private LineChatHandler baseLineChatHandler;

    @Autowired
    public BotController(LineMessageReplyService messageReplyService, LineChatHandler chatHandler) {
        lineMessageReplyService = messageReplyService;
        baseLineChatHandler = chatHandler;
    }

    public void registerLineChatHandler(LineChatHandler lineChatHandler) {

    }


}
