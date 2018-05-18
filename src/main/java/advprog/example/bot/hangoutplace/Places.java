package advprog.example.bot.hangoutplace;

/**
 * Created by fazasaffanah on 18/05/2018.
 */

import org.json.simple.parser.JSONParser;

public class Places {
    //location, address, name, short desc, longitude, latitude
    public String getPlaces(String longitude, String latitude) {
        return longitude + " " + latitude;
    }
}
