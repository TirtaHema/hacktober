package advprog.bot.feature.hospital;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.*;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.Template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class HospitalChatHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger.getLogger(HospitalChatHandler.class.getName());

    public HospitalChatHandler(LineChatHandler decorated) {
        this.decoratedLineChatHandler = decorated;
        LOGGER.fine("Hospital chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        String message = event.getMessage().getText();
        return (message.equals("/hospital") || message.equals("/random_hospital"));
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        List<Message> replies = new LinkedList<>();
        if (event.getSource() instanceof UserSource) {
            String message = event.getMessage().getText();

            if (message.equals("/hospital")) {
                List<Action> actions = new ArrayList<Action>();
                actions.add(new URIAction("Share Location", "https://line.me/R/nv/location"));
                replies.add(new TemplateMessage("Confirm Location",
                        new ButtonsTemplate("https://upload.wikimedia.org/wikipedia/commons/0/07/Redcross.png",
                                "Find Nearest Hospital",
                                "Share your current location",
                                actions)
                ));
//            } else if (message.equals("/random_hospital")) {
//                Hospital[] hospitals = HospitalBot.getHospitals();
//                List<CarouselColumn> list = new ArrayList<>();
//                List<Action> actions = new ArrayList<Action>();
//                for (Hospital hospital : hospitals) {
//                    List<Action> actions = new ArrayList<Action>();
//                    CarouselColumn data = new CarouselColumn(hospital.getImageLink(),hospital.getName(),hospital.getAddress(),
//                            Arrays.asList(new PostbackAction("Pilih",hospital.getName())));
//                    list.add(data);
//                }
//                CarouselTemplate carouselTemplate = new CarouselTemplate(list);
//                Template
//            }
        }
        return replies;
    }



    @Override
    protected List<Message> handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        List<Message> replies = new LinkedList<>();

        LocationMessageContent content = event.getMessage();
        Hospital nearestHospital = HospitalBot.findNearestHospital(
                content.getLatitude(), content.getLongitude()
        );
        double jarak = HospitalBot.getDistance(content.getLatitude(),content.getLongitude(),
                nearestHospital.getLatitude(), nearestHospital.getLongitude()
        );

        LocationMessage hospitalLocation = new LocationMessage(
                nearestHospital.getName(), nearestHospital.getAddress(),
                nearestHospital.getLatitude(), nearestHospital.getLongitude()
        );

        ImageMessage hospitalImage = new ImageMessage(
                nearestHospital.getImageLink(), nearestHospital.getImageLink()
        );

        StringBuilder sb = new StringBuilder();
        sb.append(nearestHospital.getName() + "\n");
        sb.append("\n");
        sb.append(nearestHospital.getDescription() + "\n");
        sb.append("\n");
        sb.append(String.format("Jarak dari lokasi anda ke rumah sakit %s adalah %s meter",
                nearestHospital.getName(),jarak)
        );
        TextMessage hospitalDetail = new TextMessage(sb.toString());

        replies.add(hospitalImage);
        replies.add(hospitalLocation);
        replies.add(hospitalDetail);
        return replies;
    }






}
