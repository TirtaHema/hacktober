package advprog.bot.feature.fakejson.helper;

import java.util.Random;
import org.springframework.web.client.RestTemplate;


public class FakeJsonApiHelper {
    static final int MAX_ID = 10;
    static final Random RAND = new Random();
    static final String[] RESOURCES = {"posts", "comments", "albums", "photos", "todos", "users"};
    static final String URL_FORMAT = "https://jsonplaceholder.typicode.com/%s/%d";

    public String getUrl() {
        String resource = RESOURCES[RAND.nextInt(RESOURCES.length)];
        int id = RAND.nextInt(MAX_ID) + 1;
        return String.format(URL_FORMAT, resource, id);
    }

    public String getFakeJsonObject() {
        String url = getUrl();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
