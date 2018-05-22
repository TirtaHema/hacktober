package advprog.bot.feature.yerlandinata.quran.privatechat.interactive;

import advprog.bot.feature.yerlandinata.quran.AyatQuran;
import advprog.bot.feature.yerlandinata.quran.SurahQuran;
import advprog.bot.feature.yerlandinata.quran.fetcher.AyatQuranFetcher;
import advprog.bot.feature.yerlandinata.quran.fetcher.SurahQuranFetcher;
import advprog.bot.feature.yerlandinata.quran.privatechat.interactive.service.InteractiveAyatFetcherService;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.JSONException;

public class InteractivePrivateQuranChatHandler extends AbstractLineChatHandlerDecorator {

    static final String QURAN_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/"
            + "thumb/7/7b/Opened_Qur%27an.jpg/220px-Opened_Qur%27an.jpg";
    static final String SERVICE_DOWN = "Mohon maaf, "
            + "layanan Quran interaktif sedang tidak tersedia";

    private final SurahQuranFetcher surahQuranFetcher;
    private final InteractiveAyatFetcherService interactiveAyatFetcherService;

    private static final Pattern QS_PATTERN = Pattern.compile("/qs");
    private static final Pattern QSI_NEXT_PATTERN = Pattern.compile("/qsi \\d+:\\d+");
    private static final Pattern QSI_CHOOSE_PATTERN = Pattern.compile("/qsi \\d+");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final Logger LOGGER = Logger.getLogger(
            InteractivePrivateQuranChatHandler.class.getName()
    );

    public InteractivePrivateQuranChatHandler(
            LineChatHandler decoratedHandler,
            SurahQuranFetcher surahQuranFetcher,
            InteractiveAyatFetcherService interactiveAyatFetcherService
    ) {
        this.decoratedLineChatHandler = decoratedHandler;
        this.surahQuranFetcher = surahQuranFetcher;
        this.interactiveAyatFetcherService = interactiveAyatFetcherService;
        LOGGER.info("Interactive Quran Private Chat Handler set!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return QS_PATTERN.matcher(event.getMessage().getText()).matches()
                || QSI_NEXT_PATTERN.matcher(event.getMessage().getText()).matches()
                || QSI_CHOOSE_PATTERN.matcher(event.getMessage().getText()).matches()
                || NUMBER_PATTERN.matcher(event.getMessage().getText()).matches();
    }

    /*
    * /qs - show carousel of surah 1-10
    * /qsi i:j - show carousel of surah i-j
    * /qsi x - select chapter x
    * {ayat number} - show ayat of previously selected surah
    */
    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        if (!(event.getSource() instanceof UserSource)) {
            return Collections.emptyList();
        }
        UserSource userSource = (UserSource) event.getSource();
        if (QS_PATTERN.matcher(event.getMessage().getText()).matches()) {
            try {
                return showSurah(1, 6);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else if (QSI_CHOOSE_PATTERN.matcher(event.getMessage().getText()).matches()) {
            try {
                return recordSurahChoice(
                        Integer.parseInt(event.getMessage().getText().replace("/qsi", "").trim()),
                        userSource.getUserId()
                );
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else if (NUMBER_PATTERN.matcher(event.getMessage().getText().trim()).matches()) {
            return getAyat(
                    Integer.parseInt(event.getMessage().getText().trim()),
                    userSource.getUserId()
            );
        }
        return Collections.emptyList();
    }

    private List<Message> showSurah(int begin, int end) throws IOException, JSONException {
        List<CarouselColumn> columns = surahQuranFetcher.fetchSurahQuran()
                .subList(begin - 1, end)
                .stream()
                .map(s -> new CarouselColumn(
                        QURAN_IMAGE,
                        s.getEnglishName(),
                        s.getArabName(),
                        Collections.singletonList(
                                new MessageAction(
                                        "Pilih",
                                        "/qsi " + s.getSurahNumber()
                                )
                        )
                ))
                .collect(Collectors.toList());
        columns.add(new CarouselColumn(
                InteractivePrivateQuranChatHandler.QURAN_IMAGE,
                "Next",
                "Tampilkan surah lainnya",
                Arrays.asList(
                        new MessageAction(
                                String.format("Surah %d - %d", end + 1, end + 1 + 5),
                                String.format("/qsi %d:%d", end + 1, end + 1 + 5)
                        ),
                        new MessageAction(
                                String.format("Surah %d - %d", end + 7, end + 1 + 11),
                                String.format("/qsi %d:%d", end + 7, end + 1 + 11)
                        ),
                        new MessageAction(
                                String.format("Surah %d - %d", end + 13, end + 1 + 17),
                                String.format("/qsi %d:%d", end + 13, end + 1 + 17)
                        )
                )
        ));
        return Collections.singletonList(
                new TemplateMessage(
                        "Quran",
                        new CarouselTemplate(
                            columns
                        ))
        );
    }

    private List<Message> recordSurahChoice(int surah, String userId)
            throws IOException, JSONException {
        try {
            interactiveAyatFetcherService.recordUserSurahSelection(userId, surah);
        } catch (IllegalStateException e) {
            return Collections.emptyList();
        }
        List<SurahQuran> surahQurans = surahQuranFetcher.fetchSurahQuran();
        return Collections.singletonList(
                new TextMessage(String.format(
                        "Surah %s terdiri dari %d ayat, ayat keberapa yang Antum ingin lihat?",
                        surahQurans.get(surah - 1).getEnglishName(),
                        surahQurans.get(surah - 1).getNumberOfAyat()
                ))
        );
    }

    private List<Message> getAyat(int ayat, String userId) {
        AyatQuran ayatQuran = null;
        try {
            ayatQuran = interactiveAyatFetcherService.fetchAyat(userId, ayat);
        } catch (IOException | JSONException e) {
            return Collections.singletonList(new TextMessage(SERVICE_DOWN));
        } catch (IllegalStateException e) {
            return Collections.emptyList();
        }
        return Arrays.asList(
                new TextMessage(
                        String.format("%s : %d", ayatQuran.getSurahName(), ayat)
                ),
                new TextMessage(ayatQuran.getAyatArab()),
                new TextMessage(ayatQuran.getAyatIndonesia()),
                new AudioMessage(ayatQuran.getAudioUri(), ayatQuran.getAudioDuration())
        );
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
