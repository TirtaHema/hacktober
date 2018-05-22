package advprog.bot.feature.bikun;

import java.util.List;

public class HalteBikun {
    private String nama;
    private double latitude;
    private double longitude;
    private String imgUrl;
    private String[] jadwal;

    public HalteBikun(String nama, double latitude, double longitude, String imgUrl, String[] jadwal) {
        this.nama = nama;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgUrl = imgUrl;
        this.jadwal = jadwal;
    }

    public String getNama() {
        return nama;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String[] getJadwal() {
        return jadwal;
    }
}
