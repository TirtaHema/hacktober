package advprog.bot.feature.anisong.util.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SongRecognizer {

    public String songValidate(String song) throws IOException {
        String url = "https://schoolido.lu/api/songs/?search=";
        song.replace(" ","+");
        url += song;
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            String json = reader(rd);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonobj = ((JSONArray) jsonObject.get("results")).getJSONObject(0);
            String id = jsonobj.get("itunes_id").toString();
            return id;
        } catch (JSONException e) {
            return null;
        }
    }

    private static String reader(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp = 0;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
