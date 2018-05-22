package advprog.bot.feature.uberestimate;

import advprog.bot.feature.uberestimate.uber.Location;
import advprog.bot.feature.uberestimate.uber.PriceDetails;
import advprog.bot.feature.uberestimate.uber.UberService;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.DatetimePickerAction;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.ImageCarouselColumn;
import com.linecorp.bot.model.message.template.ImageCarouselTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;


import java.util.*;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;


public class UberEstimateChatHandler extends AbstractLineChatHandlerDecorator {
    private static final Logger LOGGER = Logger.getLogger(UberEstimateChatHandler.class.getName());

    private String lastIntents;
    private String lastQuery;
    private Location lastLocation;
    private TreeMap<String, ArrayList<Location>> userData;

    public UberEstimateChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        userData = new TreeMap<String, ArrayList<Location> >();
        lastIntents = "";
        lastQuery = "";
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
                Source source = event.getSource();
                String sender = source.getUserId();
                ArrayList<Location> location =  userData.get(sender);
                if (location == null) {
                    return Collections.singletonList(
                            new TextMessage("No location saved")
                    );
                } else {
                    // ArrayList<Location> locations =  userData.get(sender);
                    // String imageUrl = createUri("/static/buttons/1040.jpg");
                    String imageUrl = "https://getuikit.com/v2/docs/images/placeholder_200x100.svg";
                    CarouselTemplate carouselTemplate = new CarouselTemplate(
                            Arrays.asList(
                                    new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                                            new URIAction("Go to line.me",
                                                    "https://line.me"),
                                            new URIAction("Go to line.me",
                                                    "https://line.me"),
                                            new PostbackAction("Say hello1",
                                                    "hello こんにちは")
                                    )),
                                    new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                                            new PostbackAction("言 hello2",
                                                    "hello こんにちは",
                                                    "hello こんにちは"),
                                            new PostbackAction("言 hello2",
                                                    "hello こんにちは",
                                                    "hello こんにちは"),
                                            new MessageAction("Say message",
                                                    "Rice=米")
                                    )),
                                    new CarouselColumn(imageUrl, "Datetime Picker", "Please select a date, time or datetime", Arrays.asList(
                                            new DatetimePickerAction("Datetime",
                                                    "action=sel",
                                                    "datetime",
                                                    "2017-06-18T06:15",
                                                    "2100-12-31T23:59",
                                                    "1900-01-01T00:00"),
                                            new DatetimePickerAction("Date",
                                                    "action=sel&only=date",
                                                    "date",
                                                    "2017-06-18",
                                                    "2100-12-31",
                                                    "1900-01-01"),
                                            new DatetimePickerAction("Time",
                                                    "action=sel&only=time",
                                                    "time",
                                                    "06:15",
                                                    "23:59",
                                                    "00:00")
                                    ))
                            ));
//                    List<ImageCarouselColumn> columns = new ArrayList<ImageCarouselColumn>();
//
//                    for (Location current : locations) {
//                        columns.add(new ImageCarouselColumn("https://getuikit.com/v2/docs/images/placeholder_200x100.svg",
//                                new PostbackAction(
//                                        current.getPlaceName(),
//                                        "what",
//                                        "lat= "
//                                                + Double.toString(current.getLat())
//                                                + " lon= " + Double.toString(current.getLon())
//                                        + " " + current.getPlaceName()
//                                                + " " + current.getStreet()
//                                )));
//                    }
//
//                    ImageCarouselTemplate carouselTemplate = new ImageCarouselTemplate(columns);
                    TemplateMessage templateMessage =
                            new TemplateMessage(
                                    "carousel alt text", carouselTemplate);

                    return Collections.singletonList(
                            templateMessage
                    );
                }
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
            case "lat=":
                UberService uberService = new UberService();
                lastIntents = "uber";
                lastLocation = new Location(
                        Double.parseDouble(input[1]),
                        Double.parseDouble(input[3]),
                        input[4],
                        input[5]
                );
                return Collections.singletonList(new TextMessage("Please share your location"));
            default:
                break;
        }
        return Collections.singletonList(
                new TextMessage("test")
        );
    }

    @Override
    protected List<Message> handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        Source source = event.getSource();
        String sender = source.getUserId();
        LocationMessageContent location = event.getMessage();
        switch (lastIntents) {
            case "add":
                if (!userData.containsKey(sender)) {
                    userData.put(sender, new ArrayList<Location>());
                }
                ArrayList<Location> loc = userData.get(sender);
                loc.add(
                        new Location(
                                location.getLatitude(),
                                location.getLongitude(),
                                location.getAddress(),
                                lastQuery
                        )
                );
                String tmp = sender + " ";
                ArrayList<Location> cur = userData.get(sender);
                for (Location current : cur) {
                    tmp = tmp + current.getPlaceName() + " " + Double.toString(current.getLat()) + "\n";
                }
                return Collections.singletonList(
                        new TextMessage("Location Saved " + tmp)
                );
            case "uber":
                UberService uberService = new UberService();
                List<PriceDetails> priceDetails = uberService.getPriceDetails(
                        uberService.getJsonPriceDetails(lastLocation,
                                    new Location(
                                            location.getLatitude(),
                                            location.getLongitude(),
                                            location.getAddress(),
                                            location.getAddress()
                                    )
                        )
                );

                String rides = "";
                for (PriceDetails price : priceDetails) {
                    rides = rides + "\n- Uber "
                            + price.getProvider() + ": " + price.getDuration() + " minutes, "
                            + price.getPrice();
                }

                return Collections.singletonList(
                        new TextMessage(
                                "Destination: " + lastLocation.getPlaceName()
                                + " " + lastLocation.getStreet() + "\n"
                                        + Double.toString(priceDetails.get(0).getDistance())
                                + " kilometers from current position\n"
                                        + "Estimated travel time and fares for each Uber services:\n"
                                + rides + "\n"
                                + "Data provided by [Uber](https://www.uber.com)"
                        )
                );
            default:
                break;
        }
        return Collections.singletonList(
                new TextMessage(sender + " " + lastIntents)
        );
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        String[] input = event.getMessage().getText().split(" ");
        return (input.length > 0
                && (input[0].equals("/uber")
                        || input[0].equals("/add_destination")
                        || input[0].equals("lat= ")));
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
