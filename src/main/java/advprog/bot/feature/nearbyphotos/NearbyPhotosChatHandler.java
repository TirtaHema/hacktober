package advprog.bot.feature.nearbyphotos;

import advprog.bot.feature.nearbyphotos.flickr.FlickrService;
import advprog.bot.feature.nearbyphotos.flickr.IPictureService;
import advprog.bot.feature.nearbyphotos.flickr.Location;
import advprog.bot.feature.nearbyphotos.flickr.Photo;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.linecorp.bot.model.message.template.ImageCarouselColumn;
import com.linecorp.bot.model.message.template.ImageCarouselTemplate;
import org.springframework.stereotype.Service;

public class NearbyPhotosChatHandler extends AbstractLineChatHandlerDecorator {
    private static final Logger LOGGER = Logger.getLogger(NearbyPhotosChatHandler.class.getName());

    private String lastIntents;

    public NearbyPhotosChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Nearby Photos chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().split(" ")[0].equals("/echo");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        lastIntents = "";
        TextMessageContent message = event.getMessage();
        if(message.getText().equals("nearby photos")){
            lastIntents = "nearby photos";
            return Collections.singletonList(
                    new TextMessage("Please share your location")
            );
        }

        return Collections.singletonList(
                new TextMessage("No command found")
        );
    }

    @Override
    protected List<Message> handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        try
        {
            if(lastIntents.equals("nearby photos")){
                lastIntents ="";

                LocationMessageContent locationMessage = event.getMessage();

                Double latitude = locationMessage.getLatitude();
                Double longitude = locationMessage.getLongitude();

                IPictureService service = new FlickrService();
                List<Photo> photos = service.get5Photos(new Location(latitude, longitude));

                List<ImageCarouselColumn> columns = new ArrayList<ImageCarouselColumn>();

                for(Photo photo : photos){
                    columns.add(new ImageCarouselColumn(photo.getUrl(), new URIAction(service.formatTitleForCarouselImages(photo.getTitle()), photo.getUrl())));
                }

                if (photos.size() == 0) {
                    return Collections.singletonList(
                            new TextMessage("No photo found")
                    );
                }

                ImageCarouselTemplate imageCarouselTemplate = new ImageCarouselTemplate(columns);
                TemplateMessage templateMessage = new TemplateMessage("ImageCarousel alt text", imageCarouselTemplate);
                return Collections.singletonList(
                        templateMessage
                );
            }

            return new ArrayList<Message>();


        }
        catch (Exception ex){
            return Collections.singletonList(
                    new TextMessage("No photo found")
            );
        }
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
