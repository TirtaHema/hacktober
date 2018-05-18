package advprog.bot.feature.uberestimate.uber;

import advprog.bot.feature.nearbyphotos.flickr.Photo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UberService {
    public String createUrlRequest(Location start, Location end) {
        final String baseUrl = "https://api.uber.com/v1.2/estimates/price";
        final String extensionParam = "?start_latitude=" + Double.toString(start.getLat())
                + "&start_longitude=" + Double.toString(start.getLon())
                + "&end_latitude=" + Double.toString(end.getLat())
                + "&end_longitude=" + Double.toString(end.getLon());
        return baseUrl + extensionParam;
    }

    public String getJsonPriceDetails(Location start, Location end) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        final String accessToken = "KA.eyJ2ZXJzaW9uIjoy"
                + "LCJpZCI6InoyV29DT25FUkVpUEJCbm40aFllSn"
                + "c9PSIsImV4cGlyZXNfYXQiOjE1MjkyMDE0NTMsI"
                + "nBpcGVsaW5lX2tleV9pZCI6Ik1RPT0iLCJwaXBl"
                + "bGluZV9pZCI6MX0.CShTQ0jlXdK720kVLh25yAx"
                + "rYEh1ay1u0ms_mj46e0k";
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Accept-Language", "en_US");
        headers.add("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createUrlRequest(start, end), HttpMethod.GET, entity, String.class
        );
        return response.getBody();
    }

    public List<PriceDetails> getPriceDetails(String placeName, String resultJson) {
        List<PriceDetails> priceDetails = new ArrayList<PriceDetails>();
        try {
            JSONObject json = new JSONObject(resultJson);
            JSONArray prices = json.getJSONArray("prices");
            for (int i = 0; i < prices.length(); i++) {
                String price = prices.getJSONObject(i).getString("estimate").split("-")[0];
                priceDetails.add(new PriceDetails(placeName,
                        prices.getJSONObject(i).getDouble("distance"),
                        Integer.parseInt(prices.getJSONObject(i).getString("duration")),
                        price)
                );
            }
        } catch (Exception e) {
            return priceDetails;
        }
        return priceDetails;
    }
}
