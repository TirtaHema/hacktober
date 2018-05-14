package advprog.bot.feature.nearbyphotos.flickr;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testGetPhotosFromJSONSuccess() {
        String resultJSON = "{" +
                    "\"photos\": {" +
                    "\"page\": 1," +
                    "\"pages\": 545," +
                    "\"perpage\": 5," +
                    "\"total\": \"2725\"," +
                    "\"photo\": [ " +
                        "{" +
                        "\"id\": \"41243656104\"," +
                        "\"owner\": \"151853356@N03\"," +
                        "\"secret\": \"d3b3c94320\"," +
                        "\"server\": \"904\"," +
                        "\"farm\": 1," +
                        "\"title\": \"Strangers\"," +
                        "\"ispublic\": 1," +
                        "\"isfriend\": 0," +
                        "\"isfamily\": 0 " +
                        "}" +
                    "] " +
                    "}," +
                    "\"stat\": \"ok\" " +
                "}";
        List<Photo> photo = flickrService.getPhotosFromJSON(resultJSON);
        assertEquals(photo.get(0).getUrl(), "https://farm1.staticflickr.com/904/41243656104_d3b3c94320_z.jpg");
        assertEquals(photo.get(0).getTitle(), "Strangers");
    }

    @Test
    public void testGetPhotosFromEmptyJSON() {
        String resultJSON = "{}";
        List<Photo> photo = flickrService.getPhotosFromJSON(resultJSON);
        assertEquals(photo.size(), 0);
    }
}
