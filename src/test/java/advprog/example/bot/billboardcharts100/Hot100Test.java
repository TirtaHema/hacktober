package advprog.example.bot.billboardcharts100;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class Hot100Test {

    private Hot100 Hot100;

    @Before
    public void setUp() {
        Hot100 = new Hot100();
    }

    @Test
    public void Test() {
        String charts = Hot100.getChart();
        assertNotNull(charts);
    }


}
