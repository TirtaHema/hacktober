package advprog.example.bot.hangoutplace;

/**
 * Created by fazasaffanah on 18/05/2018.
 */

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Places {
    //location, address, name, short desc, longitude, latitude
    public void parseJson() {
        try {
            String file = "tempathangout.json";
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            JSONObject json = new JSONObject(String.valueOf(bufferedReader.read()));
            JSONArray jsonArray = (JSONArray) json.get("Places");

        }
        catch (IOException e) {
            e.printStackTrace();;
        }
    }

    public String getPlaces(String longitude, String latitude) {
        return longitude + " " + latitude;
    }
}
