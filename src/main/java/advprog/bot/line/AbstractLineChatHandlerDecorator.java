package advprog.bot.line;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;

import java.util.Collections;
import java.util.List;

public abstract class AbstractLineChatHandlerDecorator implements LineChatHandler {

    protected LineChatHandler decoratedLineChatHandler;

    // here you can see our old friend Template Method pattern!

    @Override
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event,
                                       List<TextMessage> replyMessages) {
        if (canHandleTextMessage(event)) {
            replyMessages.addAll(handleTextMessage(event));
        }
        decoratedLineChatHandler.handleTextMessageEvent(event, replyMessages);
    }

    protected List<TextMessage> handleTextMessage(MessageEvent<TextMessageContent> event) {
        return Collections.emptyList();
    }

    @Override
    public void handleImageMessageEvent(MessageEvent<ImageMessageContent> event,
                                        List<ImageMessage> replyMessages) {
        if (canHandleImageMessage(event)) {
            replyMessages.addAll(handleImageMessage(event));
        }
        decoratedLineChatHandler.handleImageMessageEvent(event, replyMessages);
    }

    protected List<ImageMessage> handleImageMessage(MessageEvent<ImageMessageContent> event) {
        return Collections.emptyList();
    }

    @Override
    public void handleAudioMessageEvent(MessageEvent<AudioMessageContent> event,
                                        List<AudioMessage> replyMessages) {
        if (canHandleAudioMessage(event)) {
            replyMessages.addAll(handleAudioMessage(event));
        }
        decoratedLineChatHandler.handleAudioMessageEvent(event, replyMessages);
    }

    protected List<AudioMessage> handleAudioMessage(MessageEvent<AudioMessageContent> event) {
        return Collections.emptyList();
    }

    @Override
    public void handleStickerMessageEvent(MessageEvent<StickerMessageContent> event,
                                          List<StickerMessage> replyMessages) {
        if (canHandleStickerMessage(event)) {
            replyMessages.addAll(handleStickerMessage(event));
        }
        decoratedLineChatHandler.handleStickerMessageEvent(event, replyMessages);
    }

    protected List<StickerMessage> handleStickerMessage(MessageEvent<StickerMessageContent> e) {
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

}
