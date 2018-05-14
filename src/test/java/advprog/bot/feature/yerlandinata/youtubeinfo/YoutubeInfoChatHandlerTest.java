package advprog.bot.feature.yerlandinata.youtubeinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import advprog.bot.ChatHandlerTestUtil;
import advprog.bot.feature.yerlandinata.youtubeinfo.fetcher.YoutubeInfoFetcher;
import advprog.bot.feature.yerlandinata.youtubeinfo.fetcher.YoutubeVideoNotFoundException;
import advprog.bot.feature.yerlandinata.youtubeinfo.parser.AbstractYoutubeVideoIdParser;
import advprog.bot.feature.yerlandinata.youtubeinfo.parser.InvalidYoutubeVideoUrl;
import advprog.bot.line.BaseChatHandler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class YoutubeInfoChatHandlerTest {

    @Mock
    YoutubeInfoFetcher youtubeInfoFetcher;

    @Mock
    AbstractYoutubeVideoIdParser youtubeVideoIdParser;

    YoutubeInfoChatHandler youtubeInfoChatHandler;

    @Before
    public void setUp() {
        youtubeInfoChatHandler = new YoutubeInfoChatHandler(
                new BaseChatHandler(), youtubeInfoFetcher, youtubeVideoIdParser
        );
    }

    @Test
    public void testHandleCorrectCommandCorrectUrl() throws IOException, JSONException {
        String videoId = "videoId";
        String validUrl = "https://youtube.com/watch?v=" + videoId;
        String text = "/youtube " + validUrl;
        YoutubeVideo expectedVideo = new YoutubeVideo(
                "How to pass Advanced Programming painlessly",
                "Fahmi",
                99999,
                99999,
                0
        );
        String expectedOutput = "Informasi video"
                + "\nJudul: " + expectedVideo.getTitle()
                + "\nChannel: " + expectedVideo.getChannelName()
                + "\nJumlah ditonton: " +  expectedVideo.getViewCount() + " kali"
                + "\nLikes: " + expectedVideo.getLikeCount()
                + "\nDislikes: " + expectedVideo.getDislikeCount()
                + "\nURL: " + validUrl;
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent("re", text);
        when(youtubeInfoFetcher.fetchData(any(String.class)))
                .thenReturn(expectedVideo);
        when(youtubeVideoIdParser.parseYoutubeVideoId(any(String.class)))
                .thenReturn(videoId);

        List<Message> expectedReplies = Collections.singletonList(new TextMessage(expectedOutput));
        List<Message> actualReplies = youtubeInfoChatHandler.handleTextMessageEvent(
                me, new LinkedList<>()
        );

        verify(youtubeInfoFetcher).fetchData(eq(videoId));
        verify(youtubeVideoIdParser).parseYoutubeVideoId(eq(validUrl));
        assertEquals(expectedReplies, actualReplies);
    }

    @Test
    public void testHandleCorrectCommandInvalidUrl() throws IOException, JSONException {
        String videoId = "videoId";
        String invalidUrl = "https://yutube.com/watch?v=" + videoId;
        String text = "/youtube " + invalidUrl;
        when(youtubeVideoIdParser.parseYoutubeVideoId(any(String.class)))
                .thenThrow(new InvalidYoutubeVideoUrl("Invalid URL"));

        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent("re", text);

        List<Message> expectedReplies = Collections.singletonList(
                new TextMessage(YoutubeInfoChatHandler.MESSAGE_INVALID_URL)
        );
        List<Message> actualReplies = youtubeInfoChatHandler.handleTextMessageEvent(
                me, new LinkedList<>()
        );

        verify(youtubeVideoIdParser).parseYoutubeVideoId(eq(invalidUrl));
        verify(youtubeInfoFetcher, never()).fetchData(any(String.class));

        assertEquals(expectedReplies, actualReplies);
    }

    @Test
    public void testHandleVideoNotFound() throws IOException, JSONException {
        String videoId = "videoId";
        String validUrl = "https://youtube.com/watch?v=" + videoId;
        String text = "/youtube " + validUrl;
        String expectedOutput = "Video dengan ID " + videoId + " tidak dapat kami akses :(";
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent("re", text);
        when(youtubeInfoFetcher.fetchData(any(String.class)))
                .thenThrow(new YoutubeVideoNotFoundException(""));
        when(youtubeVideoIdParser.parseYoutubeVideoId(any(String.class)))
                .thenReturn(videoId);

        List<Message> expectedReplies = Collections.singletonList(new TextMessage(expectedOutput));
        List<Message> actualReplies = youtubeInfoChatHandler.handleTextMessageEvent(
                me, new LinkedList<>()
        );

        verify(youtubeInfoFetcher).fetchData(eq(videoId));
        verify(youtubeVideoIdParser).parseYoutubeVideoId(eq(validUrl));
        assertEquals(expectedReplies, actualReplies);
    }

    @Test
    public void testNotHandleIncorrectCommand() throws IOException, JSONException {
        String invalidCmd = "/google https://google.com/search?q=videoyutub";
        List<Message> previousMessages = Collections.singletonList(
                new TextMessage("previous messages")
        );
        MessageEvent<TextMessageContent> me = ChatHandlerTestUtil.fakeMessageEvent("re", invalidCmd);
        assertEquals(
                previousMessages,
                youtubeInfoChatHandler.handleTextMessageEvent(me, previousMessages)
        );
        verify(youtubeInfoFetcher, never()).fetchData(any(String.class));
        verify(youtubeVideoIdParser, never()).parseYoutubeVideoId(any(String.class));
    }

    @Test
    public void testIgnoreNonTextMessageEvent() {
        assertFalse(youtubeInfoChatHandler.canHandleAudioMessage(null));
        assertFalse(youtubeInfoChatHandler.canHandleImageMessage(null));
        assertFalse(youtubeInfoChatHandler.canHandleStickerMessage(null));
        assertFalse(youtubeInfoChatHandler.canHandleLocationMessage(null));
    }

}
