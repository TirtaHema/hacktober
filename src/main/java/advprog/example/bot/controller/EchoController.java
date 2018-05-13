package advprog.example.bot.controller;

import advprog.example.bot.photos.nearby.FlickrService;
import advprog.example.bot.photos.nearby.Location;
import advprog.example.bot.photos.nearby.Photo;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ImageCarouselColumn;
import com.linecorp.bot.model.message.template.ImageCarouselTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;


@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());

    private String lastIntents;

/*
    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();


        String[] input = contentText.split(" ");



        if (!isValidInput(input)) {
            return new TextMessage("Masukkan 2 bilangan koordinat latitude dan longitude, format : double");
        } else {
            Double latitude = Double.parseDouble(input[0]);
            Double longitude = Double.parseDouble(input[1]);

            if (!validLatitude(latitude) || !validLongitude(longitude)) {
                return new TextMessage("Masukkan latitude diantara -90 sampai 90, longitude -180 sampai 180");
            }

            FlickrService service = new FlickrService();
            List<String> urls = service.Get5Photos(new Location(latitude, longitude));

            String hasil = "";

            for(String cur : urls) hasil += cur + "\n";

            return new TextMessage(hasil);
        }
    }
*/
@Autowired
private LineMessagingClient lineMessagingClient;

    @EventMapping
    public void handleLocationMessageEvent(MessageEvent<LocationMessageContent> event) {
        try{
            if(lastIntents=="nearby photos"){
                LocationMessageContent locationMessage = event.getMessage();

                Double latitude = locationMessage.getLatitude();
                Double longitude = locationMessage.getLongitude();

                FlickrService service = new FlickrService();
                List<Photo> photos = service.get5Photos(new Location(latitude, longitude));

                List<ImageCarouselColumn> columns = new ArrayList<ImageCarouselColumn>();

                for(Photo photo : photos){
                    columns.add(new ImageCarouselColumn(photo.getUrl(), new URIAction(photo.getTitle(), photo.getUrl())));
                }

                if (photos.size() == 0) {
                    this.replyText(event.getReplyToken(), "NO PHOTOS");
                    return;
                } else {
                    String ampas = "COBA ";
                    for(Photo photo : photos) {
                        ampas += photo.getUrl() + " " + photo.getTitle() + " ";
                    }
                    this.replyText(event.getReplyToken(), ampas);
                    return;
                }

//                ImageCarouselTemplate imageCarouselTemplate = new ImageCarouselTemplate(columns);
//                TemplateMessage templateMessage = new TemplateMessage("ImageCarousel alt text", imageCarouselTemplate);
//                this.reply(event.getReplyToken(), templateMessage);
            }

            lastIntents ="";
        }
        catch (Exception ex){
            this.replyText(event.getReplyToken(), ex.getMessage());
        }

    }


    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent message = event.getMessage();
        handleTextContent(event.getReplyToken(), event, message);
    }

    private void handleTextContent(String replyToken, Event event, TextMessageContent content)
            throws Exception {
        String text = content.getText();
        LOGGER.fine(String.format("Got text message from {}: {}", replyToken, text));
        lastIntents = "";
        switch (text) {
            case "nearby photos":{
                this.replyText(replyToken, "Please share your location");
                lastIntents = "nearby photos";
                break;
            }
            default:
                LOGGER.fine(String.format("Returns echo message {}: {}", replyToken, text));
                //this.replyText(replyToken, text);
                break;
        }

    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages))
                    .get();
            LOGGER.fine(String.format("Sent messages: {}", apiResponse));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void replyText(@NonNull String replyToken, @NonNull String message) {
        if (replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken must not be empty");
        }
        if (message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "……";
        }
        this.reply(replyToken, new TextMessage(message));
    }

    private static String createUri(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path).build()
                .toUriString();
    }


    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

}
