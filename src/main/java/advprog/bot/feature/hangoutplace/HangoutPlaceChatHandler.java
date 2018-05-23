package advprog.bot.feature.hangoutplace;

/**
 * Created by fazasaffanah on 22/05/2018.
 */
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import advprog.example.bot.hangoutplace.Haversine;
import advprog.example.bot.hangoutplace.Place;
import advprog.example.bot.hangoutplace.Places;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.ImageCarouselColumn;
import com.linecorp.bot.model.message.template.ImageCarouselTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class HangoutPlaceChatHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger.getLogger(HangoutPlaceChatHandler.class.getName());
    private static String status = "";
    private static Places places = new Places();
    private static double radius = 0;

    public HangoutPlaceChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Hangout place chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().equals("/hangout_kuy")
                || event.getMessage().getText().equals("/random_hangout_kuy")
                || event.getMessage().getText().split(" ")[0].equals("/nearby_hangout_kuy");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        String[] pesan = event.getMessage().getText().split(" ");
        if (pesan[0].equals("/hangout_kuy")) {
            status = "nearest";
            return Collections.singletonList(new TextMessage("Silahkan kirim lokasi Anda"));
        } else if (pesan[0].equals("/random_hangout_kuy")) {
            status = "random";
            return Collections.singletonList(new TextMessage("Silahkan kirim lokasi Anda"));
        } else if (pesan[0].equals("/nearby_hangout_kuy")) {
            try {
                radius = Double.parseDouble(pesan[1]);
                status = "radius";
            } catch (Exception e) {
                return Collections.singletonList(new TextMessage(
                        "Input anda harus memenuhi format : \n/nearby_hangout_kuy (int:radius)"));
            }
            return Collections.singletonList(new TextMessage("Silahkan kirim lokasi Anda"));
        }

        return Collections.singletonList(new TextMessage(""));
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

    @Override
    protected List<Message> handleLocationMessage(MessageEvent<LocationMessageContent> e) {
        if (status.equals("nearest")) {
            LocationMessageContent locationMessage = e.getMessage();
            return Collections.singletonList(new TextMessage(places
                    .getNearestPlaces(locationMessage.getLatitude(),
                            locationMessage.getLongitude())));
        } else if (status.equals("random")) {
            LocationMessageContent locationMessage = e.getMessage();
            ArrayList<Place> randomPlaces = places.getRandomPlaces();
            String img =
                    "https://media-cdn.tripadvisor.com/media/photo-s/0a/ad/22/9d/nice-hangout-place.jpg";
            List<CarouselColumn> columns = new ArrayList<CarouselColumn>();
            for (int i = 0; i < 3; i++) {
                Place place = randomPlaces.get(i);
                double dist = Haversine.distance(locationMessage.getLatitude(),
                        locationMessage.getLongitude(), place.getLatitude(),
                        place.getLongitude());
                columns.add(new CarouselColumn(img,
                                place.getName(),
                                place.getLocation(),
                                Arrays.asList(
                                        new MessageAction("View Detail",
                                                place.getKeterangan() + "\nJarak : "
                                                        + Double.parseDouble(String
                                                        .format("%.3f", dist))
                                                        + " km")))
                );
            }
            CarouselTemplate carouselTemplate = new CarouselTemplate(columns);
            return Collections.singletonList(new
                    TemplateMessage("Carousel alt text", carouselTemplate));
        } else if (status.equals("radius")) {
            LocationMessageContent locationMessage = e.getMessage();
            return Collections.singletonList(new TextMessage(
                    places.getPlacesByRadius(radius, locationMessage.getLatitude(),
                            locationMessage.getLongitude())));
        }
        return super.handleLocationMessage(e);
    }
}
