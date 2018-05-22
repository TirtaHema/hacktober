package advprog.bot.feature.fakenews;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

public class FakeNewsHelper {

    private static JSONArray loadSourceSites(String fileName) {
        String content;
        try {
            Path file = Paths.get(fileName);
            content = String.join("\n", Files.readAllLines(file));
        } catch (Exception e) {
            content = "[]";
        }

        return new JSONArray(content);
    }

    public static JSONObject createSource(String host, String type) {
        JSONObject source = new JSONObject();
        source.put("site", host);
        source.put("type1", type);
        source.put("type2", type);
        source.put("type3", type);
        source.put("note", "Added by user");
        return source;
    }

    public static void addFilter(String url, String type) throws IOException, URISyntaxException {
        String hostName = getHostName(url);
        JSONObject source = createSource(hostName, type);
        addNewsSource(source);
    }

    public static void addNewsSource(JSONObject source) throws IOException {
        JSONArray sources = loadSourceSites("customSources.json");
        sources.put(source);
        saveNewsSource("customSources.json", sources);
    }

    public static void saveNewsSource(String fileName, JSONArray data) throws IOException {
        Path file = Paths.get(fileName);
        Files.write(file, Arrays.asList(data.toString()), Charset.forName("UTF-8"));
    }

    public static String getHostName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String host = uri.getHost();
        return host.startsWith("www.") ? host.substring(4) : host;
    }

    private static boolean matchSource(JSONObject source, Category category) {
        boolean match = false;
        if (source.getString("type1").equalsIgnoreCase(category.toString())) {
            match = true;
        }
        if (source.getString("type2").equalsIgnoreCase(category.toString())) {
            match = true;
        }
        if (source.getString("type3").equalsIgnoreCase(category.toString())) {
            match = true;
        }
        return match;
    }

    private static News matchContent(String content, Category category, JSONArray fakeSites) {
        for (int i = 0; i < fakeSites.length(); i++) {
            JSONObject source = fakeSites.getJSONObject(i);
            String site = source.getString("site").toLowerCase();
            if (matchSource(source, category) && content.contains(site)) {
                return new News(category, source.getString("note"));
            }
        }
        return new News(Category.SAFE, "");
    }

    private static News matchContent(String content, Category category) {
        content = content.toLowerCase();
        JSONArray defaultSources = loadSourceSites("defaultSources.json");
        if (!matchContent(content, category, defaultSources).isSafe()) {
            return matchContent(content, category, defaultSources);
        }
        JSONArray customSources = loadSourceSites("customSources.json");
        return matchContent(content, category, customSources);
    }

    public static News checkFake(String content) {
        return matchContent(content, Category.FAKE);
    }

    public static News checkSatire(String content) {
        return matchContent(content, Category.SATIRE);
    }

    public static News checkConspiracy(String content) {
        return matchContent(content, Category.CONSPIRACY);
    }

    public static News checkBias(String content) {
        return matchContent(content, Category.BIAS);
    }

    public static News checkPolitical(String content) {
        return matchContent(content, Category.POLITICAL);
    }

    public static News checkUnreliable(String content) {
        return matchContent(content, Category.UNRELIABLE);
    }

    public static News check(String content) {
        if (!checkFake(content).isSafe()) {
            return checkFake(content);
        }
        if (!checkSatire(content).isSafe()) {
            return checkSatire(content);
        }
        if (!checkConspiracy(content).isSafe()) {
            return checkSatire(content);
        }
        if (!checkBias(content).isSafe()) {
            return checkBias(content);
        }
        if (!checkPolitical(content).isSafe()) {
            return checkPolitical(content);
        }
        return checkUnreliable(content);
    }
}
