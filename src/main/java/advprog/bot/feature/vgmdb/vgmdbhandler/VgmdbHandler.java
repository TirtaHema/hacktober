package advprog.bot.feature.vgmdb.vgmdbhandler;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static advprog.bot.feature.vgmdb.WebScrapper.getData;

public class VgmdbHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger.getLogger(advprog.bot.feature.echo.EchoChatHandler.class.getName());

    public VgmdbHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Vgmdb handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().split(" ")[0].equalsIgnoreCase("/vgmdb")
                && event.getMessage().getText().split(" ")[1].equalsIgnoreCase("OST")
                && event.getMessage().getText().split(" ")[2].equalsIgnoreCase("this")
                && event.getMessage().getText().split(" ")[3].equalsIgnoreCase("month");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        List<String> data = getData();
        String result = "";
        List<Message> listMessage = new ArrayList<>();
        for(int i = 0; i < data.size(); i++){
            if(i !=0 && i%20==0){
                Message text = new TextMessage(result);
                listMessage.add(text);
                result = "";
                result += data.get(i) + "\n\n";
            }else{
                result += data.get(i) + "\n\n";
            }
        }
        result = result.substring(0,result.length()-2);
        Message text = new TextMessage(result);
        listMessage.add(text);

        return listMessage;// just return list of TextMessage for multi-line reply!
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
}
