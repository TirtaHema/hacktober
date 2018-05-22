package advprog.bot.feature.bikun;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class BikunApp {
    private static HalteBikun[] halteBikuns = getHalteBikunsFromJSON();
    private static int MILI_SECOND_TO_SECOND = 1000;

    public static HalteBikun findNearestHalteBikun(double latitude, double longitude) {
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
        return halteBikuns;
    }

    private static HalteBikun[] getHalteBikunsFromJSON() {
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

    public static int getWaitingTime(HalteBikun halteBikun) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        long min = -1;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));

            for (String time : halteBikun.getJadwal()) {
                Date EndTime = dateFormat.parse(time);
                long difference = EndTime.getTime() - currentTime.getTime();
                if (difference >= 0) {
                    min = difference;
                    break;
                }
            }

            if (min == -1) {
                min = dateFormat.parse(halteBikun.getJadwal()[0]).getTime() - currentTime.getTime() + 86400000;
            }
        }
        catch (ParseException e) {
        }
        return (int)(min/MILI_SECOND_TO_SECOND);
    }
}
