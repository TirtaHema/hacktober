package advprog.bot.feature.top5poster;

public class Poster {

    private final String name;
    private final double percentage;

    public Poster(String name, double percentage) {

        this.name = name;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public double getPercentage() {
        return percentage;
    }
}
