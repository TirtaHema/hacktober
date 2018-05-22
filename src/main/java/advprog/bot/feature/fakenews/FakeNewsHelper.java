package advprog.bot.feature.fakenews;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    private static News matchContent(String content, Category category) {
        content = content.toLowerCase();
        JSONArray fakeSites = loadSourceSites("defaultSources.json");
        for (int i = 0; i < fakeSites.length(); i++) {
            JSONObject source = fakeSites.getJSONObject(i);
            String site = source.getString("site").toLowerCase();
            if (matchSource(source, category) && content.contains(site)) {
                return new News(category, source.getString("note"));
            }
        }
        return new News(Category.SAFE, "");
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