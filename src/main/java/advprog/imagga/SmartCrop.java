package advprog.imagga;

import advprog.imagga.config.ApiConfig;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class SmartCrop {

    private HttpURLConnection connection;
    private String endpoint = ApiConfig.URL+"/croppings";
    private String image;
    private String resolution = "500x500";
    private int x, y;
    private int width, height;

    public SmartCrop(String image) {
        this.image = image;
    }

    public void startCrop() throws IOException {
        String url = endpoint + "?url=" + image + "&resolution=" + resolution;
        URL urlObject = new URL(url);
        connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestProperty("Authorization", "Basic " + ApiConfig.getAuth());
    }

    public int getResponseCode() throws IOException {
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        return responseCode;
    }

    public String getCoor() throws IOException, JSONException {
        BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String temp = connectionInput.readLine();
        connectionInput.close();
        System.out.println(temp);
        JSONObject jsonObject = new JSONObject(temp);
        JSONObject croppings = jsonObject.getJSONArray("results").getJSONObject(0).getJSONArray("croppings").getJSONObject(0);
        x = croppings.getInt("x1");
        y = croppings.getInt("y1");
        width = croppings.getInt("x2") - x;
        height = croppings.getInt("y2") - y;
        return String.format("Coor : [(%d, %d), (%d, %d)]", x, y, croppings.getInt("x2"), croppings.getInt("y2"));
    }

    private BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private BufferedImage cropImage(BufferedImage src, int x, int y, int height, int width) {
        BufferedImage dest = src.getSubimage(x, y, height, width);
        return resize(dest, 500, 500);
    }
}
