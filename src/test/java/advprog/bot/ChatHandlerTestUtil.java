package advprog.bot;

import static org.mockito.Mockito.mock;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;

import java.time.Instant;

public class ChatHandlerTestUtil {

    public static MessageEvent<TextMessageContent> fakeMessageEvent(
            String replyToken,
            String message
    ) {
        TextMessageContent tmc = new TextMessageContent("id", message);
        return new MessageEvent<>(replyToken, mock(Source.class), tmc, Instant.now());

    }

    public static MessageEvent<ImageMessageContent> fakeImageEvent(
            String replyToken
    ) {
        ImageMessageContent imc = new ImageMessageContent("dd");
        return new MessageEvent<>(replyToken, mock(Source.class), imc, Instant.now());

    }

}
