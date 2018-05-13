package advprog.bot.feature.nearbyphotos.flickr;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PhotoTest {
    private Photo photo;

    @Before
    public void setUp() {
        photo = new Photo("https://google.com", "google");
    }

    @Test
    public void testGetMethod() {
        assertEquals(photo.getUrl(), "https://google.com");
        assertEquals(photo.getTitle(), "google");
    }
}
