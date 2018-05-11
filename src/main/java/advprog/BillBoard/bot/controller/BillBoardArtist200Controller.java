package advprog.BillBoard.bot.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler
public class BillBoardArtist200Controller {
    private static final Logger LOGGER = Logger.getLogger(BillBoardArtist200Controller.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
            event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String replyText = "";
        if(contentText.equals("/billboard bill200 Drake")) {
            replyText = "Drake\nNice For What\n2";
        }
        else {
            replyText = "The artist Coldplay is not in Billboard Top 200 chart";
        }
        return new TextMessage(replyText);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
            event.getTimestamp(), event.getSource()));
    }


}
