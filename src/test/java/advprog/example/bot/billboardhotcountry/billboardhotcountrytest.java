package advprog.example.bot.billboardhotcountry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by fazasaffanah on 11/05/2018.
 */
public class billboardhotcountrytest {
    private billboardhotcountry hotcountry;

    @Before
    public void setUp() {
        hotcountry = billboardhotcountry();
    }

    @Test
    public void getTop10Test() {
        String top10 = hotcountry.getTop10();
        assertEquals(top10, "Top 10 Billboard Hot Country");
    }
}
