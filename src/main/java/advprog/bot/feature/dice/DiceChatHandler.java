package advprog.bot.feature.dice;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import com.linecorp.bot.model.event.MessageEvent;

import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class DiceChatHandler extends AbstractLineChatHandlerDecorator {

    private RandomGenerator randomGenerator;
    private static final Logger LOGGER = Logger.getLogger(DiceChatHandler.class.getName());

    public DiceChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        this.randomGenerator = new RandomGenerator();
        LOGGER.info("Dice chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        String command = event.getMessage().getText().split(" ")[0];
        return command.equals("/coin") || command.equals("/roll") || command.equals("/multiroll")
                || command.equals("/is_lucky");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        LOGGER.info("Trying to handle message: " + event.getMessage().getText());
        TextMessageContent content = event.getMessage();
        String contentText = content.getText().toLowerCase();

        String[] msgSplit = contentText.split(" ");
        if (msgSplit[0].equals("/coin")) {
            String flippedCoin = randomGenerator.spinCoin();
            return Collections.singletonList(new TextMessage(flippedCoin));
        } else if (msgSplit[0].equals("/roll") && msgSplit.length == 2) {
            String[] diceSplit = msgSplit[1].split("d");
            int x = Integer.parseInt(diceSplit[0]);
            int y = Integer.parseInt(diceSplit[1]);
            String rolledDice = handleRoll(x, y);
            return Collections.singletonList(new TextMessage(rolledDice));
        } else if (msgSplit[0].equals("/multiroll")) {
            int num = Integer.parseInt(msgSplit[1]);
            String[] diceSplit = msgSplit[2].split("d");
            int x = Integer.parseInt(diceSplit[0]);
            int y = Integer.parseInt(diceSplit[1]);
            String rolledDice = handleMultiRoll(num, x, y);
            return Collections.singletonList(new TextMessage(rolledDice));
        } else if (msgSplit[0].equals("/is_lucky")) {
            int num = Integer.parseInt(msgSplit[1]);
            String[] diceSplit = msgSplit[2].split("d");
            int x = Integer.parseInt(diceSplit[0]);
            int y = Integer.parseInt(diceSplit[1]);
            String isLucky = handleIsLucky(num, x, y);
            return Collections.singletonList(new TextMessage(isLucky));
        } else {
            String errorMsg = "Please follow the given format\n"
                    + "/coin\n/roll XdY\n/multiroll N XdY\n/is_lucky NUM XdY";
            return Collections.singletonList(new TextMessage(errorMsg));
        }
    }

    public String diceIterator(int[] numbers) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int arrLen = numbers.length;
        for (int i = 0; i < arrLen; i++) {
            sb.append(numbers[i]);
            if (i < arrLen - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public String handleRoll(int x, int y) {
        int[] arr = randomGenerator.rollDice(x, y);

        StringBuilder sb = new StringBuilder();
        sb.append("Result: ");
        sb.append(x + "d" + y);
        sb.append(" ");
        String diceResult = diceIterator(arr);
        sb.append(diceResult);

        return sb.toString();
    }

    public String handleMultiRoll(int n, int x, int y) {
        int[][] arr = randomGenerator.multiRollDice(n, x, y);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            String diceResult = diceIterator(arr[i]);
            sb.append(diceResult);
            if (i < n - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String handleIsLucky(int target, int x, int y) {
        int result = randomGenerator.isLucky(target, x, y);

        if (result < 0) {
            return "Please input using the correct format type "
                    + "\"is_lucky NUM XdY\" with NUM, x, y > 0";
        } else if (result == 0) {
            return "I'm not lucky";
        }

        return "Total count: " + result;
    }

    @Override
    protected boolean canHandleImageMessage(MessageEvent<ImageMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleAudioMessage(MessageEvent<AudioMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleStickerMessage(MessageEvent<StickerMessageContent> event) {
        return false;
    }

    @Override
    protected boolean canHandleLocationMessage(MessageEvent<LocationMessageContent> event) {
        return false;
    }
}
