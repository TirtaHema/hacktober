package advprog.example.bot.NewAgeBot;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewAgeChartBotTest {

    String url = "https://www.billboard.com/charts/new-age-albums";
    NewAgeChartBot bot;

    @Before
    public void setUp(){
        bot = new NewAgeChartBot();
        bot.artist ="Sigur Ros";
    }

    @Test
    public void getChartTest(){
        assertNotNull(bot.getChart());
        for(NewAgeSong song : bot.chart){
            song.song();
        }
    }

    @Test
    public void isExistTest(){
        assertTrue(bot.isExist(bot.artist));
    }

    @Test
    public void favArtistTest(){
        assertEquals("Sigur Ros\nRoute One (Soundtrack)\n6",bot.FavoriteArtist());
    }

    @Test
    public void urlTest(){
        assertEquals(url,bot.getUrl());
    }
}
