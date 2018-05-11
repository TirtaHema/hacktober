package advprog.example.bot.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


public class DocumentsSimilarityHelper {
    private static final String API_URL = "https://api.dandelion.eu/datatxt/sim/v1/";
    private static final String TOKEN = "629be95f2dc04dfda693ad5181344c45";

    public String formatPercentage(double value) {
        return Double.toString(value * 100).split("\\.")[0] + "%";
    }

    public String getSimilarityFromText(String text1, String text2) {
        String url = String.format("%s?text1=%s&text2=%s&token=%s", API_URL, text1, text2, TOKEN);
        RestTemplate restTemplate = new RestTemplate();
        String result;
        boolean error = false;

        try {
            result = restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            result = e.getResponseBodyAsString();
            error = true;
        }

        try {
            if (error) {
                return (String) (new JSONObject(result)).get("message");
            } else {
                return formatPercentage((double) (new JSONObject(result)).get("similarity"));
            }
        } catch (JSONException e) {
            return e.getMessage();
        }
    }

    public String getSimilarityFromUrl(String url1, String url2) {
        String url = String.format("%s?url1=%s&url2=%s&token=%s", API_URL, url1, url2, TOKEN);
        RestTemplate restTemplate = new RestTemplate();
        String result;
        boolean error = false;

        try {
            result = restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            result = e.getResponseBodyAsString();
            error = true;
        }

        try {
            if (error) {
                return (String) (new JSONObject(result)).get("message");
            } else {
                return formatPercentage((double) (new JSONObject(result)).get("similarity"));
            }
        } catch (JSONException e) {
            return e.getMessage();
        }
    }

    public String getSimilarity(String content) {
        Pattern textPattern = Pattern.compile("'(.*)' '(.*)'");
        Matcher textMatcher = textPattern.matcher(content);

        Pattern urlPattern = Pattern.compile("(\\S+) (\\S+)");
        Matcher urlMathcer = urlPattern.matcher(content);

        if (textMatcher.matches()) {
            String text1 = textMatcher.group(1);
            String text2 = textMatcher.group(2);
            return getSimilarityFromText(text1, text2);

        } else if (urlMathcer.matches()) {
            String url1 = urlMathcer.group(1);
            String url2 = urlMathcer.group(2);
            return getSimilarityFromUrl(url1, url2);
        }

        return "format error";
    }
}
