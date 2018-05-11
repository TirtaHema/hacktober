package advprog.example.bot.image;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class hot100Test {

    private image image;

    @Before
    public void setUp() {
        image = new image();
    }

    @Test
    public void Test() {
        String image = image.image();
        assertNotNull(image);
    }


}
