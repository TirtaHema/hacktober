package advprog.bot.feature.image;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.TextMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


public class Image {
    private static String web = "https://xkcd.com/";
    private static String site = "/info.0.json";

    public static Message gambar(String input) {
        try {
            int id  =  Integer.parseInt(input);
            String imageUrl = getUrlId(id);
            if (imageUrl.length()==0){
                return new TextMessage("komik tidak ada");
            }
            else {
                return new ImageMessage(imageUrl,imageUrl);
            }
        }
        catch (NumberFormatException nfe){
            return new TextMessage("Formatnya salah kakak /xkcd [nomor id]");
        }

    }

    private static String getUrlId(int id){
        Map<String, Object> json = getJson(id);
        if (json.isEmpty()){
            return "";
        }
        else {
            return Map.of(json.get("img").toString());
        }
    }

    private static Map<String , Object> getJson (int id){
        Map <String , Object> res = new HashMap<>();
        String website  = web + id + site;
        try{
            URL xkcd = new URL(website);
            HttpURLConnection con =  (HttpURLConnection) xkcd.openConnection();
            int code = xkcd.getResponseCode();
            if (code == 200){
                BufferedReader in = new BufferedReader(new InputStreamReader(xkcd.getInputStream()));
                String input = in.readLine();
                ObjectMapper om = new ObjectMapper();
                Map<String , Object> map = om.readValue(input, new TypeRefrence<Map<String , String>>(){});
                return Map.of(map);
            }
            return res;
        }
        catch (IOException ioe){
            return res;
        }
    }
}
