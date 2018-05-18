package advprog.bot.feature.uberestimate.uber;

public class Location {
    private Double lat;
    private Double lon;
    private String street;
    private String placeName;

    public Location(Double lat, Double lon, String street, String placeName) {
        this.lat = lat;
        this.lon = lon;
        this.street = street;
        this.placeName = placeName;
    }

    public Double getLat() {
        return this.lat;
    }

    public Double getLon() {
        return this.lon;
    }

    public String getStreet() {
        return street;
    }

    public String getPlaceName() {
        return placeName;
    }
}

