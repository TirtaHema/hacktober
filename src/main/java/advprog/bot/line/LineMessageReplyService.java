package advprog.bot.line;

import com.linecorp.bot.model.message.Message;

import java.util.List;

import org.springframework.lang.NonNull;

public interface LineMessageReplyService {
    void reply(@NonNull String replyToken, @NonNull List<Message> messages);
}
