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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("base")
public class BaseChatHandler implements LineChatHandler {

    @Autowired
    public BaseChatHandler() {

    }

    @Override
    public List<TextMessage> handleTextMessageEvent(MessageEvent<TextMessageContent> e,
                                                    List<TextMessage> replyMessages) {
        return replyMessages;
    }

    @Override
    public List<ImageMessage> handleImageMessageEvent(MessageEvent<ImageMessageContent> e,
                                                      List<ImageMessage> replyMessages) {
        return replyMessages;
    }

    @Override
    public List<AudioMessage> handleAudioMessageEvent(MessageEvent<AudioMessageContent> e,
                                                      List<AudioMessage> replyMessages) {
        return replyMessages;
    }

    @Override
    public List<StickerMessage> handleStickerMessageEvent(MessageEvent<StickerMessageContent> e,
                                                          List<StickerMessage> replyMessages) {
        return replyMessages;
    }

}
