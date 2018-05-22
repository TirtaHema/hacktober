package advprog.example.bot.hangoutplace;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by fazasaffanah on 22/05/2018.
 */
public class PlaceTest {
    private static Place place;

    @Before
    public void setUp() {
        place = new Place("a", "b", "c", "d", 1, 1);
    }

    @Test
    public void classTest() {
        assertEquals(place.getLocation() + place.getAddress()
                + place.getName() + place.getDescription()
                + place.getLatitude() + place.getLongitude(), "abcd1.01.0");
    }

    @Test
    public void getKeteranganTest() {
        assertEquals("c" + "\nLokasi : " + "a" + "\nAlamat : "
                + "b" + "\n Deskripsi : " + "d", place.getKeterangan());
    }
}
