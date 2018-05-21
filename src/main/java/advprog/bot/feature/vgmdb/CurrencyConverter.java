package advprog.bot.feature.vgmdb;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static advprog.bot.feature.vgmdb.JsonReader.readJsonFromUrl;

public class CurrencyConverter {

    public double convert(String from, int amount) throws IOException, JSONException {
        try {
            JSONObject json = readJsonFromUrl("https://www.amdoren.com/api/currency.php"
                    + "?api_key=iJe8rH3xVcathgzY3QYLkVAv4DUrTn&from=" + from
                    + "&to=IDR&amount=" + amount);
            return json.getDouble("amount");
        }catch (Exception e) {
            return -1;
        }
    }
}
