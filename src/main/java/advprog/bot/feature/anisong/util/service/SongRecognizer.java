package advprog.bot.feature.anisong.util.service;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class SongRecognizer {

    public String songValidate(String song) throws IOException {
        String url = "https://schoolido.lu/songs/?search=";
        song.replace(" ","+");
        url += song;
        try {
            JSONObject
            return output;
        } catch (HttpStatusException e) {
            return null;
        }
    }

//    public MessageEvent<AudioMessageContent> getSong(String song) {
//
//    }
}
