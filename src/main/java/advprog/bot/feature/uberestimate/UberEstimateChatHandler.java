package advprog.bot.feature.uberestimate;

import advprog.bot.feature.uberestimate.uber.Location;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class UberEstimateChatHandler extends AbstractLineChatHandlerDecorator {
    private static final Logger LOGGER = Logger.getLogger(UberEstimateChatHandler.class.getName());

    private String lastIntents;
    private String lastQuery;
    private TreeMap<String, ArrayList<Location> > userData;

    @Autowired
    private LineMessagingClient lineMessagingClient;    public UberEstimateChatHandler(LineChatHandler decoratedHandler) {
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
                    lastIntents = "uber";
                    return Collections.singletonList(
                            new TextMessage("Please share your location")
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
            default:
                break;
        }
        return new ArrayList<Message>();
    }

    @Override
    protected List<Message> handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        Source source = event.getSource();
        String sender = source.getUserId();
//        return Collections.singletonList(
//                new TextMessage(sender + " " + lastIntents + " " + lastQuery)
//        );
        switch (lastIntents) {
            case "add":
                String coba = "asdf";
                if (!userData.containsKey(sender)) {
                    userData.put(sender, new ArrayList<Location>());
                    coba = "ghijk";
                }
                LocationMessageContent location = event.getMessage();
                ArrayList<Location> loc = userData.get(sender);
                if (loc == null) {
                    return Collections.singletonList(
                            new TextMessage(sender + " " + lastIntents + " hhgx " + lastQuery + " " + coba)
                    );
                }
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
                ArrayList<Location> locations =  userData.get(sender);

                List<CarouselColumn> columns = new ArrayList<CarouselColumn>();

                for(Location current : locations) {
                    columns.add(new CarouselColumn("https://getuikit.com/v2/docs/images/placeholder_200x100.svg",
                                                    current.getPlaceName(),
                                                    current.getPlaceName(),
                                                    Arrays.asList(new PostbackAction("wewLabel","lat=1&long=2","sendText" ))));
                }

                CarouselTemplate carouselTemplate = new CarouselTemplate(columns);
                TemplateMessage templateMessage =
                        new TemplateMessage(
                                "carousel alt text", carouselTemplate);

                return Collections.singletonList(
                        templateMessage
                );


            default:
                break;
        }
        return Collections.singletonList(
                new TextMessage(sender + " " + lastIntents)
        );
    }

    @EventMapping
    public void handlePostbackEvent(PostbackEvent event) {
        LOGGER.info("trigger postback event");
        String replyToken = event.getReplyToken();
        this.replyText(replyToken, "Got postback data " + event.getPostbackContent().getData() + ", param " + event.getPostbackContent().getParams().toString());

    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages))
                    .get();

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
        this.reply(replyToken, Collections.singletonList(new TextMessage(message)));
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
