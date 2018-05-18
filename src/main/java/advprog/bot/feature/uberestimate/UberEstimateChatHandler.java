package advprog.bot.feature.uberestimate;

import advprog.bot.feature.uberestimate.uber.Location;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;

public class UberEstimateChatHandler extends AbstractLineChatHandlerDecorator {
    private static final Logger LOGGER = Logger.getLogger(UberEstimateChatHandler.class.getName());

    private String lastIntents;
    private String lastQuery;
    private TreeMap<String, ArrayList<Location> > userData;

    public UberEstimateChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        userData = new TreeMap<String, ArrayList<Location> >();
        LOGGER.info("Uber chat handler added!");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        String inp = event.getMessage().getText();
        String[] input = inp.split(" ");
        switch (input[0]) {
            case "/uber":
                lastIntents = "";
                lastQuery = "";
                break;
            case "/add_destination":
                if (input.length == 1) {
                    return Collections.singletonList(
                            new TextMessage("Please enter /add_destination [name]")
                    );
                }
                lastIntents = "add";
                lastQuery = inp.substring(17);
                return Collections.singletonList(new TextMessage("Please share your location"));
            case "/remove_destination":
                lastIntents = "remove";
                break;
            default:
                break;
        }
        return new ArrayList<Message>();
    }

    @Override
    protected List<Message> handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        switch (lastIntents) {
            case "add":
                Source source = event.getSource();
                if (userData.containsKey(source.getUserId())) {
                    userData.put(source.getUserId(), new ArrayList<Location>());
                }
                LocationMessageContent location = event.getMessage();
                userData.get(source.getUserId()).add(
                        new Location(
                                location.getLatitude(),
                                location.getLongitude(),
                                location.getAddress(),
                                lastQuery
                        )
                );
                break;
            default:
                break;
        }
        return new ArrayList<Message>();
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return true;
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
        return true;
    }
}
