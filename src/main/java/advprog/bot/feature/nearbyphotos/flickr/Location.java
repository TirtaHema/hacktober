package advprog.bot.feature.nearbyphotos.flickr;

public class Location {
    private Double lat, lon; // latitude and longitude

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
