package advprog.bot;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import advprog.bot.line.LineMessageReplyService;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.LinkedList;
import java.util.logging.Logger;

import javax.xml.stream.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@LineMessageHandler
public class BotController {

    private static final Logger LOGGER = Logger.getLogger(BotController.class.getName());
    private final LineMessageReplyService lineMessageReplyService;
    private LineChatHandler lineChatHandler;

    @Autowired
    public BotController(LineMessageReplyService messageReplyService,
                         @Qualifier("base") LineChatHandler chatHandler) {
        lineMessageReplyService = messageReplyService;
        lineChatHandler = chatHandler;
    }

    // IT WILL ENFORCE YOU TO DECORATE THE EXISTING HANDLER FIRST !!!
    public void replaceLineChatHandler(AbstractLineChatHandlerDecorator decorator) {
        if (decorator.getDecoratedLineChatHandler() != lineChatHandler) {
            LOGGER.severe("Illegal chat handler replacement detected!!!");
            throw new IllegalStateException(
                    "You should decorate the existing chat handler first! "
                    + "Please read README.md in https://gitlab.com/csui-advprog-2018/B/B3"
            );
        }
        LOGGER.info("Added a chat handler");
        lineChatHandler = decorator;
    }

    public LineChatHandler getLineChatHandler() {
        return lineChatHandler;
    }

    // redundancy below exists for a much more intuitive usage!
    //      (to reduce questions like: dude, how to reply to image message?)

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        lineMessageReplyService.reply(event.getReplyToken(),
                lineChatHandler.handleTextMessageEvent(event, new LinkedList<>()));
    }

    @EventMapping
    public void handleImageMessageEvent(MessageEvent<ImageMessageContent> event) {
        lineMessageReplyService.reply(event.getReplyToken(),
                lineChatHandler.handleImageMessageEvent(event, new LinkedList<>()));
    }

    @EventMapping
    public void handleAudioMessageEvent(MessageEvent<AudioMessageContent> event) {
        lineMessageReplyService.reply(event.getReplyToken(),
                lineChatHandler.handleAudioMessageEvent(event, new LinkedList<>()));
    }

    @EventMapping
    public void handleStickerMessageEvent(MessageEvent<StickerMessageContent> event) {
        lineMessageReplyService.reply(event.getReplyToken(),
                lineChatHandler.handleStickerMessageEvent(event, new LinkedList<>()));
    }

    @EventMapping
    public void handleLocationMessageEvent(MessageEvent<LocationMessageContent> event) {
        lineMessageReplyService.reply(event.getReplyToken(),
                lineChatHandler.handleLocationMessageEvent(event, new LinkedList<>()));
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

}
