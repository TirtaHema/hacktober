package advprog.bot.feature.anisong.util.service;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


@Service
public class SongRecognizer {

    public String songValidate(String song) throws IOException {
        String url = "https://schoolido.lu/api/songs/?search=";
        song.replace(" ","+");
        url += song;
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
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

    public String getImage(String song) throws IOException {
        String url = "https://schoolido.lu/api/songs/?search=";
        song.replace(" ","+");
        url += song;
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String json = reader(rd);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonobj = ((JSONArray) jsonObject.get("results")).getJSONObject(0);
            String urlImage = "https:" + jsonobj.get("image").toString();
            return urlImage;
        } catch (JSONException e) {
            return null;
        }
    }

    public String getSongName(String song) throws IOException {
        String url = "https://schoolido.lu/api/songs/?search=";
        song.replace(" ","+");
        url += song;
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String json = reader(rd);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonobj = ((JSONArray) jsonObject.get("results")).getJSONObject(0);
            String urlImage = jsonobj.get("name").toString();
            return urlImage;
        } catch (JSONException e) {
            return null;
        }
    }
}
