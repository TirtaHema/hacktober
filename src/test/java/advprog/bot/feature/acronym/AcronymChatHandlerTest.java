package advprog.bot.feature.acronym;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.feature.acronym.helper.FileAccessor;
import advprog.bot.line.BaseChatHandler;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.linecorp.bot.model.message.template.CarouselTemplate;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AcronymChatHandlerTest {
    //TODO: GROUP CHAT, CAROUSEL INTERACTION
    AcronymChatHandler acronymChatHandler;

    @Before
    public void setUp() {
        acronymChatHandler = new AcronymChatHandler(new BaseChatHandler());
        FileAccessor fileAccessor = new FileAccessor();
        try {
            fileAccessor.saveFile("acronyms", "");
            fileAccessor.saveFile("groupId", "");
            fileAccessor.saveFile("userId", "");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testPrivateChat_AddAcronym() {
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();

        expectedMessages.add(new TextMessage("Please write your acronym"));
        MessageEvent<TextMessageContent> me = TestUtil
                .createDummyPrivateTextMessage("/add_acronym");
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        expectedMessages.add(new TextMessage("Please specify the extension"));
        me = TestUtil.createDummyPrivateTextMessage("ACR");
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        expectedMessages.add(new TextMessage("Acronym successfully added/updated"));
        me = TestUtil.createDummyPrivateTextMessage("Acronym");
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testPrivateChat_UpdateAcronym() {
        List<Message> messages = new LinkedList<>();
        List<Message> expectedMessages = new LinkedList<>();
        MessageEvent<TextMessageContent> me = TestUtil.createDummyPrivateTextMessage("/update_acronym");
        expectedMessages.add(new TextMessage("No acronyms yet"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        TestUtil.addAcronym("ACR", "Acronym");

        expectedMessages = new LinkedList<>();
        messages = new LinkedList<>();
        me = TestUtil.createDummyPrivateTextMessage("/update_acronym");
        expectedMessages.add(new TextMessage("Which acronym to update?"));
        expectedMessages.add(new TemplateMessage(
                "[ACR -- Acronym]",
                TestUtil.createCarouselResponse(Collections.singletonList("ACR -- Acronym"))));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyPrivateTextMessage("W");
        expectedMessages.add(new TextMessage("Acronym not found"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyPrivateTextMessage("ACR");
        expectedMessages.add(new TextMessage("Please specify the new extension"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyPrivateTextMessage("Acronym");
        expectedMessages.add(new TextMessage("Acronym successfully added/updated"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testPrivateChat_DeleteAcronym() {
        List<Message> messages = new LinkedList<>();
        List<Message> expectedMessages = new LinkedList<>();

        expectedMessages.add(new TextMessage("No acronyms yet"));

        MessageEvent<TextMessageContent> me = TestUtil.createDummyPrivateTextMessage("/delete_acronym");
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        TestUtil.addAcronym("ACR", "Acronym");

        expectedMessages = new LinkedList<>();
        messages = new LinkedList<>();
        me = TestUtil.createDummyPrivateTextMessage("/delete_acronym");
        expectedMessages.add(new TextMessage("Which acronym to delete?"));
        expectedMessages.add(new TemplateMessage(
                "[ACR -- Acronym]",
                TestUtil.createCarouselResponse(Collections.singletonList("ACR -- Acronym"))));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyPrivateTextMessage("W");
        expectedMessages.add(new TextMessage("Acronym not found"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyPrivateTextMessage("ACR");
        expectedMessages.add(new TextMessage("Are you sure?"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyPrivateTextMessage("No");
        expectedMessages.add(new TextMessage("Delete cancelled"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyPrivateTextMessage("/delete_acronym");
        expectedMessages.add(new TextMessage("Which acronym to delete?"));
        expectedMessages.add(new TemplateMessage(
                "[ACR -- Acronym]",
                TestUtil.createCarouselResponse(Collections.singletonList("ACR -- Acronym"))));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyPrivateTextMessage("ACR");
        expectedMessages.add(new TextMessage("Are you sure?"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyPrivateTextMessage("Yes");
        expectedMessages.add(new TextMessage("Acronym successfully deleted"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testGroupChat() {
        List<Message> messages = new ArrayList<>();
        List<Message> expectedMessages = new ArrayList<>();
        String windi = "U16efaf7d89b30e599dc82e38a7e4fb56";
        String norman = "U63d4529458763a7fd0df60a705134d5d";

        MessageEvent<TextMessageContent> me = TestUtil.createDummyGroupTextMessage("start acronym");
        acronymChatHandler.handleTextMessageEvent(me, messages);
        expectedMessages.add(new TextMessage("No acronyms yet"));
        assertEquals(expectedMessages, messages);

        TestUtil.addAcronym("ACR", "Acronym");
        TestUtil.addAcronym("W", "Windi");

        me = TestUtil.createDummyGroupTextMessage("start acronym");
        acronymChatHandler.handleTextMessageEvent(me, messages);
        String acronym = TestUtil.getCurrentAcronym();
        expectedMessages.add(new TextMessage("What is the extension of " + acronym + "?"));
        assertEquals(expectedMessages, messages);

        me = TestUtil.createDummyGroupTextMessage("wrong");
        expectedMessages.add(
                new TextMessage("Something went wrong. Have you added me as a friend?"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyGroupTextMessage("wrong", windi);
        expectedMessages.add(
                new TextMessage("Windi Chandra has answered incorrectly"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyGroupTextMessage("wrong", windi);
        expectedMessages.add(
                new TextMessage("Windi Chandra has answered incorrectly"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyGroupTextMessage("wrong", windi);
        expectedMessages.add(
                new TextMessage("Windi Chandra has answered incorrectly"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyGroupTextMessage("wrong", windi);
        expectedMessages.add(
                new TextMessage("Windi Chandra cannot answer anymore"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyGroupTextMessage((acronym.equals("ACR") ? "Acronym" : "Windi"), norman);
        expectedMessages.add(
                new TextMessage("Norman Bintang has answered correctly\n"
                        + "What is the extension of "
                        + (acronym.equals("ACR") ? "W" : "ACR")
                        + "?"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyGroupTextMessage((acronym.equals("ACR") ? "Windi" : "Acronym"), norman);
        expectedMessages.add(
                new TextMessage("Norman Bintang has answered correctly\n"
                        + "What is the extension of "
                        + (acronym.equals("ACR") ? "ACR" : "W")
                        + "?"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));

        me = TestUtil.createDummyGroupTextMessage("stop acronym");
        expectedMessages.add(
                new TextMessage("Norman Bintang 2\n"
                        + "Windi Chandra 0\n"));
        assertEquals(expectedMessages, acronymChatHandler.handleTextMessageEvent(me, messages));
    }

    @After
    public void recreateAcronymBank() {
        JSONObject acronyms = new JSONObject("{}");
        acronyms.put("A", "Alpha");
        acronyms.put("B", "Beta");
        acronyms.put("C", "Charlie");
        acronyms.put("D", "Delta");
        acronyms.put("E", "Ehehe");

        try {
            new FileAccessor().saveFile("acronyms", acronyms);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(acronymChatHandler.canHandleAudioMessage(null));
        assertFalse(acronymChatHandler.canHandleImageMessage(null));
        assertFalse(acronymChatHandler.canHandleStickerMessage(null));
        assertFalse(acronymChatHandler.canHandleLocationMessage(null));
    }

}


