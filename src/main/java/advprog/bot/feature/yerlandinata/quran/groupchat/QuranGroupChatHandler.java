package advprog.bot.feature.yerlandinata.quran.groupchat;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;
import advprog.bot.feature.yerlandinata.quran.groupchat.service.GuessSurahService;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.json.JSONException;

public class QuranGroupChatHandler extends AbstractLineChatHandlerDecorator {

    static final String BENAR = "Masya Allah! Antum benar!";
    static final String SALAH = "Afwan, jawaban Antum belum benar.";
    private static final Pattern NGAJI_PATTERN = Pattern.compile(".*ngaji.*");
    private static final Logger LOGGER = Logger.getLogger(QuranGroupChatHandler.class.getName());
    private final GuessSurahService guessSurahService;


    public QuranGroupChatHandler(LineChatHandler decorated, GuessSurahService guessSurahService) {
        this.decoratedLineChatHandler = decorated;
        this.guessSurahService = guessSurahService;
        LOGGER.info("Quran group chat handler initialized!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return true;
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        if (!(event.getSource() instanceof GroupSource)) {
            return Collections.emptyList();
        }
        GroupSource groupSource = (GroupSource) event.getSource();
        if (guessSurahService.isGuessing(groupSource.getGroupId())) {
            if (guessSurahService.guess(groupSource.getGroupId(), event.getMessage().getText())) {
                return Collections.singletonList(
                        new TextMessage(BENAR)
                );
            } else {
                return Collections.singletonList(
                        new TextMessage(SALAH)
                );
            }
        } else if (NGAJI_PATTERN.matcher(event.getMessage().getText()).matches()) {
            AyatQuran ayatQuran = null;
            try {
                ayatQuran = guessSurahService.startGuessing(groupSource.getGroupId());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            LOGGER.info("tebakan seharusnya: " + ayatQuran.getSurahName());
            return Arrays.asList(
                    new TextMessage(ayatQuran.getAyatArab()),
                    new TextMessage(ayatQuran.getAyatIndonesia())
            );
        }
        return Collections.emptyList();
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
