package advprog.bot.feature.bikun;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class BikunChatHandlerTest {
    BikunChatHandler bikunChatHandler;

    @Before
    public void setUp() {
        bikunChatHandler = new BikunChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEventBikun() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("bbb"));
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("bbb"));
        List<Action> actions = new ArrayList<Action>();
        actions.add(new URIAction("Share Location", "https://line.me/R/nv/location"));
        String thumbnailImageUrl = "https://images.idgesg.net/images/article/"
                + "2017/07/location-pixabay-1200x800-100728584-large.jpg";
        expectedMessages.add(new TemplateMessage("Confirm Location",
                new ButtonsTemplate(thumbnailImageUrl,
                        "Find Nearest Halte Bikun",
                        "Share your current location",
                        actions)
        ));
        String msg = "/bikun";

        MessageEvent<TextMessageContent> me = new MessageEvent<>(
                "1234", new UserSource("12345"),
                new TextMessageContent("123456", msg), Instant.now()
        );
        assertEquals(expectedMessages, bikunChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testHandleTextMessageEventSpecificBikunStop() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("bbb"));
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("bbb"));
        HalteBikun halteBikun = BikunApp.getHalteByName("Halte Fisip UI");

        LocationMessage halteBikunLocation = new LocationMessage(
                halteBikun.getNama(), "Universitas Indonesia",
                halteBikun.getLatitude(), halteBikun.getLongitude()
        );
        expectedMessages.add(halteBikunLocation);

        int waktu = BikunApp.getWaitingTime(halteBikun);
        int jam = waktu / 60;
        int menit = waktu % 60;
        String strWaktu = "";
        if (jam > 0) {
            strWaktu += jam + " jam ";
        }
        strWaktu += menit + " menit";
        expectedMessages.add(new TextMessage(String.format(
                "Anda memilih Halte Fisip UI\nBikun selanjutnya akan tiba dalam waktu %s",
                strWaktu))
        );
        String msg = "/bikun_stop Halte Fisip UI";
        MessageEvent<TextMessageContent> me = new MessageEvent<>(
                "1234", new UserSource("12345"),
                new TextMessageContent("123456", msg), Instant.now()
        );
        assertEquals(expectedMessages, bikunChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testHandleLocationMessageEvent() {
        List<Message> expectedMessages = new LinkedList<>();
        HalteBikun halteBikun = BikunApp.getHalteByName("Halte Psikologi UI");

        LocationMessage halteBikunLocation = new LocationMessage(
                halteBikun.getNama(), "Universitas Indonesia",
                halteBikun.getLatitude(), halteBikun.getLongitude()
        );
        expectedMessages.add(halteBikunLocation);

        int waktu = BikunApp.getWaitingTime(halteBikun);
        int jam = waktu / 60;
        int menit = waktu % 60;
        String strWaktu = "";
        if (jam > 0) {
            strWaktu += jam + " jam ";
        }
        strWaktu += menit + " menit";
        expectedMessages.add(new TextMessage(String.format(
                "Halte Bikun terdekat dari lokasi Anda adalah :\n" +
                        "Halte Psikologi UI\n" +
                        "Dengan jarak 0.16 meter\n" +
                        "Bikun selanjutnya akan tiba dalam waktu %s",
                strWaktu))
        );

        LocationMessageContent locationMessageContent =
                new LocationMessageContent("123", "Universitas Indonesia",
                        "UI", -6.362865, 106.831115);
        MessageEvent<LocationMessageContent> me = new MessageEvent<>(
                "1234", new UserSource("12345"),
                locationMessageContent, Instant.now()
        );

        assertEquals(expectedMessages, bikunChatHandler.handleLocationMessageEvent(me, new LinkedList<>()));
    }

    @Test
    public void testHandleTextMessageEventBikunStop() {
        List<Message> expectedMessages = new LinkedList<>();
        HalteBikun[] halteBikuns = BikunApp.getHalteBikuns();

        List<List<CarouselColumn>> listHalteBikuns = new ArrayList<List<CarouselColumn>>();
        int counter = 0;
        while (counter < halteBikuns.length) {
            List<CarouselColumn> halteBikunsTemp = new ArrayList<CarouselColumn>();

            do {
                HalteBikun currentHalteBikun = halteBikuns[counter];
                halteBikunsTemp.add(new CarouselColumn(currentHalteBikun.getImgUrl(),
                        "Halte Bikun " + (counter + 1), currentHalteBikun.getNama(),
                        Collections.singletonList(new MessageAction("Pilih",
                                "/bikun_stop " + currentHalteBikun.getNama())
                        )));
                counter++;
            } while (counter % 10 > 0 && counter < halteBikuns.length);

            listHalteBikuns.add(halteBikunsTemp);
        }

        counter = 0;
        for (List<CarouselColumn> carouselColumns : listHalteBikuns) {
            counter++;
            CarouselTemplate carouselTemplate = new CarouselTemplate(carouselColumns);
            TemplateMessage templateMessage = new TemplateMessage(
                    "Pilih Bikun (Page " + counter + ")",
                    carouselTemplate);
            expectedMessages.add(templateMessage);
        }

        String msg = "/bikun_stop";
        MessageEvent<TextMessageContent> me = new MessageEvent<>(
                "1234", new UserSource("12345"),
                new TextMessageContent("123456", msg), Instant.now()
        );
        assertEquals(expectedMessages, bikunChatHandler.handleTextMessageEvent(me, new LinkedList<>()));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(bikunChatHandler.canHandleAudioMessage(null));
        assertFalse(bikunChatHandler.canHandleImageMessage(null));
        assertFalse(bikunChatHandler.canHandleStickerMessage(null));
        assertTrue(bikunChatHandler.canHandleLocationMessage(null));
    }
}
