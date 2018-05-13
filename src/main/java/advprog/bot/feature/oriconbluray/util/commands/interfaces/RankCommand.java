package advprog.bot.feature.oriconbluray.util.commands.interfaces;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public interface RankCommand {
    public TextMessage execute(String date) throws IOException;
}
