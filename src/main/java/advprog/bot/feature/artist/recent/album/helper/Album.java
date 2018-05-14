package advprog.bot.feature.artist.recent.album.helper;

import org.jetbrains.annotations.NotNull;

public class Album implements Comparable<Album> {
    private String date;
    private String name;

    public Album(String date, String name) {
        this.date = date;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(@NotNull Album o) {
        return o.date.compareTo(date);
    }
}
