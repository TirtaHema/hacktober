package advprog.bot.feature.Language;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;



public class Detect {
    private static String web = "https://api.dandelion.eu/datatxt/li/v1?";
    private static String site = "&token=64e73c02e4874784b0bd59fc7c0e0dd7";

    public static String cekBahasa (String input) {
        String[] command = input.split(" ");
        if (command.length==2){
            if (isValidUrl(command[1])){
                String json = web+"url="+command[1]+site;
                JSONArray res = getJson(json);
                try {
                    JSONObject result = res.getJSONObject(0);
                    String lang = result.getString("lang");
                    int confidence = result.getInt("confidence")*100;
                    return "Bahasanya adalah: "+ lang + "dengan confidence: "+confidence+"%";
                }
                catch (JSONException e) {
                    return "Tau ah gelap";
                }


            }
            else{
                String json = web+"text="+command[1]+site;
                JSONArray res = getJson(json);
                try{
                    JSONObject result = res.getJSONObject(0);
                    String lang = result.getString("lang");
                    int confidence = result.getInt("confidence")*100;
                    return "Bahasanya adalah: "+ lang + "dengan confidence: "+confidence+"%";
                }
                catch (JSONException e){
                    return  "error";
                }
            }

        }
        else if (command.length>2){
            input = input.substring (12).replace(" ","%20");
            String json = web+"text="+input+site;
            JSONArray res = getJson(json);
            try{
                JSONObject result = res.getJSONObject(0);
                String lang = result.getString("lang");
                int confidence = result.getInt("confidence")*100;
                return ("Bahasanya adalah: "+ lang + "dengan confidence: "+confidence+"%");
            }
            catch (JSONException e) {
                return "error 2";
            }
        }
        else {
            return "HAHA";
        }

    }
    public static void main (String[] args){
        cekBahasa("/detect_lang facebook.com");

    }

    private static boolean isValidUrl (String input){
        URL u = null;
        try {
            u = new URL(input);
        }
        catch (MalformedURLException e){
            return false;
        }
        try {
            u.toURI();
        }
        catch (URISyntaxException e){
            return false;
        }
        return true;
    }

    private static JSONArray getJson (String input){
        JSONArray map = null;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity(input, String.class);
        try{
            URL url = new URL(input);
            HttpURLConnection con =  (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode name = root.path("detectedLangs");
//            String masukan = in.readLine(con.getInputStream());
//            ObjectMapper om = new ObjectMapper();
//            JSONObject haha = om.readValues(masukan, new TypeReference<Map<String,String>>(){});
            try{
                return haha.getJSONArray("detectedLangs");
            }
            catch (JSONException e){
                return map;
            }

        }
        catch (IOException e){
            return map;
        }
    }




    }


