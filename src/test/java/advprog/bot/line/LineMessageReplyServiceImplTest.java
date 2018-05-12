package advprog.bot.line;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.response.BotApiResponse;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LineMessageReplyServiceImplTest {

    @Mock
    LineMessagingClient lineMessagingClient;

    @Mock
    CompletableFuture<BotApiResponse> response;

    LineMessageReplyService lineMessageReplyService;

    @Before
    public void setUp() {
        lineMessageReplyService = new LineMessageReplyServiceImpl(lineMessagingClient);
    }

    @Test
    public void testReply() throws ExecutionException, InterruptedException {
        String replyToken = "token";
        List<Message> msg = new LinkedList<>();
        when(lineMessagingClient.replyMessage(eq(new ReplyMessage(replyToken, msg))))
                .thenReturn(response);
        lineMessageReplyService.reply(replyToken,msg);
        verify(response).get();
    }

    @Test(expected = RuntimeException.class)
    public void testHandleReplyFail() throws ExecutionException, InterruptedException {
        String replyToken = "token";
        List<Message> msg = Collections.emptyList();
        when(lineMessagingClient.replyMessage(eq(new ReplyMessage(replyToken, msg))))
                .thenReturn(response);
        when(response.get()).thenThrow(new InterruptedException());
        lineMessageReplyService.reply(replyToken,msg);
    }

}
