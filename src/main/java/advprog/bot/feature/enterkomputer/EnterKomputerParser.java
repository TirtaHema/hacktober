package advprog.bot.feature.enterkomputer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EnterKomputerParser {
    ArrayList<JSONObject> jsonObj = new ArrayList<>();
    boolean falseCategory = false;

    public EnterKomputerParser(String category) throws IOException {
        try {
            URL url = new URL("https://enterkomputer.com/api/product/"
            + category + ".json");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = br.readLine();
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(str);
                JSONArray arr = (JSONArray) obj;
                for (int i = 0; i < arr.size() ; i++) {
                    JSONObject thisJson = (JSONObject) arr.get(i);
                    jsonObj.add(thisJson);
                }
            } catch (ParseException p) {

            }
        } catch (FileNotFoundException f) {
            falseCategory = true;
        }
    }

    public ArrayList<JSONObject> getJsonObj() {
        return jsonObj;
    }

    public ArrayList<String> getProductNames() {
        ArrayList<String> result = new ArrayList<>();
        for (JSONObject obj: jsonObj) {
            result.add(((String) obj.get("name")).toLowerCase());
        }
        return result;
    }

    public boolean isFalseCategory() {
        return falseCategory;
    }

}
