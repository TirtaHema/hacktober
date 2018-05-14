package advprog.bot.feature.artist.recent.album;

import advprog.bot.feature.artist.recent.album.helper.Album;
import advprog.bot.feature.artist.recent.album.helper.MusicBrainzApiHelper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ArtistRecentAlbumChatHandler extends AbstractLineChatHandlerDecorator {
    private static final Logger LOGGER = Logger
            .getLogger(ArtistRecentAlbumChatHandler.class.getName());
    private static final MusicBrainzApiHelper MUSIC_BRAINZ_API_HELPER = new MusicBrainzApiHelper();

    public ArtistRecentAlbumChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("ArtistRecentAlbum chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().split(" ")[0].equals("/10albums");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        List<Message> result = new ArrayList<>();
        result.add(new TextMessage(queryArtistRecentAlbum(event
                .getMessage().getText().replace("/10albums ", ""))));

        return result;
    }

    private String queryArtistRecentAlbum(String artistName) {
        try {
            String artistId = MUSIC_BRAINZ_API_HELPER.getArtistId(artistName);
            artistName = MUSIC_BRAINZ_API_HELPER.getArtistName(artistId);
            List<Album> albumList = MUSIC_BRAINZ_API_HELPER.getMostRecentAlbumByArtistId(artistId);
            StringBuilder result = new StringBuilder();

            for (Album album : albumList) {
                if (result.length() > 0) {
                    result.append("\n");
                }
                result.append(artistName);
                result.append(" - ");
                result.append(album.getName());
                result.append("(");
                result.append(album.getDate());
                result.append(")");
            }

            if (result.length() == 0) {
                result.append(artistName);
                result.append(" has no album");
            }

            return result.toString();
        } catch (Exception exception) {
            LOGGER.fine(exception.toString());

            return "Artist not found";
        }
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
