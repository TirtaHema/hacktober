package advprog.bot.feature.hospital;

public class Hospital {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String phone;
    private String imageLink;
    private String description;

    public Hospital(String name, String address, double latitude, double longitude,
                    String phone, String imageLink, String description) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.imageLink = imageLink;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPhone() {
        return phone;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getDescription() {
        return description;
    }
}
