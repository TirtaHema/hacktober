package advprog.tropical_top_10.bot.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



@LineMessageHandler
public class TropicalController {

    private static final Logger LOGGER = Logger.getLogger(TropicalController.class.getName());

    @EventMapping
    public static TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event)
            throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        String reply = getTopTen();
        return new TextMessage(reply);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    public static String getTopTen() throws IOException {
        Document doc = Jsoup.connect("https://www.billboard.com/charts/tropical-songs").get();
        Elements container = doc.select(".chart-row__title");
        String hasil = "";
        for (int i = 0; i < 10; i++) {
            Element elements = container.get(i);
            hasil += "(" + (i + 1) + ") " + elements.select(".chart-row__artist").text()
                    + " - " + elements.select(".chart-row__song").text() + "\n";
        }
        return hasil;
    }
}
