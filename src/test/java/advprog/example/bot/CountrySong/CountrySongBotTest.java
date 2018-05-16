package advprog.example.bot.CountrySong;

import advprog.example.bot.CountryBot.CountrySong;
import advprog.example.bot.CountryBot.CountrySongChartBot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CountrySongBotTest {

    String url = "https://www.billboard.com/charts/country-songs";
    CountrySongChartBot bot;

    @Before
    public void setUp(){
        bot = new CountrySongChartBot();
        bot.artist ="Kane Brown";
    }

    @Test
    public void getChartTest(){
        assertNotNull(bot.getChart());
        for(CountrySong song : bot.chart){
            song.song();
        }
    }

    @Test
    public void isExistTest(){
        assertTrue(bot.isExist(bot.artist));
    }

    @Test
    public void favArtistTest(){
        assertEquals("Kane Brown\nHeaven\n2",bot.FavoriteArtist());
    }

    @Test
    public void urlTest(){
        assertEquals(url,bot.getUrl());
    }
}
