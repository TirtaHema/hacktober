package advprog.bot.feature.acronym;

import advprog.bot.feature.acronym.helper.AcronymService;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class AcronymChatHandler extends AbstractLineChatHandlerDecorator {

    private static final Logger LOGGER = Logger.getLogger(AcronymChatHandler.class.getName());
    private static final AcronymService ACRONYM_SERVICE = new AcronymService();

    public AcronymChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Acronym chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        if (event.getSource() instanceof UserSource) {
            return ACRONYM_SERVICE.isRecievingUserInput(
                    event.getSource().getUserId(), event.getMessage().getText());
        }

        return ACRONYM_SERVICE.isRecievingGroupInput(
                event.getSource().getSenderId(), event.getMessage().getText());
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        try {
            if (event.getSource().getSenderId().equals(event.getSource().getUserId())) {
                List<Message> response = new ArrayList<>();
                String firstResponse = ACRONYM_SERVICE.processUserInput(
                        event.getSource().getUserId(),
                        event.getMessage().getText());
                response.add(new TextMessage(firstResponse));
                if (firstResponse.split(" ")[0].toLowerCase().equals("which")) {
                    response.add(new TemplateMessage(ACRONYM_SERVICE.getAcronymsList().toString(),
                            createCarouselResponse(ACRONYM_SERVICE.getAcronymsList())));
                }
                return response;
            }
            return Collections.singletonList(new TextMessage(ACRONYM_SERVICE.processGroupInput(
                    event.getSource().getSenderId(),
                    event.getSource().getUserId(),
                    event.getMessage().getText())));
        } catch (Exception e) {
            return Collections.singletonList(
                    new TextMessage("Something went wrong. Have you added me as a friend?"));
        }
    }

    private CarouselTemplate createCarouselResponse(List<String> texts) {
        List<CarouselColumn> columns = new ArrayList<>();
        CarouselColumn.CarouselColumnBuilder columnBuilder = CarouselColumn.builder();

        for (String text : texts) {
            List<Action> actions = new ArrayList<>();
            actions.add(new MessageAction("choose", text.split(" -- ")[0]));
            columnBuilder.text(text);
            columnBuilder.actions(actions);
            columns.add(columnBuilder.build());
        }
        return new CarouselTemplate(columns);
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
