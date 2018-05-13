package advprog.example.bot.photos.nearby;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class FlickrService {
    public List<String> Get5Photos(Location location)  {
        final String BASE_URL = "https://api.flickr.com/services/rest/";
        final String API_KEY = "22fac7b1124ad64d303a50de7c529f8f";
        final String API_METHOD = "flickr.photos.search";
        final String EXTENSION_PARAM = "&radius=0.5&accuracy=16&per_page=5&format=json&nojsoncallback=1";

        String url = BASE_URL + "?method=" + API_METHOD + "&api_key=" + API_KEY + "&lat=" + Double.toString(location.getLat()) + "&lon=" + Double.toString(location.getLon()) + EXTENSION_PARAM;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String result = restTemplate.getForObject(url, String.class, headers);

        List<String> urls = new ArrayList<String>();

        try {
            JSONObject json = new JSONObject(result);
            JSONArray photo = json.getJSONObject("photos").getJSONArray("photo");
            for(int i = 0; i < photo.length(); i++) {
                urls.add(createPhotoUrl(photo.getJSONObject(i).getString("farm"), photo.getJSONObject(i).getString("server"),
                        photo.getJSONObject(i).getString("id"), photo.getJSONObject(i).getString("secret")));
            }
        } catch (Exception e) {
            return urls;
        }

        return urls;

        // List<Photo> top5 = new ArrayList<Photo>();
        // return top5;
    }

    // photo url : https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
    private String createPhotoUrl(String farmId, String serverId, String id, String secret) {
        return "https://farm" + farmId + ".staticflickr.com/" + serverId + "/" + id + "_" + secret + "_z.jpg";
    }
}
