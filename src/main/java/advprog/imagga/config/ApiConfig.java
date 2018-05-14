package advprog.imagga.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ApiConfig {

    private static String key = "API_KEY";
    private static String secret = "API_SECRET";
    public final static String URL = "https://api.imagga.com/v1";
    private static HttpURLConnection connection;

    public static String getAuth() {
        String credentialsToEncode = key + ":" + secret;
        String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));
        return basicAuth;
    }

}
