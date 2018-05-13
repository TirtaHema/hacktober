package advprog.bot.feature.oriconbluray.util.commands.control;

import advprog.bot.feature.oriconbluray.util.commands.impls.DailyRankCommand;
import advprog.bot.feature.oriconbluray.util.commands.impls.WeeklyRankCommand;
import advprog.bot.feature.oriconbluray.util.commands.interfaces.RankCommand;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RankCommandControl {

    private Map<String, RankCommand> commandMap;

    public RankCommandControl() {
        registerCommands();
    }

    public RankCommand addCommand(String key, RankCommand command) {
        return commandMap.put(key, command);
    }

    public RankCommand removeCommand(String key) {
        return commandMap.remove(key);
    }

    public TextMessage execute(String key, String date) throws IOException {
        return commandMap.get(key).execute(date);
    }

    private void registerCommands() {
        commandMap = new HashMap<>();
        commandMap.put("daily", new DailyRankCommand());
        commandMap.put("weekly", new WeeklyRankCommand());
    }
}
