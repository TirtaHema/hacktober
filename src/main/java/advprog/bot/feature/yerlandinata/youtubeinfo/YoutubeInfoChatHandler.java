package advprog.bot.feature.yerlandinata.youtubeinfo;

import advprog.bot.feature.yerlandinata.youtubeinfo.fetcher.YoutubeInfoFetcher;
import advprog.bot.feature.yerlandinata.youtubeinfo.fetcher.YoutubeVideoNotFoundException;
import advprog.bot.feature.yerlandinata.youtubeinfo.parser.AbstractYoutubeVideoIdParser;
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

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONException;

public class YoutubeInfoChatHandler extends AbstractLineChatHandlerDecorator {

    private final YoutubeInfoFetcher youtubeInfoFetcher;
    private final AbstractYoutubeVideoIdParser youtubeVideoIdParser;
    private static final String COMMAND_WORD = "/youtube";
    private static final Logger LOGGER = Logger.getLogger(YoutubeInfoChatHandler.class.getName());
    static final String MESSAGE_INVALID_URL = "URL Video Youtube tidak sah :(";

    public YoutubeInfoChatHandler(
            LineChatHandler decoratedHandler,
            YoutubeInfoFetcher youtubeInfoFetcher,
            AbstractYoutubeVideoIdParser youtubeVideoIdParser) {
        this.decoratedLineChatHandler = decoratedHandler;
        this.youtubeInfoFetcher = youtubeInfoFetcher;
        this.youtubeVideoIdParser = youtubeVideoIdParser;
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().split(" ")[0].equals(COMMAND_WORD);
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        LOGGER.info("Trying to handle message: " + event.getMessage().getText());
        String videoUrl = event.getMessage().getText()
                                            .split(" ")[1]
                                            .trim();
        String videoId;
        try {
            videoId = youtubeVideoIdParser.parseYoutubeVideoId(videoUrl);
        } catch (Exception e) {
            return Collections.singletonList(
                    new TextMessage(MESSAGE_INVALID_URL)
            );
        }
        YoutubeVideo youtubeVideo;
        try {
            youtubeVideo = youtubeInfoFetcher.fetchData(videoId);
        } catch (IOException | JSONException | YoutubeVideoNotFoundException e){
            String reply = "Video dengan ID " + videoId + " tidak dapat kami akses :(";
            LOGGER.info("Fail, reply:\n" + reply);
            return Collections.singletonList(new TextMessage(reply));
        }
        String reply = "Informasi video"
                + "\nJudul: " + youtubeVideo.getTitle()
                + "\nChannel: " + youtubeVideo.getChannelName()
                + "\nJumlah ditonton: " +  youtubeVideo.getViewCount() + " kali"
                + "\nLikes: " + youtubeVideo.getLikeCount()
                + "\nDislikes: " + youtubeVideo.getDislikeCount()
                + "\nURL: " + videoUrl;
        LOGGER.info("Success, reply:\n" + reply);
        return Collections.singletonList(new TextMessage(reply));
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
