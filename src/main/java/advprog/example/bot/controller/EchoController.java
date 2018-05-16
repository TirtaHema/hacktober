package advprog.example.bot.controller;

import advprog.example.bot.CountryBot.CountrySongChartBot;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

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

        String[] splitContent = contentText.split(" ",2);
        String command = splitContent[0];

        if (splitContent.length <2){
            return handleDefaultMessage(event);
        }

        String input = splitContent[1];

        if (command.equals("/echo")){
            String replyText = contentText.replace("/echo", "");
            return new TextMessage(replyText.substring(1));
        }
        else if (command.equals("/billboard")){
            String[] input2 = input.split(" ",2);

            String command2 = input2[0];
            if (command2.toLowerCase().equals("hotcountry")){
                String artis = input2[1];
                CountrySongChartBot bot = new CountrySongChartBot(artis);
                return new TextMessage(bot.FavoriteArtist());
            }
        }

        return handleDefaultMessage(event);
    }

    @EventMapping
    public TextMessage handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));

        return new TextMessage("Error Command");
    }
}
