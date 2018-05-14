package advprog.bot.feature.artist.recent.album.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


public class MusicBrainzApiHelper {
    private static final String URI = "https://musicbrainz.org/ws/2/artist";
    private static final String USER_AGENT = "Java/10.0.1 ( windi.chandra@gmail.com )";
    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders headers = new HttpHeaders();

    public MusicBrainzApiHelper() {
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", USER_AGENT);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
    }

    public String getArtistId(String artistName) throws Exception {
        String query = buildQuery(artistName);
        String result = restTemplate.getForObject(URI
                + "?query=" + query + "&fmt=json", String.class, headers);

        JSONObject json = new JSONObject(result);
        return json.getJSONArray("artists").getJSONObject(0).getString("id");
    }

    public String getArtistName(String artistId) throws Exception {
        String result = restTemplate.getForObject(URI + "/" + artistId, String.class, headers);
        JSONObject json = new JSONObject(result);

        return json.getString("name");
    }

    private String buildQuery(String inp) {
        String[] words = inp.split(" ");
        StringBuilder result = new StringBuilder();
        boolean spaces = false;

        for (String word : words) {
            if (spaces) {
                result.append(" ");
            }
            result.append("+");
            result.append(word);
            spaces = true;
        }

        return result.toString();
    }

    public List<Album> getMostRecentAlbumByArtistId(String artistId) throws Exception {
        String result = restTemplate.getForObject(URI
                + "/" + artistId + "?inc=release-groups&fmt=json", String.class, headers);

        JSONArray albumJsonArray = new JSONObject(result).getJSONArray("release-groups");
        List<Album> albums = new ArrayList<>();

        for (int i = 0; i < albumJsonArray.length(); i++) {
            if (albums.size() == 10) {
                break;
            }

            JSONObject albumJson = albumJsonArray.getJSONObject(i);
            if (albumJson.getString("primary-type") != null
                    && albumJson.getString("primary-type").equals("Album")) {
                albums.add(new Album(albumJson.getString("first-release-date"),
                        albumJson.getString("title")));
            }
        }

        Collections.sort(albums);

        return albums;
    }
}
