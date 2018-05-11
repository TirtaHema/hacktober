package advprog.example.bot.language;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class detecTest {

    private detect detect;

    @Before
    public void setUp() {
        detect = new detect();
    }

    @Test
    public void Test() {
        String charts = detect.detect;
        assertNotNull(detect);
    }


}
