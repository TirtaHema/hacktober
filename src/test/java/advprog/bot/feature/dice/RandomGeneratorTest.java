package advprog.bot.feature.dice;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class RandomGeneratorTest {

    private RandomGenerator rg;

    public RandomGeneratorTest() {
        this.rg = new RandomGenerator();
    }

    @Test
    public void spinCoinTest() {
        String result = rg.spinCoin();
        String expected1 = "tail";
        String expected2 = "face";
        assertTrue(result.equals(expected1) || result.equals(expected2));
    }

    @Test
    public void rollDiceFail() {
        int[] result = rg.rollDice(0,0);
        int lengthExpected = 0;
        assertEquals(result.length, lengthExpected);
    }

    @Test
    public void rollDiceSuccess() {
        int[] result = rg.rollDice(5, 6);

        for (int i = 0; i < result.length; i++) {
            assertTrue(result[i] <= 6 && result[i] >= 1);
        }
    }

    @Test
    public void multiRollDiceFail() {
        int[][] result = rg.multiRollDice(0,0, 0);
        int lengthExpected = 0;
        assertEquals(result.length, lengthExpected);
    }

    @Test
    public void multiRollDiceSuccess() {
        int[][] result = rg.multiRollDice(3, 5, 6);

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                assertTrue(result[i][j] <= 6 && result[i][j] >= 1);
            }
        }
    }

    @Test
    public void isLuckyTestFail() {
        int result = rg.isLucky(0,0,0);
        int expected = -1; //error
        assertEquals(result, expected);
    }

    @Test
    public void isLuckyTestSuccess() {
        int result = rg.isLucky(5,5,5);
        assertTrue(result >= 0);
    }
}
