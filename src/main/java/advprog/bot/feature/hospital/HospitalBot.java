package advprog.bot.feature.hospital;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class HospitalBot {
    private static Hospital[] hospitals = getHospitalFromJSON();

    private static Hospital[] getHospitalFromJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        Hospital[] hospitals = new Hospital[0];
        try {
            File file = new File("./src/main/java/advprog/bot/feature/hospital/hospital-list.json");
            hospitals = objectMapper.readValue(file, Hospital[].class);
        }
        catch (IOException e) {}
        return hospitals;
    }

    public static Hospital[] getHospitals() {
        return hospitals;
    }

    public static double getDistance(double currentLatitude, double currentLongitude, double targetLatitude, double targetLongitude) {
        double a = currentLatitude-targetLatitude;
        double b = currentLongitude-targetLongitude;
        return Math.sqrt(Math.pow(a,2.0) + Math.pow(b,2.0));
    }

    public static Hospital findNearestHospital(double latitude, double longitude) {
        double minDistance = Double.MAX_VALUE;
        Hospital nearestHospital = null;
        for (Hospital hospital : hospitals) {
            double jarak = getDistance(latitude, longitude, hospital.getLatitude(), hospital.getLongitude());
            if (jarak < minDistance) {
                minDistance = jarak;
                nearestHospital = hospital;
            }
        }
        return nearestHospital;
    }
}
