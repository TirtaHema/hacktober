package advprog.bot.feature.hangoutplace;

/**
 * Created by fazasaffanah on 22/05/2018.
 */
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import advprog.example.bot.hangoutplace.Place;
import advprog.example.bot.hangoutplace.Places;
import com.linecorp.bot.model.action.MessageAction;
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
        return event.getMessage().getText().split(" ")[0].equals("/echo");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        String[] pesan = event.getMessage().getText().split(" ");
        if (pesan[0].equals("/hangout_kuy")) {
            status = "nearest";
            return Collections.singletonList(new TextMessage("Kirimkan lokasi Anda!"));
        } else if (pesan[0].equals("/random_hangout_kuy")) {
            status = "random";
            return Collections.singletonList(new TextMessage("Kirimkan lokasi Anda!"));
        } else if (pesan[0].equals("/nearby_hangout_kuy")) {
            try {
                radius = Double.parseDouble(pesan[1]);
                status = "radius";
            } catch (Exception e) {
                return Collections.singletonList(new TextMessage(
                        "Input anda harus memenuh format /nearby_hangout_kuy (int:radius)"));
            }
            return Collections.singletonList(new TextMessage("Kirimkan lokasi Anda!"));
        }

        return Collections.singletonList(
                new TextMessage(event.getMessage().getText().replace("/echo", ""))
        ); // just return list of TextMessage for multi-line reply!
        // Return empty list of TextMessage if not replying. DO NOT RETURN NULL!!!
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
            String img = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("src/main/java/advprog/bot/feature/hangoutplace/hangout.jpg")
                    .build().toUriString();
            CarouselTemplate carouselTemplate = new CarouselTemplate(
                    Arrays.asList(
                            new CarouselColumn(img,
                                    randomPlaces.get(0).getName(),
                                    randomPlaces.get(0).getLocation(),
                                    Arrays.asList(
                                            new MessageAction("Detail",
                                                    randomPlaces.get(0).getKeterangan()))),
                            new CarouselColumn(img,
                                    randomPlaces.get(1).getName(),
                                    randomPlaces.get(1).getLocation(),
                                    Arrays.asList(
                                            new MessageAction("Detail",
                                                    randomPlaces.get(1).getKeterangan()))),
                            new CarouselColumn(img,
                                    randomPlaces.get(2).getName(),
                                    randomPlaces.get(2).getLocation(),
                                    Arrays.asList(
                                            new MessageAction("Detail",
                                                    randomPlaces.get(2).getKeterangan()))),
                            new CarouselColumn(img,
                                    randomPlaces.get(3).getName(),
                                    randomPlaces.get(3).getLocation(),
                                    Arrays.asList(
                                            new MessageAction("Detail",
                                                    randomPlaces.get(3).getKeterangan())))
                    )
            );
            return Collections.singletonList(new
                    TemplateMessage("Carousel alt text", carouselTemplate));
        } else if (status.equals("nearest")) {
            LocationMessageContent locationMessage = e.getMessage();
            return Collections.singletonList(new TextMessage(
                    places.getPlacesByRadius(radius, locationMessage.getLatitude(),
                            locationMessage.getLongitude())));
        }
        return super.handleLocationMessage(e);
    }
}
