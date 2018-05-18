package advprog.example.bot.hangoutplaces;

import static org.junit.Assert.assertEquals;

import advprog.example.bot.hangoutplace.Places;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by fazasaffanah on 18/05/2018.
 */
public class PlacesTest {
    private Places places;

    @Before
    public void setUp() {
        places = new Places();
    }

    @Test
    public void getPlacesTest() {
        assertEquals(places.getPlaces("3", "1"), "3 1");
    }
}
