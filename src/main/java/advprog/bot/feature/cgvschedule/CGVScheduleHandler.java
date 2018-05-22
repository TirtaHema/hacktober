package advprog.bot.feature.cgvschedule;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class CGVScheduleHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger.getLogger(CGVScheduleHandler.class.getName());


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
        List<Message> result = Collections.emptyList();

        if (text.equals("/cgv_gold_class")) {
            try {
                String out = CgvScreenScrapper.screenScrapper("Gold");
                result = Collections.singletonList(new TextMessage(out));
            } catch (Exception e) {
                LOGGER.info("ERROR");
            }
        } else if (text.equals("/cgv_regular_2d")) {
            try {
                String out = CgvScreenScrapper.screenScrapper("2D Audi");
                result = Collections.singletonList(new TextMessage(out));
            } catch (Exception e) {
                LOGGER.info("ERROR");
            }
        } else if (text.equals("/cgv_4dx_3d_cinema")) {
            try {
                String out = CgvScreenScrapper.screenScrapper("4DX3D");
                result = Collections.singletonList(new TextMessage(out));
            } catch (Exception e) {
                LOGGER.info("ERROR");
            }
        } else if (text.equals("/cgv_velvet")) {
            try {
                String out = CgvScreenScrapper.screenScrapper("Velvet");
                result = Collections.singletonList(new TextMessage(out));
            } catch (Exception e) {
                LOGGER.info("ERROR");
            }
        } else if (text.equals("/cgv_sweet_box")) {
            try {
                String out = CgvScreenScrapper.screenScrapper("Sweetbox");
                result = Collections.singletonList(new TextMessage(out));
            } catch (Exception e) {
                LOGGER.info("ERROR");
            }
        } else if (text.equals("/cgv_change_cinema")) {
            try {
                CgvScreenScrapper.setUrl(event.getMessage().getText().split(" ")[1]);
                result = Collections.singletonList(new TextMessage("Success! Url Valid!"));
            } catch (Exception e) {
                result = Collections.singletonList(new TextMessage("Failed! Url Not Valid!"));
            }
        }
        return result;
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
