package advprog.bot.feature.acronym;

import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    public static MessageEvent<TextMessageContent> createDummyPrivateTextMessage(String text) {
        return new MessageEvent<>("replyToken", new UserSource("userId"),
                new TextMessageContent("id", text),
                Instant.parse("2018-01-01T00:00:00.000Z"));
    }

    public static MessageEvent<TextMessageContent> createDummyGroupTextMessage(String text) {
        return new MessageEvent<>("replyToken", new GroupSource("groupId", "userId"),
                new TextMessageContent("id", text),
                Instant.parse("2018-01-01T00:00:00.000Z"));
    }

    public static CarouselTemplate createCarouselResponse(List<String> texts) {
        List<CarouselColumn> columns = new ArrayList<>();
        CarouselColumn.CarouselColumnBuilder columnBuilder = CarouselColumn.builder();

        for (String text : texts) {
            List<Action> actions = new ArrayList<>();
            actions.add(new MessageAction("choose", text));
            columnBuilder.text(text);
            columnBuilder.actions(actions);
            columns.add(columnBuilder.build());
        }
        return new CarouselTemplate(columns);
    }
}
