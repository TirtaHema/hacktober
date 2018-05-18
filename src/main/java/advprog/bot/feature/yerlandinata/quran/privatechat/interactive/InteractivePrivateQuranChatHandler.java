package advprog.bot.feature.yerlandinata.quran.privatechat.interactive;

import advprog.bot.line.AbstractLineChatHandlerDecorator;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;

import java.util.Collections;
import java.util.List;

public class InteractivePrivateQuranChatHandler extends AbstractLineChatHandlerDecorator {
    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return false;
    }

    /*
    * /qs - show carousel of surah 1-10
    * /qsi i:j - show carousel of surah i-j
    * {ayat number} - show ayat of previously selected surah
    */
    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        return Collections.emptyList();
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
