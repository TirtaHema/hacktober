package advprog.bot.feature.fakenews;

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

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class FakeNewsChatHandler extends AbstractLineChatHandlerDecorator {
    private static final Logger LOGGER = Logger.getLogger(FakeNewsChatHandler.class.getName());

    public FakeNewsChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Fake news detector chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return true;
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        String content = event.getMessage().getText();

        try {
            News news;
            if (content.startsWith("/add_filter ")) {
                String url = content.split(" ")[1];
                String type = content.split(" ")[2];
                FakeNewsHelper.addFilter(url, type);

                return Collections.singletonList(
                        new TextMessage("Filter successfully added")
                );

            } else if (content.startsWith("/is_fake ")) {
                String url = content.split(" ")[1];
                news = FakeNewsHelper.checkFake(url);

            } else if (content.startsWith("/is_satire ")) {
                String url = content.split(" ")[1];
                news = FakeNewsHelper.checkSatire(url);

            } else if (content.startsWith("/is_conspiracy ")) {
                String url = content.split(" ")[1];
                news = FakeNewsHelper.checkConspiracy(url);

            } else {
                news = FakeNewsHelper.check(content);

            }
            return Collections.singletonList(
                    new TextMessage(news.getCategory() + "\n" + news.getNote())
            );
        } catch (Exception e) {
            return Collections.singletonList(
                    new TextMessage("Error occured")
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
