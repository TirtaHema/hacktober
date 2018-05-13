package advprog.bot.feature.yerlandinata.youtubeinfo;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import advprog.bot.ChatHandlerTestUtil;
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

    YoutubeInfoChatHandler youtubeInfoChatHandler;

    @Before
    public void setUp() {
        youtubeInfoChatHandler = new YoutubeInfoChatHandler(
                new BaseChatHandler(), youtubeInfoFetcher
        );
    }

    @Test
    public void testHandleCorrectCommandCorrectUrl() throws IOException, JSONException {
        String videoId = "videoId";
        String validUrl = "https://youtube.com/watch?v=" + videoId;
        String text = "/youtube " + validUrl;
        YoutubeVideo expectedVideo = new YoutubeVideo(
                "How to pass Advanced Programming",
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

        List<Message> expectedReplies = Collections.singletonList(new TextMessage(expectedOutput));
        List<Message> actualReplies = youtubeInfoChatHandler.handleTextMessageEvent(
                me, new LinkedList<>()
        );

        verify(youtubeInfoFetcher).fetchData(eq(videoId));
        assertEquals(expectedReplies, actualReplies);
    }

}
