package advprog.bot.feature.uberestimate.uber;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.channels.Pipe;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UberServiceTest {
    private UberService uberService;

    @Before
    public void setUp() {
        uberService = new UberService();
    }

    @Test
    public void testCreateUrlRequest() {
        assertEquals(uberService.createUrlRequest(
                new Location(37.7752315, -122.418075),
                new Location(37.7752415, -122.518075)),
                "https://api.uber.com/v1.2/estimates/price"
                        + "?start_latitude=37.7752315&start_longitude=-122.418075"
                        + "&end_latitude=37.7752415&end_longitude=-122.518075"
        );
    }

    @Test
    public void testGetJsonPriceDetailsFromIsolatedLocation() {
        assertEquals(uberService.getJsonPriceDetails(
                new Location(15.326572, -76.157227),
                new Location(15.316572, -76.157227)),
                "{\"prices\":[]}"
        );
    }

    @Test
    public void testGetPriceDetailsFromValidJson() {
        List<PriceDetails> priceDetails = uberService.getPriceDetails(
                "America",
                "{"
                        + "\"prices\":["
                        +   "{"
                        +   "\"localized_display_name\": \"SELECT\","
                        +   "\"distance\": 6.19,"
                        +   "\"display_name\": \"SELECT\","
                        +   "\"product_id\": \"57c0ff4e-1493-4ef9-a4df-6b961525cf92\","
                        +   "\"high_estimate\": 42,"
                        +   "\"low_estimate\": 33,"
                        +   "\"duration\": 1200,"
                        +   "\"estimate\": \"$33-42\","
                        +   "\"currency_code\": \"USD\""
                        +   "}"
                        +  "]"
                        + "}"
        );
        assertEquals(Integer.toString(priceDetails.get(0).getDuration()), "1200");
        assertEquals(priceDetails.get(0).getPlaceName(), "America");
        assertEquals(Double.toString(priceDetails.get(0).getDistance()), "6.19");
    }

    @Test
    public void testGetPriceDetailsFromInvalidJson() {
        List<PriceDetails> priceDetails = uberService.getPriceDetails(
                "", "{\"prices\":[{\"distance\":\"abc\"}]}"
        );
        assertEquals(priceDetails.size(), 0);
    }
}
