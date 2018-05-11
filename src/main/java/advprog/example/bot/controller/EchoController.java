package advprog.example.bot.controller;

import advprog.example.bot.helper.Album;
import advprog.example.bot.helper.MusicBrainzAPIHelper;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.List;
import java.util.logging.Logger;

@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());
    private static final MusicBrainzAPIHelper musicBrainzAPIHelper = new MusicBrainzAPIHelper();

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        if (contentText.contains("/10albums")) {
            return new TextMessage(queryArtistRecentAlbum(contentText.replace("/10albums ", "")));
        }

        String replyText = contentText.replace("/echo", "");
        return new TextMessage(replyText);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    public String queryArtistRecentAlbum(String artist) {
        try {
            List<Album> albumList = musicBrainzAPIHelper.getMostRecentAlbumByArtistID(musicBrainzAPIHelper.getArtistId(artist));
            StringBuilder result = new StringBuilder();

            result.append(artist);
            result.append("\n");
            for (Album album : albumList) {
                result.append(album.getDate());
                result.append(" ");
                result.append(album.getName());
                result.append("\n");
            }

            return result.toString();
        } catch (Exception exception) {
            LOGGER.fine(exception.toString());

            return null;
        }
    }

}
