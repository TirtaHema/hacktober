package advprog.bot.feature.enterkomputer;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;


public class EnterKomputerChatHandler extends AbstractLineChatHandlerDecorator {
    private static final Logger LOGGER = Logger.getLogger(EnterKomputerChatHandler.class.getName());

    public EnterKomputerChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("EnterKomputer chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        String billboard = event.getMessage().getText().split(" ")[0];
        return (billboard.equals("/enterkomputer"));
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        // just return list of TextMessage for multi-line reply!
        // Return empty list of TextMessage if not replying. DO NOT RETURN NULL!!!
        String contentText = event.getMessage().getText();
        if (contentText.contains("/enterkomputer ")) {
            String catName =
                contentText.replace("/enterkomputer ", "").toLowerCase();
            if (catName.equals("")) {
                return Collections.singletonList(
                    new TextMessage(("Please input the category name"))
                );
            }
            String[] catAndName = catName.split(" ");
            if (catAndName.length == 1) {
                return Collections.singletonList(
                    new TextMessage(("Please input the name of the product"))
                );
            } else {
                String category = catAndName[0];
                String name = contentText.replace("/enterkomputer "
                    + category + " ", "");
                try {
                    return findProduct(category, name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Collections.singletonList(
            new TextMessage((""))
        );

    }

    public  List<Message> findProduct(String cat, String name) throws IOException {
        String result = "";
        EnterKomputerParser parser = new EnterKomputerParser(cat);
        ArrayList<String> productNames = parser.getProductNames();
        if (parser.isFalseCategory()) {
            return Collections.singletonList(
                new TextMessage(("Sorry, we don't have the category"))
            );
        }
        for (String nama: productNames) {
            if (nama.contains(name)) {
                String tambah = append(parser, productNames.indexOf(nama));
                if (result.length() + tambah.length() > 2000) {
                    break;
                }
                result += append(parser, productNames.indexOf(nama));
            }
        }
        if (result.equals("")) {
            return Collections.singletonList(
                new TextMessage(("Sorry, the product name is not available"))
            );
        }
        result = result.substring(0, result.length() - 1);
        //System.out.println(result);
        return Collections.singletonList(
            new TextMessage((result))
        );
    }

    public String append(EnterKomputerParser parser, int posisi) {
        ArrayList<JSONObject> arr = parser.getJsonObj();
        JSONObject dicari = arr.get(posisi);
        String result = dicari.get("name") + " - " + dicari.get("category_description")
            + " - " + dicari.get("price") + "\n";
        return result;
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

}
