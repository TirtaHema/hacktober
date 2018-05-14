package advprog.bot.feature.artist.recent.album;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.line.BaseChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArtistRecentAlbumChatHandlerTest {

    private ArtistRecentAlbumChatHandler artistRecentAlbumChatHandler;

    @Before
    public void setUp() {
        artistRecentAlbumChatHandler = new ArtistRecentAlbumChatHandler(new BaseChatHandler());
    }

    @Test
    public void testHandleTextMessageEvent() {
        List<Message> messages = new LinkedList<>();
        List<TextMessage> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("Ada - Yalan(2007-07-17)"));
        String msg = "/10albums Tidak ada";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );

        assertEquals(expectedMessages, artistRecentAlbumChatHandler.handleTextMessageEvent(me, messages));

        expectedMessages.add(new TextMessage("Nama has no album"));
        msg = "/10albums Nama Artist";
        me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );

        assertEquals(expectedMessages, artistRecentAlbumChatHandler.handleTextMessageEvent(me, messages));

        expectedMessages.add(new TextMessage("Artist not found"));
        msg = "/10albums zczxxzcz";
        me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );

        assertEquals(expectedMessages, artistRecentAlbumChatHandler.handleTextMessageEvent(me, messages));

        expectedMessages.add(new TextMessage("Coldplay - Greatest Songs(2018)\n"
                + "Coldplay - A Head Full of Dreams(2015-12-04)\n"
                + "Coldplay - That Hits(2014)\n"
                + "Coldplay - Coldplay 4 CD Catalogue Set(2012-11-26)\n"
                + "Coldplay - After After Before(2006)\n"
                + "Coldplay - Greatest Hits(2005)\n"
                + "Coldplay - A Rush of Blood to the Head(2002-08-12)\n"
                + "Coldplay - Parachutes(2000-07-10)\n"
                + "Coldplay - Best of You()\n"
                + "Coldplay - The Singles 1999 - 2008()"));
        msg = "/10albums Coldplay";
        me = ChatHandlerTestUtil.fakeMessageEvent(
                "dsf", msg
        );

        assertEquals(expectedMessages, artistRecentAlbumChatHandler.handleTextMessageEvent(me, messages));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(artistRecentAlbumChatHandler.canHandleAudioMessage(null));
        assertFalse(artistRecentAlbumChatHandler.canHandleImageMessage(null));
        assertFalse(artistRecentAlbumChatHandler.canHandleStickerMessage(null));
        assertFalse(artistRecentAlbumChatHandler.canHandleLocationMessage(null));
    }

}