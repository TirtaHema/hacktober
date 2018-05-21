package advprog.bot.feature.dice;

import java.util.Random;

/*
    Random Number Generator
 */
public class RandomGenerator {

    private Random rand;

    public RandomGenerator() {
        this.rand = new Random();
    }

    public String spinCoin() {
        boolean n = rand.nextBoolean();

        if (n == true) {
            return "face";
        }
        return "tail";
    }

    public int getRandomInt(int bound) {
        return rand.nextInt(bound) + 1;
    }

    public int[] rollDice(int times, int sides) {
        if (times < 1 || sides < 1) {
            return new int[0];
        }
        int[] numbers = new int[times];

        for (int i = 0; i < times; i++) {
            int randomNumber = getRandomInt(sides);
            numbers[i] = randomNumber;
        }
        return numbers;
    }

    public int[][] multiRollDice(int iterations, int times, int sides) {
        if (iterations < 1 || times < 1 || sides < 1) {
            return new int[0][0];
        }

        int[][] numbers = new int[iterations][times];
        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < times; j++) {
                int randomNumber = getRandomInt(sides);
                numbers[i][j] = randomNumber;
            }
        }
        return numbers;
    }

    public int isLucky(int target, int times, int sides) {
        if (target < 1 || times < 1 || sides < 1) {
            return -1;
        }

        int[] result = rollDice(times, sides);
        int count = 0;

        for (int i = 0; i < result.length; i++) {
            if (result[i] == target) {
                count++;
            }
        }
        return count;
    }
}
