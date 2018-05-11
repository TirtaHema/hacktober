package advprog.oriconSingle.util.commands;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChartCommandControl {

    private Map<String, ChartCommand> listCommand;

    public ChartCommandControl() {
        registerCommands();
    }

    public ChartCommand addCommand(String key, ChartCommand command) {
        return listCommand.put(key, command);
    }

    public ChartCommand removeCommand(String key) {
        return listCommand.remove(key);
    }

    public TextMessage execute(String key, String date) throws IOException {
        return listCommand.get(key).execute(date);
    }

    private void registerCommands() {
        listCommand = new HashMap<>();
        listCommand.put("daily", new DailyChartCommand());
        listCommand.put("weekly", new WeeklyChartCommand());
        listCommand.put("monthly", new MonthlyChartCommand());
        listCommand.put("yearly", new YearlyChartCommand());
    }
}
