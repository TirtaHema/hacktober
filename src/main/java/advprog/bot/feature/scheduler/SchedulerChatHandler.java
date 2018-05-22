package advprog.bot.feature.scheduler;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;

import java.security.acl.Group;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import org.springframework.stereotype.Service;

public class SchedulerChatHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger.getLogger(SchedulerChatHandler.class.getName());

    public SchedulerChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Scheduler chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().split(" ")[0].equals("/create_schedule")
               || event.getMessage().getText().split(" ")[0].equals("jadwal") ;
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        String command = event.getMessage().getText().split(" ")[0];

        Source source = event.getSource();

        if (source instanceof GroupSource) {
            if (command.equals("/create_schedule")) {
                String imageUrl = "https://getuikit.com/v2/docs/images/placeholder_200x100.svg";
                CarouselTemplate carouselTemplate = new CarouselTemplate(
                        Arrays.asList(
                                new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
                                        new PostbackAction("言 hello2",
                                                "hello こんにちは",
                                                "hello こんにちは"),
                                        new PostbackAction("言 hello2",
                                                "hello こんにちは",
                                                "hello こんにちは")
                                ))
                        ));

                TemplateMessage templateMessage =
                        new TemplateMessage(
                                "carousel alt text", carouselTemplate);

                return Collections.singletonList(
                        templateMessage
                );
            }
            else if (command.equals("jadwal")) {

            }
            return Collections.singletonList(
                    new TextMessage("Hehehe")
            );
        } else if (source instanceof UserSource) {
            return Collections.singletonList(
                    new TextMessage("Hehehe")
            );
        } else {
            return Collections.singletonList(
                    new TextMessage("")
            );
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
