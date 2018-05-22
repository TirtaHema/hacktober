package advprog.bot.feature.bikun;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;

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
        String message = event.getMessage().getText();
        return (message.equals("/bikun") || message.equals("/bikun_stop") || message.split(" ")[0].equals("/bikun_stop"));
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        List<Message> replies = new LinkedList<>();
        if (event.getSource() instanceof UserSource) {
            String message = event.getMessage().getText();

            if (message.equals("/bikun")) {
                List<Action> actions = new ArrayList<Action>();
                actions.add(new URIAction("Share Location", "https://line.me/R/nv/location"));
                replies.add(new TemplateMessage("Confirm Location",
                        new ButtonsTemplate("https://images.idgesg.net/images/article/2017/07/location-pixabay-1200x800-100728584-large.jpg",
                                "Find Nearest Halte Bikun",
                                "Share your current location",
                                actions)
                ));
            }
            else if (message.equals("/bikun_stop")) {
                HalteBikun[] halteBikuns = BikunApp.getHalteBikuns();

                List<List<CarouselColumn>> listHalteBikuns = new ArrayList<List<CarouselColumn>>();
                int counter = 0;
                while (counter < halteBikuns.length) {
                    List<CarouselColumn> halteBikunsTemp = new ArrayList<CarouselColumn>();

                    do {
                        HalteBikun currentHalteBikun = halteBikuns[counter];
                        halteBikunsTemp.add(new CarouselColumn(currentHalteBikun.getImgUrl(),
                                "Halte Bikun " + (counter+1), currentHalteBikun.getNama(),
                                Collections.singletonList(new MessageAction("Pilih",
                                        "/bikun_stop " + currentHalteBikun.getNama())
                        )));
                        counter++;
                    }
                    while (counter % 10 > 0 && counter < halteBikuns.length);

                    listHalteBikuns.add(halteBikunsTemp);
                }

                counter = 0;
                for (List<CarouselColumn> carouselColumns : listHalteBikuns) {
                    counter++;
                    CarouselTemplate carouselTemplate = new CarouselTemplate(carouselColumns);
                    TemplateMessage templateMessage = new TemplateMessage("Pilih Bikun (Page "+counter+")",
                            carouselTemplate);
                    replies.add(templateMessage);
                }
            }
            else if (message.split(" ")[0].equals("/bikun_stop")) {
                String namaTargetHalte = message.replace("/bikun_stop ", "");
                HalteBikun targetHalte = BikunApp.getHalteByName(namaTargetHalte);
                return getHalteInformationReply(targetHalte);
            }
        }
        return replies;
    }

    @Override
    protected List<Message> handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        LocationMessageContent content = event.getMessage();
        HalteBikun nearestHalteBikun = BikunApp.findNearestHalteBikun(
                content.getLatitude(), content.getLongitude()
        );

        return getHalteInformationReply(nearestHalteBikun);
    }

    private List<Message> getHalteInformationReply(HalteBikun halteBikun) {
        List<Message> replies = new LinkedList<>();

        LocationMessage halteBikunLocation = new LocationMessage(
                halteBikun.getNama(), "Universitas Indonesia",
                halteBikun.getLatitude(), halteBikun.getLongitude()
        );

        int waktu = BikunApp.getWaitingTime(halteBikun);
        int jam = waktu / 60;
        int menit = waktu % 60;
        String strWaktu = "";
        if (jam > 0) {
            strWaktu += jam + " jam ";
        }
        strWaktu += menit + " menit";
        TextMessage halteBikunDetail = new TextMessage(
                String.format("Halte terdekat dari lokasi Anda adalah :\n"
                                + "%s\n"
                                + "Bikun selanjutnya akan tiba dalam waktu %s",
                        halteBikun.getNama(), strWaktu)
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
