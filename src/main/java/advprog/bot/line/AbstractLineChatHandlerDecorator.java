package advprog.bot.line;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;

import java.util.Collections;
import java.util.List;

public abstract class AbstractLineChatHandlerDecorator implements LineChatHandler {

    protected LineChatHandler decoratedLineChatHandler;

    public LineChatHandler getDecoratedLineChatHandler() {
        return decoratedLineChatHandler;
    }

    // here you can see our old friend Template Method pattern!

    @Override
    public List<Message> handleTextMessageEvent(MessageEvent<TextMessageContent> event,
                                                List<Message> replyMessages) {
        if (canHandleTextMessage(event)) {
            replyMessages.addAll(handleTextMessage(event));
        }
        return decoratedLineChatHandler.handleTextMessageEvent(event, replyMessages);
    }

    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        return Collections.emptyList();
    }

    @Override
    public List<Message> handleImageMessageEvent(MessageEvent<ImageMessageContent> event,
                                                      List<Message> replyMessages) {
        if (canHandleImageMessage(event)) {
            replyMessages.addAll(handleImageMessage(event));
        }
        return decoratedLineChatHandler.handleImageMessageEvent(event, replyMessages);
    }

    protected List<Message> handleImageMessage(MessageEvent<ImageMessageContent> event) {
        return Collections.emptyList();
    }

    @Override
    public List<Message> handleAudioMessageEvent(MessageEvent<AudioMessageContent> event,
                                                      List<Message> replyMessages) {
        if (canHandleAudioMessage(event)) {
            replyMessages.addAll(handleAudioMessage(event));
        }
        return decoratedLineChatHandler.handleAudioMessageEvent(event, replyMessages);
    }

    protected List<Message> handleAudioMessage(MessageEvent<AudioMessageContent> event) {
        return Collections.emptyList();
    }

    @Override
    public List<Message> handleStickerMessageEvent(MessageEvent<StickerMessageContent> event,
                                                   List<Message> replyMessages) {
        if (canHandleStickerMessage(event)) {
            replyMessages.addAll(handleStickerMessage(event));
        }
        return decoratedLineChatHandler.handleStickerMessageEvent(event, replyMessages);
    }

    protected List<Message> handleStickerMessage(MessageEvent<StickerMessageContent> e) {
        return Collections.emptyList();
    }

    @Override
    public List<Message> handleLocationMessageEvent(MessageEvent<LocationMessageContent> event,
                                                   List<Message> replyMessages) {
        if (canHandleLocationMessage(event)) {
            replyMessages.addAll(handleLocationMessage(event));
        }
        return decoratedLineChatHandler.handleLocationMessageEvent(event, replyMessages);
    }

    protected List<Message> handleLocationMessage(MessageEvent<LocationMessageContent> e) {
        return Collections.emptyList();
    }

    //USE event.getMessage().getText() !!!
    protected abstract boolean canHandleTextMessage(MessageEvent<TextMessageContent> event);

    //IF YOUR CODE CANNOT HANDLE IMAGE, JUST RETURN FALSE!
    protected abstract boolean canHandleImageMessage(MessageEvent<ImageMessageContent> event);

    //IF YOUR CODE CANNOT HANDLE AUDIO, JUST RETURN FALSE!
    protected abstract boolean canHandleAudioMessage(MessageEvent<AudioMessageContent> event);

    //IF YOUR CODE CANNOT HANDLE STICKER, JUST RETURN FALSE!
    protected abstract boolean canHandleStickerMessage(MessageEvent<StickerMessageContent> event);

    //IF YOUR CODE CANNOT HANDLE LOCATION, JUST RETURN FALSE!
    protected abstract boolean canHandleLocationMessage(MessageEvent<LocationMessageContent> event);

}
