package advprog.example.bot.controller;

import advprog.example.bot.photos.nearby.FlickrService;
import advprog.example.bot.photos.nearby.Location;
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

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    public boolean isValidInput( String[] input ) {
        if (input.length != 2 || !isDouble(input[0]) || !isDouble(input[1])) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validLatitude(Double latitude) {
        return latitude >= -90.0 && latitude <= 90.0;
    }

    public boolean validLongitude(Double longitude) {
        return longitude >= -180.0 && longitude <= 180.0;
    }

    public boolean isDouble( String str ){
        try{
            Double.parseDouble( str );
            return true;
        }
        catch( Exception e ){
            return false;
        }
    }
}
