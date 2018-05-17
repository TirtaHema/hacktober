package advprog.bot.feature.acronym;

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

public class AcronymChatHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger.getLogger(AcronymChatHandler.class.getName());

    public AcronymChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Acronym chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        if (event.getSource().getSenderId().equals(event.getSource().getUserId())) {
            //this should be private chat
            String keyword = event.getMessage().getText().split(" ")[0];

            return keyword.equals("/add_acronym")
                    || keyword.equals("/update_acronym")
                    || keyword.equals("/delete_acronym");
        }
        //this should be public chat
        String message = event.getMessage().getText();
        return message.contains("start acronym")
                || message.equals("next acronym")
                || message.equals("stop acronym");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        if (event.getSource().getSenderId().equals(event.getSource().getUserId())) {
            //this should be private chat
            String keyword = event.getMessage().getText().split(" ")[0];
            return null;
        }
        //this should be public chat
        String message = event.getMessage().getText();
        return null;
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
