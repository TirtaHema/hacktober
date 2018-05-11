package advprog.example.bot.service;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class DocumentsSimilarityHelper {
    private static final String API_URL = "https://api.dandelion.eu/datatxt/sim/v1/";
    private static final String TOKEN = "629be95f2dc04dfda693ad5181344c45";

    public double getSimilarityFromText(String text1, String text2) {
        String url = String.format("%s?text1=%s&text2=%s&token=%s", API_URL, text1, text2, TOKEN);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        try {
            return (double) (new JSONObject(result)).get("similarity");
        } catch (Exception e) {
            return -1;
        }
    }

    public double getSimilarityFromUrl(String url1, String url2) {
        String url = String.format("%s?url1=%s&url2=%s&token=%s", API_URL, url1, url2, TOKEN);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        try {
            return (double) (new JSONObject(result)).get("similarity");
        } catch (Exception e) {
            return -1;
        }
    }

    public double getSimilarity(String content) {
        if (content.split("'").length == 4) {
            return getSimilarityFromText(
                    content.split("'")[1],
                    content.split("'")[3]
            );
        }

        if (content.split(" ").length == 2) {
            return getSimilarityFromUrl(
                    content.split(" ")[0],
                    content.split(" ")[1]
            );
        }
        return -1;
    }
}
