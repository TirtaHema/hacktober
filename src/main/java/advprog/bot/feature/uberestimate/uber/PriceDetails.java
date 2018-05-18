package advprog.bot.feature.uberestimate.uber;

public class PriceDetails {
    private String placeName;
    private Double distance;
    private Integer duration;
    private String price;

    public PriceDetails(String placeName, Double distance, Integer duration, String price) {
        this.placeName = placeName;
        this.distance = distance;
        this.duration = duration;
        this.price = price;
    }

    public Double getDistance() {
        return distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPrice() {
        return price;
    }
}
