package advprog.bot.feature.bikun;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class BikunApp {
    private static HalteBikun[] halteBikuns = getHalteBikunsFromJSON();
    private static int MILI_SECOND_TO_MINUTE = 60 * 1000;

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

    public static HalteBikun getHalteByName(String namaTarget) {
        for (HalteBikun halteBikun : halteBikuns) {
            if (halteBikun.getNama().equalsIgnoreCase(namaTarget)) {
                return halteBikun;
            }
        }
        return null;
    }

    public static double getDistance(double currentLatitude, double currentLongitude, double targetLatitude, double targetLongitude) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(currentLatitude - targetLatitude);
        double lonDistance = Math.toRadians(currentLongitude - targetLongitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(currentLatitude)) * Math.cos(Math.toRadians(targetLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    public static int getWaitingTime(HalteBikun halteBikun) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        long min = -1;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));

            for (String time : halteBikun.getJadwal()) {
                Date endTime = dateFormat.parse(time);
                long difference = endTime.getTime() - currentTime.getTime();
                if (difference >= 0) {
                    min = difference;
                    break;
                }
            }

            if (min == -1) {
                min = dateFormat.parse(halteBikun.getJadwal()[0]).getTime() - currentTime.getTime() + (24*60*60*1000);
            }
        }
        catch (ParseException e) {
        }
        return (int)(min/MILI_SECOND_TO_MINUTE);
    }
}
