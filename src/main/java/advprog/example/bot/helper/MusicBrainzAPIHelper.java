package advprog.example.bot.helper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MusicBrainzAPIHelper {
    private final static String URI= "https://musicbrainz.org/ws/2/artist";
    private final static String USER_AGENT = "Java/10.0.1 ( windi.chandra@gmail.com )";
    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders headers = new HttpHeaders();

    public MusicBrainzAPIHelper() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", USER_AGENT);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
    }

    public String getArtistId(String artistName) throws Exception {
        String query = buildQuery(artistName);
        String result = restTemplate.getForObject(URI + "?query=" + query + "&fmt=json", String.class, headers);

        JSONObject json = new JSONObject(result);
        String id;
        try {
            id = json.getJSONArray("artists").getJSONObject(0).getString("id");
        } catch (ArrayIndexOutOfBoundsException exc) {
            throw new Exception("Artist not found");
        }
        return id;
    }

    private String buildQuery(String inp) {
        String[] words = inp.split(" ");
        StringBuilder result = new StringBuilder();
        boolean spaces = false;

        for (String word : words) {
            if (spaces)
                result.append(" ");
            result.append("+");
            result.append(word);
            spaces = true;
        }

        return result.toString();
    }

    public List<Album> getMostRecentAlbumByArtistID(String artistID) throws Exception {
        String result = restTemplate.getForObject(URI + "/" + artistID + "?inc=release-groups&fmt=json", String.class, headers);

        JSONArray albumJSONArray = new JSONObject(result).getJSONArray("release-groups");
        List<Album> albums = new ArrayList<>();

        for (int i = 0; i < albumJSONArray.length(); i++){
            JSONObject albumJSON = albumJSONArray.getJSONObject(i);
            albums.add(new Album(albumJSON.getString("first-release-date"), albumJSON.getString("title")));
        }

        return albums;
    }
}
