package advprog.bot.feature.Language;

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

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class DetectChatHandler {
    private static final Logger LOGGER = Logger.getLogger(DetectChatHandler.class.getName());

    public DetectChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Detect chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().split(" ")[0].equals("/echo");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        return Collections.singletonList(
                new TextMessage(detectLang(event.getMessage().getText());))
        ); // just return list of TextMessage for multi-line reply!
        // Return empty list of TextMessage if not replying. DO NOT RETURN NULL!!!
    }

    private static final void detectLang(String input){
        String[] command = input.split(" ");
        if (command[0].equals("/detect_lang")){
            Detect.cekBahasa(input);
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
