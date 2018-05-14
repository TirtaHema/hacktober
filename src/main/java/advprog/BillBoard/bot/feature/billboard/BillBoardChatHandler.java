package advprog.BillBoard.bot.feature.billboard;

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

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

public class BillBoardChatHandler extends AbstractLineChatHandlerDecorator{
    private static final Logger LOGGER = Logger.getLogger(BillBoardChatHandler.class.getName());

    public BillBoardChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Billboard chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        String billboard = event.getMessage().getText().split(" ")[0];
        String command = event.getMessage().getText().split(" ")[1];
        return (billboard.equals("/billboard") && command.equals("bill200"));
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        return Collections.singletonList(
            new TextMessage(event.getMessage().getText().replace("/echo", ""))
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
