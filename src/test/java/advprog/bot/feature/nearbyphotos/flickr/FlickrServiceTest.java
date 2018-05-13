package advprog.bot.feature.nearbyphotos.flickr;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FlickrServiceTest {
    private FlickrService flickrService;

    @Before
    public void setUp() {
        flickrService = new FlickrService();
    }

    @Test
    public void testformatTitleForCarouselImages() {
        assertEquals(flickrService.formatTitleForCarouselImages("abc"), "abc");
        assertEquals(flickrService.formatTitleForCarouselImages("abcdefghijklm"), "abcdefghi...");
    }

    @Test
    public void testCreatePhotoUrl() {
        assertEquals(flickrService.createPhotoUrl("1", "30", "5", "7"), "https://farm1.staticflickr.com/30/5_7_z.jpg");
    }

    @Test
    public void testCreateUrlRequest() {
        assertEquals(flickrService.createUrlRequest(new Location(-6.21462, 106.84513)),
                "https://api.flickr.com/services/rest/?method=flickr.photos.search" +
                "&api_key=22fac7b1124ad64d303a50de7c529f8f&lat=-6.21462&lon=106.84513" +
                        "&radius=0.5&accuracy=16&per_page=5&format=json&nojsoncallback=1");
    }
}
