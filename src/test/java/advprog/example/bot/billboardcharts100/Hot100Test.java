package advprog.example.bot.billboardcharts100;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class Hot100Test {

    private Hot100 hot100;

    @Before
    public void setUp() {
        hot100 = new Hot100();
    }

    @Test
    public void testHot100() {
        String charts = hot100.getChart();
        assertNotNull(charts);
    }


}
