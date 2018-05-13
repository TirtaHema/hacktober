package advprog.imagga.config;

import java.net.MalformedURLException;
import java.net.URL;

public class APIConfig {

    private String key;
    private String secret;
    private final URL urlObject = new URL("https://api.imagga.com/v1");
    private String endpoint;

    public APIConfig(String key, String secret) throws MalformedURLException {
        this.key = key;
        this.secret = secret;
    }

    public void setEndpoint(String endpoint){
        this.endpoint = endpoint;
    }

    public String getEndpointUrl(){
        return urlObject+endpoint;
    }
    public void getAuth(){
        /*TBD*/
    }

}
