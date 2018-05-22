package advprog.bot.feature.scheduler.resources;

import org.jetbrains.annotations.NotNull;

public class Schedule implements Comparable<Schedule> {
    private String time;
    private String description;

    public Schedule(String time, String description) {
        this.time = time;
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(@NotNull Schedule other) {
        return this.time.compareTo(other.getTime());
    }
}
