package advprog.bot.feature.itunes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ITunesParser {
    ArrayList<JSONObject> jsonObjs = new ArrayList<>();
    ArrayList<String> artists = new ArrayList<>();
    ArrayList<String> tracks = new ArrayList<>();
    ArrayList<String> links = new ArrayList<>();
    boolean falseCategory = false;

    public ITunesParser(String artis) throws IOException {
        try {
            URL url = new URL("https://itunes.apple.com/search?term=" + artis
                + "&entity=musicTrack");
            BufferedReader br = new
                BufferedReader(new InputStreamReader(url.openConnection().getInputStream(),
                StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            line = sb.toString().trim();
            int position = 0;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '[') {
                    position = i;
                    break;
                }
            }
            line = line.substring(position, line.length() - 1);
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(line);
                JSONArray array = (JSONArray)obj;
                for (int i = 0; i < array.size(); i++) {
                    JSONObject j = (JSONObject)array.get(i);
                    jsonObjs.add(j);
                }
                fillLists();
            } catch (ParseException pe) {
                System.out.println(pe);
            }
        } catch (FileNotFoundException f) {
            System.out.println(f);
            falseCategory = true;
        }
    }

    private void fillLists() {
        for (JSONObject obj: jsonObjs) {
            fillArtist(obj);
            fillTrack(obj);
            fillLink(obj);
        }
    }

    private void fillArtist(JSONObject obj) {
        artists.add((String) obj.get("artistName"));
    }

    private void fillTrack(JSONObject obj) {
        tracks.add((String) obj.get("trackName"));
    }

    private void fillLink(JSONObject obj) {
        links.add((String) obj.get("previewUrl"));
    }

    public ArrayList<String> getRandom() {
        if (jsonObjs.size() == 0) {
            return null;
        }
        ArrayList<String> result = new ArrayList<>();
        Random r = new Random();
        int random = r.nextInt(jsonObjs.size() - 1);
        result.add(artists.get(random));
        result.add(tracks.get(random));
        result.add(links.get(random));
        return result;
    }

    public boolean isFalseCategory() {
        return falseCategory;
    }
}
