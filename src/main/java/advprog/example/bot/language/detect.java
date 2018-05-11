package advprog.example.bot.language;

import org.apache.commons.validator.UrlValidator;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Detect {

    private String APIKEY = '64e73c02e4874784b0bd59fc7c0e0dd7' ;
    private String BASE_URL = 'https://api.dandelion.eu/datatxt/li/v1';
    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders headers = new HttpHeaders();

    private String inputString(String input) {
        String[] detect = input.split(" ");
        if (detect[0].equals("/detect_lang")){
             if (detect[1].equals("TEXT")){
                 return hasil (detect[2]);
             }
             else if (detect[1].equals("URL")){
                 if (urlValidator.isValid(detect[2])){
                     return hasil(detect[2]);
                 }
                 else{
                     return "formatnya salah :(";
                 }
             }
        }
    }s

    public void proses (String input) {
        
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String result = restTemplate.getForObject(hasil(input), String.class, headers);
        JSONobject tes = new JSONobject (result);

        System.out.println(result);
    }
    
    public static void main(String[] args) {
       proses("facebook.com");
   }

    private boolean URLValidator(String URL){
        UrlValidator urlValidator = new UrlValidator();
		
	    //valid URL
	    if (urlValidator.isValid(URL)) {
	       return true;
	    } else {
	       return false;
	    }
    }

    private string hasil(String input){
        if (URLValidator(input)) {
            String hasil = BASE_URL + "url=" + input + "&token="+APIKEY;
            return hasil;
        }
        else {
            String baru = input.replace(" ","%20");
            String hasil = BASE_URL + "text=" + baru + "&token="+APIKEY;
            
        }
    }
}
