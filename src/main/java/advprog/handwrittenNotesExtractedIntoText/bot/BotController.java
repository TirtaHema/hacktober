package advprog.handwrittenNotesExtractedIntoText.bot;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

@LineMessageHandler
public class BotController {

    private LineMessagingClient lineMessagingClient;

    private static final Logger LOGGER = Logger.getLogger(BotController.class.getName());

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String replyText = contentText.replace("/echo", "");
        return new TextMessage(replyText.substring(1));
    }

    @EventMapping
    public TextMessage handleImageMessageEvent(MessageEvent<ImageMessageContent> event) throws IOException {
        final LineMessagingClient client = LineMessagingClient
                .builder(System.getProperty("line.bot.channelToken"))
                .build();

        final MessageContentResponse messageContentResponse;
        try {
            messageContentResponse = client.getMessageContent(event.getMessage().getId()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new TextMessage("Error!");
        }

        InputStream contentStream = messageContentResponse.getStream();
        byte[] contentBytes = IOUtils.toByteArray(contentStream);

        String result = APIUtil.imageToText(contentBytes);

        return new TextMessage(result);
    }
}