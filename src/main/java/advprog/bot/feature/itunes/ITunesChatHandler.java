package advprog.bot.feature.itunes;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class ITunesChatHandler extends AbstractLineChatHandlerDecorator {
    private static final Logger LOGGER = Logger.getLogger(ITunesChatHandler.class.getName());
    private static String ItunesPreviewImageUrl =
        "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Download_on_iTunes.svg/200px-Download_on_iTunes.svg.png";

    public ITunesChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("iTunes chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        String billboard = event.getMessage().getText().split(" ")[0];
        return (billboard.equals("/itunes_preview"));
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        String[] inputText = event.getMessage().getText().split(" ");
        if (event.getMessage().getText().contains("/itunes_preview")) {
            try {
                return previewArtist(event.getMessage().getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Collections.singletonList(
            new TextMessage("Please specify your artist")
        );

        // just return list of TextMessage for multi-line reply!
        // Return empty list of TextMessage if not replying. DO NOT RETURN NULL!!!


    }

    protected List<Message> previewArtist(String input) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        String inputArtist = input.replace("/itunes_preview ", "").toLowerCase();
        inputArtist.replace(" ", "+");
        ITunesParser parser = new ITunesParser(inputArtist);
        result = parser.getRandom();
        System.out.println(result);
        if (result == null) {
            return Collections.singletonList(
                new TextMessage("Sorry, your artist is not in iTunes")
            );
        }
        String artist = result.get(0);
        String track = result.get(1);
        String link = result.get(2);
        System.out.println(artist + "-" + track);
        return Arrays.asList(
            new TextMessage(artist + " - " + track),
            new AudioMessage(link, 28),
            new ImageMessage(
                "https://upload.wikimedia.org/wikipedia/commons/5/55/Download_on_iTunes.svg",
                ItunesPreviewImageUrl)
        );
    }

    @Override
    protected boolean canHandleImageMessage(MessageEvent<ImageMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleAudioMessage(MessageEvent<AudioMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleStickerMessage(MessageEvent<StickerMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleLocationMessage(MessageEvent<LocationMessageContent> event) {
        return false;
    }


}
