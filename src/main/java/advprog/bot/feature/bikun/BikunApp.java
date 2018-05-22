package advprog.bot.feature.bikun;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class BikunApp {
    public static HalteBikun findNearestHalteBikun(double latitude, double longitude) {
        HalteBikun[] halteBikuns = getHalteBikuns();

        double minDistance = Double.MAX_VALUE;
        HalteBikun nearestHalte = null;
        for (HalteBikun halte : halteBikuns) {
            double jarak = getDistance(latitude, longitude, halte.getLatitude(), halte.getLongitude());
            if (jarak < minDistance) {
                minDistance = jarak;
                nearestHalte = halte;
            }
        }
        return nearestHalte;
    }

    public static HalteBikun[] getHalteBikuns() {
        ObjectMapper objectMapper = new ObjectMapper();
        HalteBikun[] halteBikuns = new HalteBikun[0];
        try {
            File file = new File("./src/main/java/advprog/bot/feature/bikun/dataHalteBikun.json");
            halteBikuns = objectMapper.readValue(file, HalteBikun[].class);
        }
        catch (IOException e) {}
        return halteBikuns;
    }

    public static double getDistance(double currentLatitude, double currentLongitude, double targetLatitude, double targetLongitude) {
        double a = currentLatitude-targetLatitude;
        double b = currentLongitude-targetLongitude;
        return Math.sqrt(Math.pow(a, 2.0) + Math.pow(b, 2.0));
    }
}
