package advprog.oriconSingle.util.commands;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public interface ChartCommand {
    public TextMessage execute(String date) throws IOException;
}
