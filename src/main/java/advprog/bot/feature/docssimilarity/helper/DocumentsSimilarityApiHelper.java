package advprog.bot.feature.docssimilarity.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


public class DocumentsSimilarityApiHelper {
    private static final String API_URL = "https://api.dandelion.eu/datatxt/sim/v1/";
    private static final String TOKEN = "629be95f2dc04dfda693ad5181344c45";

    private String formatPercentage(double value) {
        return Double.toString(value * 100).split("\\.")[0] + "%";
    }

    private String getFullApiUrl(String param1, String val1, String param2, String val2) {
        return String.format(
                "%s?%s=%s&%s=%s&token=%s", API_URL, param1, val1, param2, val2, TOKEN
        );
    }

    private String fetchSimilarity(String url) {
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

    public String getSimilarityFromText(String text1, String text2) {
        String url = getFullApiUrl("text1", text1, "text2", text2);
        return fetchSimilarity(url);
    }

    public String getSimilarityFromUrl(String url1, String url2) {
        String url = getFullApiUrl("url1", url1, "url2", url2);
        return fetchSimilarity(url);
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

        return "Format harus mengikuti pilihan berikut:\n"
                + "/docs_sim 'text1' 'text2'"
                + "/docs_sim url1 url2";
    }
}
