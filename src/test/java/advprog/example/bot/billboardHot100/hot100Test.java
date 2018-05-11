package advprog.example.bot.billboardHot100;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class hot100Test {

    private hot100 hot100;

    @Before
    public void setUp() {
        hot100 = new hot100();
    }

    @Test
    public void Test() {
        String charts = hot100.GetChart();
        assertNotNull(charts);
    }


}
