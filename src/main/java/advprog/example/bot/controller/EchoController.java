package advprog.example.bot.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import javax.xml.soap.Text;
import java.util.logging.Logger;

@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        TextMessage replayMessage;

        switch (contentText.split(" ")[0]) {
            case "/echo" : {
                replayMessage = new TextMessage(contentText.replace("/echo", "").substring(1));
                break;
            }
            case "/toplaughers" : {
                replayMessage = handleTopLaughers(event);
                break;
            }
            default: {
                replayMessage = new TextMessage(contentText);
                break;
            }
        }

        return replayMessage;
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    public TextMessage handleTopLaughers(Event event) {
        String message = "1. \n" +
                "2. \n" +
                "3. \n" +
                "4. \n" +
                "5. \n";

        return new TextMessage(message);
    }
}
