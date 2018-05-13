package advprog.imagga.config;

import java.net.URL;

public class APIConfig {

    private String key;
    private String secret;
    private final URL urlObject = new URL("https://api.imagga.com/v1");
    private String endpoint;

    public APIConfig(String key, String secret){
        this.key = key;
        this.secret = secret;
    }

    public String setEndpoint(String endpoint){
        this.endpoint = endpoint;
    }

    public String getEndpointUrl(){
        return urlObject+endpoint;
    }
    public String getAuth(){
        /*TBD*/
    }

}
