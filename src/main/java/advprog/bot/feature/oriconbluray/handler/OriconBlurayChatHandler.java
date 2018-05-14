package advprog.bot.feature.oriconbluray.handler;

import advprog.bot.feature.echo.EchoChatHandler;
import advprog.bot.feature.oriconbluray.util.commands.control.RankCommandControl;
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

import org.springframework.stereotype.Service;

@Service
public class OriconBlurayChatHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger
            .getLogger(EchoChatHandler.class.getName());
    private RankCommandControl control;
    private final String invalidFormatMsg = "....... Eh?\n"
            + "I don't understand what're you saying....\n"
            + "Please enter these format, kay?\n\n"
            + "/oricon bluray [daily/weekly] [yyyy-mm-dd]";

    public OriconBlurayChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Oricon Bluray chat handler added!");
        control = new RankCommandControl();
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        String[] msg = event.getMessage().getText().split(" ");
        return msg[0].equals("/oricon") && msg[1].equals("bluray");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        try {
            String[] msg = event.getMessage().getText().split(" ");
            if (msg.length != 4) {
                throw new IllegalArgumentException();
            }
            return Collections.singletonList(
                    control.execute(msg[2], msg[3])
            );
        } catch (IOException e) {
            return new LinkedList<>();
        } catch (IllegalArgumentException e) {
            return Collections.singletonList(
                    new TextMessage(invalidFormatMsg)
            );
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
