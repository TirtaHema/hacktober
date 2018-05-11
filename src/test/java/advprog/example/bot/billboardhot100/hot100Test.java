package advprog.example.bot.billboardhot100;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class hot100Test {

    private Hot100 Hot100;

    @Before
    public void setUp(){
        Hot100 = new Hot100();
    }

    @Test
    public void Test(){
        String charts = Hot100.getChart();
        assertNotNull(charts);
    }


}
