package advprog.bot.feature.BillboardTropicalArtist;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class BillboardTropicalArtistHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger
            .getLogger(BillboardTropicalArtistHandler.class.getName());

    public BillboardTropicalArtistHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Billboard Tropical Artist checker handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().split(" ")[0].equals("/billboard")
                && event.getMessage().getText().split(" ")[1].equals("tropical");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        try {
            return Collections.singletonList(
                    new TextMessage(cekArtist(event.getMessage().getText().split(" ")[2]))
            ); // just return list of TextMessage for multi-line reply!
        } catch (IOException e) {
            return Collections.singletonList(new TextMessage("Not a valid keyword"));
        }
        // Return empty list of TextMessage if not replying. DO NOT RETURN NULL!!!
    }

    @Override
    protected boolean canHandleImageMessage(MessageEvent<ImageMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleAudioMessage(MessageEvent<AudioMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleStickerMessage(MessageEvent<StickerMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleLocationMessage(MessageEvent<LocationMessageContent> event) {
        return false;
    }

    public static String cekArtist(String artist) throws IOException {
        Document doc = Jsoup.connect("https://www.billboard.com/charts/tropical-songs").get();
        Elements container = doc.select(".chart-row__title");
        String hasil = "";
        for (int i = 0; i < 25; i++) {
            Element elements = container.get(i);
            if (elements.select(".chart-row__artist").text().equalsIgnoreCase(artist)) {
                hasil += "Artist : " + elements.select(".chart-row__artist").text() + "\n"
                        + "Song : " + elements.select(".chart-row__song").text() + "\n"
                        + "Position : " + (i + 1) + "\n";
            }
        }
        if(hasil.equals("")) {
            return artist + " is not present in Billboard's Tropical Songs music chart this week";
        }
        return hasil;
    }
}