package advprog.bot.feature.xkcd.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Optional;


public class Xkcd {
    private static String web = "https://xkcd.com/";
    private static String site = "/info.0.json";


    public static String gambar(String input) {
        String[] command = input.split(" ");
        if (command.length == 2) {
            try {
                int id = Integer.parseInt(command[1]);
                String imageUrl = getUrlId(id);
                if (imageUrl.length() == 0) {
                    return "Image tidak ada !";
                } else {
                    return imageUrl;
                }
            } catch (NumberFormatException nfe) {
                return "harus angka !";
            }
        } else {
            return "Formatnya salah kakak /xkcd [nomor id] !";
        }

    }


    private static String getUrlId(int id) {
        Optional<Map<String, Object>> json = getJson(id);
        if (json.isPresent()) {
            Map<String,Object> jsonfix = json.get();
            return jsonfix.get("img").toString();
        } else {
            return "";
        }
    }

    private static Optional<Map<String, Object>> getJson(int id) {
        String website = web + id + site;
        try {
            URL xkcd = new URL(website);
            HttpURLConnection con = (HttpURLConnection) xkcd.openConnection();
            int code = con.getResponseCode();
            if (code == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String input = in.readLine();
                ObjectMapper om = new ObjectMapper();
                Map<String, Object> map = om.readValue(input,
                        new TypeReference<Map<String, String>>() {
                        });
                return Optional.of(map);
            }
            return Optional.empty();
        } catch (IOException ioe) {
            return Optional.empty();
        }
    }
}
