package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class EchoControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private EchoController echoController;

    @Test
    void testContextLoads() {
        assertNotNull(echoController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", reply.getText());
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        echoController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    void testHadleTextForHotCountry() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/billboard newage Sigur Ros");

        TextMessage reply = echoController.handleTextMessageEvent(event);

        assertEquals("Sigur Ros\nRoute One (Soundtrack)\n6", reply.getText());
    }

    String url = "https://www.billboard.com/charts/new-age-albums";
    NewAgeChartBot bot;

    NewAgeSong song;

    @Before
    public void setUp() {
        bot = new NewAgeChartBot();
        bot.artist = "Sigur Ros";
    }

    @org.junit.Test
    public void getChartTest() {
        Assert.assertNotNull(bot.getChart());
        for (NewAgeSong song : bot.chart) {
            song.song();
        }
    }

    @org.junit.Test
    public void isExistTest() {
        assertTrue(bot.isExist(bot.artist));
    }

    @org.junit.Test
    public void favArtistTest() {
        assertEquals("Sigur Ros\nRoute One (Soundtrack)\n6",bot.favoriteArtist());
    }

    @org.junit.Test
    public void urlTest() {
        assertEquals(url,bot.getUrl());
    }

    @BeforeEach
    public void setTest() {
        song = new NewAgeSong("judul","artis",1);
    }


    @org.junit.jupiter.api.Test
    public void titleTest() {
        Assertions.assertEquals("judul",song.getTitle());
        song.setTitle("ganti judul");
        Assertions.assertEquals("ganti judul",song.getTitle());
    }

    @org.junit.jupiter.api.Test
    public void artisTest() {
        Assertions.assertEquals("artis",song.getArtist());
        song.setArtist("ganti artis");
        Assertions.assertEquals("ganti artis", song.getArtist());
    }

    @org.junit.jupiter.api.Test
    public void rankTest() {
        Assertions.assertEquals(1,song.getRank());
        song.setRank(2);
        Assertions.assertEquals(2,song.getRank());
    }

    @org.junit.jupiter.api.Test
    public void songTest() {
        Assertions.assertEquals("artis\njudul\n1",song.song());
    }
}