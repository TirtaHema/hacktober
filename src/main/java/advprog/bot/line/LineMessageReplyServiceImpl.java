package advprog.bot.line;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.response.BotApiResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineMessageReplyServiceImpl implements LineMessageReplyService {

    private final LineMessagingClient client;

    @Autowired
    public LineMessageReplyServiceImpl(LineMessagingClient client) {
        this.client = client;
    }

    @Override
    public void reply(@NotNull String replyToken, @NotNull List<? extends Message> messages) {
        List<Message> messageList = messages.stream()
                .map(e -> (Message) e)
                .collect(Collectors.toList());
        try {
            BotApiResponse apiResponse = client
                    .replyMessage(new ReplyMessage(replyToken, messageList))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
