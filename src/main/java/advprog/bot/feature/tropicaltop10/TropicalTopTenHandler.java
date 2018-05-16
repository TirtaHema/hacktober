package advprog.bot.feature.tropicaltop10;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class TropicalTopTenHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger
            .getLogger(TropicalTopTenHandler.class.getName());

    public TropicalTopTenHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Billboard Tropical Top Ten handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().split(" ")[0].equals("/billboard")
                && event.getMessage().getText().split(" ")[1].equals("tropical");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        try {
            return Collections.singletonList(new TextMessage(getTopTen()));
        } catch (IOException e) {
            return new LinkedList<>();
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
