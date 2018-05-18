package advprog.bot.feature.cgvschedule;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.Message;

import java.util.Collections;
import java.util.List;

public class CGVScheduleHandler extends AbstractLineChatHandlerDecorator {

    public CGVScheduleHandler(LineChatHandler decorated) {
        this.decoratedLineChatHandler = decorated;
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return true;
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        String text = event.getMessage().getText().split(" ")[0];
        List<Message> result;

        if (text.equals("/cgv_gold_class")) {
            result =  Collections.emptyList();
        } else if (text.equals("/cgv_regular_2d")) {
            result =  Collections.emptyList();
        } else if (text.equals("/cgv_4dx_3d_cinema")) {
            result = Collections.emptyList();
        } else if (text.equals("/cgv_velvet")) {
            result = Collections.emptyList();
        } else if (text.equals("/cgv_sweet_box")) {
            result = Collections.emptyList();
        } else if (text.equals("/cgv_change_cinema")) {
            result = Collections.emptyList();
        }
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
