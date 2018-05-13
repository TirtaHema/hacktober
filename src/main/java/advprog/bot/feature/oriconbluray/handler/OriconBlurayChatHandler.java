package advprog.bot.feature.oriconbluray.handler;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.Message;

import java.util.List;

public class OriconBlurayChatHandler extends AbstractLineChatHandlerDecorator{

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return false;
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
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
