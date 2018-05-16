package advprog.bot.feature.top5poster;

import org.jetbrains.annotations.NotNull;

public class Poster implements Comparable<Poster> {

    private final String name;
    private final double percentage;

    private final static double EPS = 0.0001;

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

    @Override
    public int compareTo(@NotNull Poster poster) {
        if (this.percentage > poster.percentage) {
            return -1;
        } else if (this.percentage < poster.percentage) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        Poster other = (Poster) o;
        return this.name.equals(other.name) && (Math.abs(this.percentage - other.percentage) < EPS);
    }

    @Override
    public String toString() {
        return this.name + " " + this.percentage + "%";
    }
}
