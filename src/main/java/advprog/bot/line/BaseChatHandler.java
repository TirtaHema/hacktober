package advprog.bot.line;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("base")
public class BaseChatHandler implements LineChatHandler {

    @Autowired
    public BaseChatHandler() {

    }

    @Override
    public List<Message> handleTextMessageEvent(MessageEvent<TextMessageContent> e,
                                                    List<Message> replyMessages) {
        return replyMessages;
    }

    @Override
    public List<Message> handleImageMessageEvent(MessageEvent<ImageMessageContent> e,
                                                      List<Message> replyMessages) {
        return replyMessages;
    }

    @Override
    public List<Message> handleAudioMessageEvent(MessageEvent<AudioMessageContent> e,
                                                      List<Message> replyMessages) {
        return replyMessages;
    }

    @Override
    public List<Message> handleStickerMessageEvent(MessageEvent<StickerMessageContent> e,
                                                          List<Message> replyMessages) {
        return replyMessages;
    }

    @Override
    public List<Message> handleLocationMessageEvent(MessageEvent<LocationMessageContent> event,
                                                    List<Message> replyMessages) {
        return replyMessages;
    }

}
