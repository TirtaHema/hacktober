package advprog.example.bot.hangoutplace;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Created by fazasaffanah on 22/05/2018.
 */
public class HaversineTest {

    @Test
    public void haversineMethodsTest() {
        String res = String.valueOf(Haversine.haversin(60.0));
        assertEquals(res, "0.9762064902075782");
        String res2 = String.valueOf(Haversine.distance(3.0,4.0,5.0,6.0));
        assertEquals(res2, "314.11580854053534");
    }
}
