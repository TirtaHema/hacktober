package advprog.oriconbluray.util.commands;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RankCommandControl {

    private Map<String, RankCommand> commandMap;

    public RankCommandControl() {
        registerCommands();
    }

    public boolean addCommand(String key, RankCommand command) {
        return commandMap.put(key, command) != null;
    }

    public boolean removeCommand(String key) {
        return commandMap.remove(key) != null;
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
