package advprog.bot.feature.hospital;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class HospitalBot {
    private static Hospital[] hospitals = getHospitalFromJson();

    private static Hospital[] getHospitalFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        Hospital[] hospitals = new Hospital[0];
        try {
            File file = new File("./src/main/java/advprog/bot/feature/hospital/hospital-list.json");
            hospitals = objectMapper.readValue(file, Hospital[].class);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return hospitals;
    }

    public static Hospital getHospital(String namaTarget) {
        for (Hospital hospital : hospitals) {
            if (hospital.getName().equalsIgnoreCase(namaTarget)) {
                return hospital;
            }
        }
        return null;
    }

    public static Hospital[] getHospitals() {
        return hospitals;
    }

    public static double getDistance(double currentLatitude,
                                     double currentLongitude,
                                     double targetLatitude,
                                     double targetLongitude) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(currentLatitude - targetLatitude);
        double lonDistance = Math.toRadians(currentLongitude - targetLongitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(currentLatitude))
                * Math.cos(Math.toRadians(targetLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    public static Hospital findNearestHospital(double latitude, double longitude) {
        double minDistance = Double.MAX_VALUE;
        Hospital nearestHospital = null;
        for (Hospital hospital : hospitals) {
            double jarak = getDistance(latitude,
                    longitude, hospital.getLatitude(), hospital.getLongitude());
            if (jarak < minDistance) {
                minDistance = jarak;
                nearestHospital = hospital;
            }
        }
        return nearestHospital;
    }

}
