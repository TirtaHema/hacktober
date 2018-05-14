package advprog.bot.feature.nearbyphotos.flickr;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class FlickrService implements IPictureService {

    public List<Photo> get5Photos(Location location)  {
        String url = createUrlRequest(location);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String result = restTemplate.getForObject(url, String.class, headers);
        return getPhotosFromJSON(result);
    }

    public List<Photo> getPhotosFromJSON(String result) {
        List<Photo> photos = new ArrayList<Photo>();
        try {
            JSONObject json = new JSONObject(result);
            JSONArray photo = json.getJSONObject("photos").getJSONArray("photo");
            for(int i = 0; i < photo.length(); i++) {
                photos.add(new Photo(createPhotoUrl(photo.getJSONObject(i).getString("farm"), photo.getJSONObject(i).getString("server"),
                        photo.getJSONObject(i).getString("id"), photo.getJSONObject(i).getString("secret")), photo.getJSONObject(i).getString("title")));
            }
        } catch (Exception e) {
            return photos;
        }
        return photos;
    }

    // return kata dengan maximal panjang 10
    public String formatTitleForCarouselImages(String title) {
        if(title.length()>12){
            return title.substring(0, 9) + "...";
        }
        return title;
    }

    public String createUrlRequest(Location location) {
        final String BASE_URL = "https://api.flickr.com/services/rest/";
        final String API_KEY = "22fac7b1124ad64d303a50de7c529f8f";
        final String API_METHOD = "flickr.photos.search";
        final String EXTENSION_PARAM = "&radius=0.5&accuracy=16&per_page=5&format=json&nojsoncallback=1";

        return BASE_URL + "?method=" + API_METHOD + "&api_key=" + API_KEY + "&lat=" + Double.toString(location.getLat()) + "&lon=" + Double.toString(location.getLon()) + EXTENSION_PARAM;
    }

    // photo url : https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
    public String createPhotoUrl(String farmId, String serverId, String id, String secret) {
        return "https://farm" + farmId + ".staticflickr.com/" + serverId + "/" + id + "_" + secret + "_z.jpg";
    }

    public static void main(String[] args) {
        FlickrService a = new FlickrService();
        a.get5Photos(new Location(-6.21462, 106.84513));
    }
}
