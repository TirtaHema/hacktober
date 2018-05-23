package advprog.bot.feature.hospital;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import advprog.bot.line.BaseChatHandler;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HospitalChatHandlerTest {
    HospitalChatHandler hospitalChatHandler;

    @Before
    public void setUp() {
        hospitalChatHandler = new HospitalChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEventHospital() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("bbb"));
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("bbb"));
        List<Action> actions = new ArrayList<Action>();
        actions.add(new URIAction("Share Location", "https://line.me/R/nv/location"));
        expectedMessages.add(new TemplateMessage("Confirm Location",
                new ButtonsTemplate("https://upload.wikimedia.org/wikipedia/commons/0/07/Redcross.png",
                        "Find Nearest Hospital",
                        "Share your current location",
                        actions)
        ));
        String msg = "/hospital";
        MessageEvent<TextMessageContent> me = new MessageEvent<>(
                "1234",new UserSource("1234"),
                new TextMessageContent("123",msg), Instant.now()
        );
        assertEquals(expectedMessages, hospitalChatHandler.handleTextMessageEvent(me, messages));
    }



    @Test
    public void testHandleTextMessageEventSpecificHospital() {
        List<Message> messages = new LinkedList<>();
        messages.add(new TextMessage("bbb"));
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("bbb"));
        Hospital hospital = HospitalBot.getHospital("RSUD Tarakan");
        ImageMessage hospitalImage = new ImageMessage(
                hospital.getImageLink(), hospital.getImageLink()
        );
        expectedMessages.add(hospitalImage);
        LocationMessage hospitalLocation = new LocationMessage(
                hospital.getName(), hospital.getAddress(),
                hospital.getLatitude(), hospital.getLongitude()
        );
        expectedMessages.add(hospitalLocation);
        StringBuilder sb = new StringBuilder();
        sb.append(hospital.getName() + "\n");
        sb.append("\n");
        sb.append(hospital.getDescription() + "\n");
        sb.append("\n");
        sb.append(String.format("Jarak dari lokasi anda ke rumah sakit %s adalah %s meter",
                hospital.getName(),HospitalBot.getDistance(0, 0,
                        hospital.getLatitude(), hospital.getLongitude())));
        TextMessage hospitalDetail = new TextMessage(sb.toString());
        expectedMessages.add(hospitalDetail);
        String msg = "/hospital RSUD Tarakan";
        MessageEvent<TextMessageContent> me = new MessageEvent<>(
                "1234", new UserSource("12345"),
                new TextMessageContent("123456", msg), Instant.now()
        );
        assertEquals(expectedMessages, hospitalChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(hospitalChatHandler.canHandleAudioMessage(null));
        assertFalse(hospitalChatHandler.canHandleImageMessage(null));
        assertFalse(hospitalChatHandler.canHandleStickerMessage(null));
        assertTrue(hospitalChatHandler.canHandleLocationMessage(null));
    }
}
