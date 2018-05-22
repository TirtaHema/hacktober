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

    public Place(String location, String address, String name,
                 String description, double latitude, double longitude) {
        this.location = location;
        this.address = address;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getKeterangan() {
        return name + "\nLokasi : " + location + "\nAlamat : "
                + address + "\n Deskripsi : " + description;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
