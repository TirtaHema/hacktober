package advprog.bot.feature.anisong.handler;

import advprog.bot.feature.anisong.util.service.SongGetter;
import advprog.bot.feature.echo.EchoChatHandler;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class AnisongChatHandler extends AbstractLineChatHandlerDecorator {
    private static final Logger LOGGER = Logger
            .getLogger(EchoChatHandler.class.getName());

    public AnisongChatHandler(LineChatHandler decoratorHandler) {
        this.decoratedLineChatHandler = decoratorHandler;
        LOGGER.info("Anisong chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return true;
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event)  {
        try {
            String[] message = event.getMessage().getText().split(" ");
            if (message[0].equalsIgnoreCase("/listen_song")) {
                if (!event.getSource().toString().contains("groupId")) {
                    SongGetter songGetter = new SongGetter();
                    String pesan = "";
                    for (int i = 1; i < message.length; i++) {
                        pesan += message[i];
                        if (i < message.length - 1) {
                            pesan += " ";
                        }
                    }
                    return Collections.singletonList(
                            new AudioMessage(songGetter.getSong(pesan), 29000));
                } else {
                    return Collections.singletonList(
                            new TextMessage("You must private chat me..."));
                }
            }
        } catch (Exception e) {
            return Collections.singletonList(new TextMessage("Your Input is not valid"));
        }
        return Collections.singletonList(new TextMessage("Your Input is not valid"));
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
