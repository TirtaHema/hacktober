package advprog.bot.feature.yerlandinata.youtubeinfo;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;

public class YoutubeInfoChatHandler extends AbstractLineChatHandlerDecorator {

    private final YoutubeInfoFetcher youtubeInfoFetcher;

    public YoutubeInfoChatHandler(LineChatHandler decoratedHandler, YoutubeInfoFetcher youtubeInfoFetcher) {
        this.decoratedLineChatHandler = decoratedHandler;
        this.youtubeInfoFetcher = youtubeInfoFetcher;
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return false;
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
