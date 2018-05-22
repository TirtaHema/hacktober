package advprog.example.bot.hangoutplace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public void getNearestPlaceTest() {
        double lat = -6.249095;
        double lon = 106.828162;
        assertNotNull(places.getNearestPlaces(lat, lon));
    }

    @Test
    public void getRandomPlacesTest() {
        assertNotNull(places.getRandomPlaces());
    }

    @Test
    public void getPlacesByRadiusTest() {
        double lat = -6.249095;
        double lon = 106.828162;
        double rad1 = 4;
        double rad2 = 3;
        assertNotNull(places.getPlacesByRadius(rad2, lat, lon));
        assertEquals(places.getPlacesByRadius(rad1, lat, lon),
                "Tidak ada tempat hangout dengan radius " + rad1 + " dari lokasi Anda.");
    }
}
