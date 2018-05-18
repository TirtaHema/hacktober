package advprog.bot.feature.anisong.handler;

import advprog.bot.feature.anisong.util.service.SongRecognizer;
import advprog.bot.feature.echo.EchoChatHandler;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

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
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        try {
            SongRecognizer song = new SongRecognizer();
            return Collections.singletonList(new AudioMessage(song.songValidate(event.getMessage().getText()),30));
        } catch (Exception e) {
            return Collections.singletonList(new TextMessage("a"));
        }
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
