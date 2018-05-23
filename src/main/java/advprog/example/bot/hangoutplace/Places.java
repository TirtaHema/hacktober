package advprog.example.bot.hangoutplace;

/**
 * Created by fazasaffanah on 18/05/2018.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Places {
    private ArrayList<Place> places;
    //location, address, name, short desc, longitude, latitude

    public void parseJson() {
        places = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            String file = "src/main/java/advprog/example/bot/hangoutplace/hangout.json";
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            Object object = parser.parse(reader);
            JSONObject json = (JSONObject) object;
            JSONArray arr = (JSONArray) json.get("Places");

            for (Object obj : arr) {
                JSONObject jsonobj = (JSONObject) obj;
                String location = (String) jsonobj.get("location");
                String address = (String) jsonobj.get("address");
                String name = (String) jsonobj.get("name");
                String description = (String) jsonobj.get("description");
                double latitude = (double) jsonobj.get("latitude");
                double longitude = (double) jsonobj.get("longitude");
                Place place = new Place(location, address, name, description, latitude, longitude);
                places.add(place);
            }
        } catch (IOException e) {
            e.printStackTrace();;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getNearestPlaces(double latitude, double longitude) {
        parseJson();
        Place nearest = places.get(0);
        double distance = Math.abs(Haversine.distance(latitude, longitude,
                nearest.getLatitude(), nearest.getLongitude()));
        for (int i = 1; i < places.size(); i++) {
            double temp = Math.abs(Haversine.distance(latitude, longitude,
                    places.get(i).getLatitude(), places.get(i).getLongitude()));
            if (distance > temp) {
                nearest = places.get(i);
                distance = temp;
            }
        }

        return "Tempat hangout terdekat dari lokasi Anda adalah " + nearest.getName() 
                + "\nLokasi : " + nearest.getLocation()
                + "\nAlamat : " + nearest.getAddress() + "\nDeskripsi : " + nearest.getDescription()
                + "\nJarak : " + Double.parseDouble(String.format("%.3f", distance)) + " km";
    }

    public ArrayList<Place> getRandomPlaces() {
        parseJson();
        ArrayList<Place> res = new ArrayList<>();
        Random random = new Random();
        int[] randomArr = random.ints(15, 0, 15).distinct().toArray();
        for (int i = 0; i < 4; i++) {
            res.add(places.get(i));
        }
        return res;
    }

    public String getPlacesByRadius(double radius, double latitude, double longitude) {
        parseJson();
        StringBuilder result = new StringBuilder();
        result.append("Tempat hangout yang berjarak " + radius + " km dari lokasi Anda adalah :");
        int count = 0;
        for (Place place : places) {
            double distance = Math.abs(Haversine.distance(latitude,
                    longitude, place.getLatitude(), place.getLongitude()));
            if (radius <= distance && (radius + 1) > distance) {
                result.append("\n\n" + ++count + ". " + place.getName()
                        + "\nLokasi : " + place.getLocation()
                        + "\nAlamat : " + place.getAddress()
                        + "\nDeskripsi : " + place.getDescription()
                        + "\nJarak : " + Double.parseDouble(String.format("%.3f", distance))
                        + " km");
            }
        }
        if (result.toString().equals("Tempat hangout yang berjarak "
                + radius + " km dari lokasi Anda adalah :")) {
            return "Tidak ada tempat hangout dengan radius " + radius + " km dari lokasi Anda.";
        } else {
            return result.toString();
        }
    }
}