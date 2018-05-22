package advprog.bot.feature.bikun;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class BikunChatHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger.getLogger(BikunChatHandler.class.getName());

    public BikunChatHandler(LineChatHandler decorated) {
        this.decoratedLineChatHandler = decorated;
        LOGGER.fine("Bikun chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().equals("/bikun");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        List<Message> replies = new LinkedList<>();

//        List<Action> actions = new ArrayList<Action>();
//        actions.add(new URIAction("Share Location", "https://line.me/R/nv/location"));
//        replies.add(new TemplateMessage("Confirm Location",
//                new ButtonsTemplate("https://images.idgesg.net/images/article/2017/07/location-pixabay-1200x800-100728584-large.jpg",
//                        "Find Nearest Halte Bikun",
//                        "Share your current location",
//                        actions)
//                ));
        replies.add(new TextMessage("ancol"));
        return replies;
    }

    @Override
    protected List<Message> handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        List<Message> replies = new LinkedList<>();

        LocationMessageContent content = event.getMessage();
        HalteBikun nearestHalteBikun = BikunApp.findNearestHalteBikun(
                content.getLatitude(), content.getLongitude()
        );
        double jarak = BikunApp.getDistance(content.getLatitude(), content.getLongitude(),
                nearestHalteBikun.getLatitude(), nearestHalteBikun.getLongitude()
        );

        LocationMessage halteBikunLocation = new LocationMessage(
                nearestHalteBikun.getNama(), "Universitas Indonesia",
                nearestHalteBikun.getLatitude(), nearestHalteBikun.getLongitude()
        );
        TextMessage halteBikunDetail = new TextMessage(
                String.format("Halte terdekat dari lokasi Anda adalah : %s dengan jarak %s meter",
                        nearestHalteBikun.getNama(), jarak)
        );

        replies.add(halteBikunLocation);
        replies.add(halteBikunDetail);
        return replies;
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
