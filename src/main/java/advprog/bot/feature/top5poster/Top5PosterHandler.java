package advprog.bot.feature.top5poster;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Top5PosterHandler extends AbstractLineChatHandlerDecorator {

    private Top5PosterService service;

    public Top5PosterHandler(LineChatHandler decorated, Top5PosterService service) {
        this.decoratedLineChatHandler = decorated;
        this.service = service;
    }


    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return true;
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        List<Message> messages = new ArrayList<>();
        if (event.getMessage().getText().split(" ")[0].equals("/topposters")
                && event.getSource() instanceof GroupSource) {
            try {
                List<Poster> posters = service.getTop5(
                        ((GroupSource) event.getSource()).getGroupId());
                int counter = 1;
                for (Poster p : posters) {
                    messages.add(new TextMessage(counter++ + ". " + p.toString()));
                }

                while (counter <= 5) {
                    messages.add(new TextMessage(counter++ + ". "));
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return messages; // just return list of TextMessage for multi-line reply!
        // Return empty list of TextMessage if not replying. DO NOT RETURN NULL!!!
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
