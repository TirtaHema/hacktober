package advprog.bot.feature.yerlandinata.quran.privatechat;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;
import advprog.bot.feature.yerlandinata.quran.InvalidAyatQuranException;
import advprog.bot.feature.yerlandinata.quran.fetcher.AyatQuranFetcher;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

public class PrivateQuranChatHandler extends AbstractLineChatHandlerDecorator {

    static final String HELP_MESSAGE = "Untuk langsung mendapatkan ayat Quran,"
            + " gunakan /qs nomor_surah:nomor_ayat, contoh /qs 1:1";
    static final String FETCHER_FAIL = "Mohon maaf, layanan quran online"
            + " tidak tersedia untuk sementara";

    private final AyatQuranFetcher ayatQuranFetcher;
    private static final Pattern NON_INTERACTIVE_PATTERN = Pattern.compile("/qs \\d+:\\d+");
    private static final Pattern COMMAND_PATTERN = Pattern.compile("/qs.*");
    private static final Logger LOGGER = Logger.getLogger(PrivateQuranChatHandler.class.getName());

    public PrivateQuranChatHandler(
            LineChatHandler decoratedHandler,
            AyatQuranFetcher ayatQuranFetcher
    ) {
        this.decoratedLineChatHandler = decoratedHandler;
        this.ayatQuranFetcher = ayatQuranFetcher;
        LOGGER.info("Non-Interactive Quran Private Chat Handler set!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        if (event.getSource() instanceof UserSource) {
            Matcher matcher = COMMAND_PATTERN.matcher(event.getMessage().getText());
            return matcher.matches();
        }
        return false;
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        if (!NON_INTERACTIVE_PATTERN.matcher(event.getMessage().getText()).matches()) {
            LOGGER.info("Incorrect argument detected for non-interactive mode");
            return Collections.singletonList(
                    new TextMessage(HELP_MESSAGE)
            );
        }
        List<Message> replies = new LinkedList<>();
        String[] surahAyat = event.getMessage().getText().replace("/qs", "").trim().split(":");
        int surah = Integer.parseInt(surahAyat[0]);
        int ayat = Integer.parseInt(surahAyat[1]);
        AyatQuran ayatQuran;
        try {
            ayatQuran = ayatQuranFetcher.fetchAyatQuran(surah, ayat);
        } catch (InvalidAyatQuranException e) {
            return Collections.singletonList(new TextMessage(e.getMessage()));
        } catch (IOException | JSONException e) {
            return Collections.singletonList(new TextMessage(FETCHER_FAIL));
        }
        replies.add(new TextMessage(
                String.format("%s : %d", ayatQuran.getSurahName(), ayatQuran.getAyatNum())
        ));
        replies.add(new TextMessage(ayatQuran.getAyatArab()));
        replies.add(new TextMessage(ayatQuran.getAyatIndonesia()));
        replies.add(new AudioMessage(ayatQuran.getAudioUri(), 3));
        LOGGER.info("Success, result: \n" + ayatQuran.toString());
        return replies;
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
