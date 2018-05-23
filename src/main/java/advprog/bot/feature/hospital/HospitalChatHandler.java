package advprog.bot.feature.hospital;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;

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
            } else if (message.equals("/random_hospital")) {
                Hospital[] hospitals = HospitalBot.getHospitals();
                List<CarouselColumn> list = new ArrayList<>();
                for (Hospital hospital : hospitals) {
                    CarouselColumn data = new CarouselColumn(hospital.getImageLink(),
                            hospital.getName(),hospital.getAddress(),
                            Arrays.asList(new MessageAction("Pilih",
                                    "/hospital " + hospital.getName())));
                    list.add(data);
                }
                CarouselTemplate carouselTemplate = new CarouselTemplate(list);
                TemplateMessage templateMessage = new TemplateMessage("Pilih Bikun",
                        carouselTemplate);
                replies.add(templateMessage);
            } else if (message.split(" ")[0].equals("/hospital")) {
                String namaTargetHospital = message.replace("/hospital ","");
                Hospital targetHospital = HospitalBot.getHospital(namaTargetHospital);
                return getHospitalInformationReply(targetHospital,0,0);
            }
        }
        return replies;
    }

    private List<Message> getHospitalInformationReply(Hospital hospital,
                                                      double currentLatitude,
                                                      double currentLongitude) {

        List<Message> replies = new LinkedList<>();
        ImageMessage hospitalImage = new ImageMessage(
                hospital.getImageLink(), hospital.getImageLink()
        );
        replies.add(hospitalImage);
        LocationMessage hospitalLocation = new LocationMessage(
                hospital.getName(), hospital.getAddress(),
                hospital.getLatitude(), hospital.getLongitude()
        );
        replies.add(hospitalLocation);
        StringBuilder sb = new StringBuilder();
        sb.append(hospital.getName() + "\n");
        sb.append("\n");
        sb.append(hospital.getDescription() + "\n");
        sb.append("\n");
        sb.append(String.format("Jarak dari lokasi anda ke rumah sakit %s adalah %s meter",
                hospital.getName(),HospitalBot.getDistance(currentLatitude, currentLongitude,
                        hospital.getLatitude(), hospital.getLongitude())));
        TextMessage hospitalDetail = new TextMessage(sb.toString());
        replies.add(hospitalDetail);
        return replies;
    }

    @Override
    protected List<Message> handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        List<Message> replies;

        LocationMessageContent content = event.getMessage();
        Hospital nearestHospital = HospitalBot.findNearestHospital(
                content.getLatitude(), content.getLongitude()
        );
        replies = getHospitalInformationReply(nearestHospital,
                content.getLatitude(),content.getLongitude());
        return replies;

    }






}
