package advprog.bot.feature.uberestimate.uber;

public class PriceDetails {
    private Double distance;
    private Integer duration;
    private String price;
    private String provider;

    public PriceDetails(Double distance, Integer duration, String price, String provider) {
        this.distance = distance;
        this.duration = duration;
        this.price = price;
        this.provider = provider;
    }

    public Double getDistance() {
        return distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getPrice() {
        return price;
    }

    public String getProvider() {
        return provider;
    }
}
