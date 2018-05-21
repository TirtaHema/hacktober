package advprog.example.bot.hangoutplace;

/**
 * Created by fazasaffanah on 21/05/2018.
 */
public class Place {
    private String location;
    private String address;
    private String name;
    private String description;
    private double latitude;
    private double longitude;

    public Place(String location, String address, String name, String description, double latitude, double longitude) {
        this.location = location;
        this.address = address;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
