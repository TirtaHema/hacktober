package advprog.bot.feature.nearbyphotos.flickr;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocationTest {
    private Location location;

    @Before
    public void setUp() {
        location = new Location(1.3, -9.231);
    }

    @Test
    public void testGetMethod() {
        assertEquals(Double.toString(location.getLat()), "1.3");
        assertEquals(Double.toString(location.getLon()), "-9.231");
    }
}
