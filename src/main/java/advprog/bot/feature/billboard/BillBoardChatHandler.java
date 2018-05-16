package advprog.bot.feature.billboard;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class BillBoardChatHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger.getLogger(BillBoardChatHandler.class.getName());

    public BillBoardChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Billboard chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        String billboard = event.getMessage().getText().split(" ")[0];
        String command = event.getMessage().getText().split(" ")[1];
        return (billboard.equals("/billboard") && command.equals("japan100"));
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        String contentText = event.getMessage().getText();
        if (contentText.contains("/billboard japan100")) {
            BillBoardOperation operation = new BillBoardOperation();
        }
        if (contentText.contains("/billboard japan100")) {
            BillBoardOperation operation = new BillBoardOperation();
            ArrayList<String> artists = operation.getArrayArtist();
            ArrayList<String> songs = operation.getArraySong();
            String replyMessage = "";
            for (int i = 0; i < 10; i++) {
                replyMessage = replyMessage
                    + "(" +  (i + 1) + ") "
                    + artists.get(i) + " - "
                    + songs.get(i) + "\n";
            }

            return Collections.singletonList(
                new TextMessage(replyMessage)
            );
        }
        return Collections.singletonList(
            new TextMessage("")
        ); // just return list of TextMessage for multi-line reply!
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
