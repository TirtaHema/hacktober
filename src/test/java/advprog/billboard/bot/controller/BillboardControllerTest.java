package advprog.billboard.bot.controller;

import static advprog.billboard.bot.controller.BillboardController.cekArtist;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class BillboardControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private BillboardController billboardController;

    @Test
    void testContextLoads() {
        assertNotNull(billboardController);
    }

    @Test
    void testHandleTextMessageEventFail() throws IOException {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard tropical Lorem Ipsum");

        TextMessage reply = billboardController.handleTextMessageEvent(event);

        assertEquals("Artist is not present in Billboard's Tropical Songs music chart this week", reply.getText());
    }

    @Test
    void testCekArtist() throws IOException {
        String artist = "Dummy";
        String check = cekArtist(artist);
        assertEquals("Artist is not present in Billboard's Tropical Songs music chart this week",check);
    }

    @Test
    void testHandleTextMessageEventSuccess() throws IOException {
        Document doc = Jsoup.connect("https://www.billboard.com/charts/tropical-songs").get();
        Elements container = doc.select(".chart-row__title");
        String artist = container.select(".chart-row__artist").text();
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard tropical "+artist);
        String expected = cekArtist(artist);
        TextMessage reply = billboardController.handleTextMessageEvent(event);

        assertEquals(expected, reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        billboardController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}