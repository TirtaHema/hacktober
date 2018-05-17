package advprog.bot.feature.nearbyphotos.flickr;

public class Location {
    private Double lat;
    private Double lon;

    public Location(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    Double getLat() {
        return this.lat;
    }

    Double getLon() {
        return this.lon;
    }
}
