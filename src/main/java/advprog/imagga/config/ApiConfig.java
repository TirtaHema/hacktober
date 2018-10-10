package advprog.imagga.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ApiConfig {

    private static String key = "acc_c79690c6c890ef8";
    private static String secret = "bad73ff16021bc16bcf944d0b40e9a3c";
    public final static String URL = "https://api.imagga.com/v1";

    public static String getAuth() {
        String credentialsToEncode = key + ":" + secret;
        String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));
        return basicAuth;
    }

}
