package advprog.imagga;

import advprog.imagga.config.ApiConfig;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SmartCropTest {

    String image;
    SmartCrop smartCrop;

    @Before
    public void setUp(){
        image = "https://imagga.com/static/images/tagging/wind-farm-538576_640.jpg";
        smartCrop = new SmartCrop(image);
        try {
            smartCrop.startCrop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testResponseCode() throws IOException {
        assertEquals(200, smartCrop.getResponseCode());
    }

    @Test
    public void testCoor() throws IOException, JSONException {
        assertEquals("Coor : [(65, 0), (513, 448)]", smartCrop.getCoor());
    }
}
