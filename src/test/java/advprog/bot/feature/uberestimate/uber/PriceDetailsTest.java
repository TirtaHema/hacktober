package advprog.bot.feature.uberestimate.uber;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PriceDetailsTest {
    private PriceDetails priceDetails;

    @Before
    public void setUp() {
        priceDetails = new PriceDetails(9.11, 13, "$50", "uberX");
    }

    @Test
    public void testGetMethod() {
        assertEquals(Double.toString(priceDetails.getDistance()), "9.11");
        assertEquals(Integer.toString(priceDetails.getDuration()), "13");
        assertEquals(priceDetails.getPrice(), "$50");
        assertEquals(priceDetails.getProvider(), "uberX");
    }
}
