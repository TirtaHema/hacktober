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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

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
        String[] inputText = event.getMessage().getText().split(" ");
        if (inputText[0].equals("/enterkomputer") && inputText.length > 1) {
            if (inputText[1].equals("ssd") && inputText.length > 2) {
                if (inputText[2].equals("right")) {
                    return Collections.singletonList(
                        new TextMessage(("this is if the format is right"))
                    );
                } else {
                    return Collections.singletonList(
                        new TextMessage(("Sorry, the product name is not available"))
                    );
                }
            } else if (inputText[1].equals("CATEGORY")) {
                return Collections.singletonList(
                    new TextMessage(("Sorry, we don't have the category"))
                );
            } else {
                return Collections.singletonList(
                    new TextMessage(("Please input the name of the product"))
                );
            }
        } else {
            return Collections.singletonList(
                new TextMessage(("Please input the category name"))
            );
        }
        // just return list of TextMessage for multi-line reply!
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
