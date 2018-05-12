package advprog.bot.line;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;

import java.util.List;

//redundancy exists for a much less confusing usage
public interface LineChatHandler {
    List<Message> handleTextMessageEvent(MessageEvent<TextMessageContent> event,
                                         List<TextMessage> replyMessages);

    List<Message> handleImageMessageEvent(MessageEvent<ImageMessageContent> event,
                                 List<ImageMessage> replyMessages);

    List<Message>  handleAudioMessageEvent(MessageEvent<AudioMessageContent> event,
                                 List<AudioMessage> replyMessages);

    List<Message> handleStickerMessageEvent(MessageEvent<StickerMessageContent> event,
                                   List<StickerMessage> replyMessages);

}
