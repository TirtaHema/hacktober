package advprog.oriconbluray.util.commands;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public interface RankCommand {
    public TextMessage execute(String date) throws IOException;
}