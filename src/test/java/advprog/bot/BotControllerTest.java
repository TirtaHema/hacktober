package advprog.bot;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;
import advprog.bot.line.LineMessageReplyService;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BotControllerTest {

    @Mock
    LineMessageReplyService lineMessageReplyService;

    @Mock
    LineChatHandler baseChatHandler;

    BotController botController;

    @Captor
    private ArgumentCaptor<MessageEvent<TextMessageContent>> meTmcCaptor;

    @Captor
    private ArgumentCaptor<MessageEvent<ImageMessageContent>> meImcCaptor;

    @Captor
    private ArgumentCaptor<MessageEvent<AudioMessageContent>> meAmcCaptor;

    @Captor
    private ArgumentCaptor<MessageEvent<StickerMessageContent>> meSmcCaptor;


    @Before
    public void setUp() {
        botController = new BotController(lineMessageReplyService, baseChatHandler);
    }

    @Test
    public void testRegisterHandlerCorrectly() {
        AbstractLineChatHandlerDecorator handler = mock(AbstractLineChatHandlerDecorator.class);
        when(handler.getDecoratedLineChatHandler()).thenReturn(baseChatHandler);
        botController.replaceLineChatHandler(handler);
        assertEquals(handler, botController.getLineChatHandler());
    }

    @Test(expected = IllegalStateException.class)
    public void testHandleRegisterHandlerIncorrectly() {
        AbstractLineChatHandlerDecorator handler = mock(AbstractLineChatHandlerDecorator.class);
        botController.replaceLineChatHandler(handler);
    }

    @Test
    public void testHandleTextMessageEvent() {
        List<Message> expectedMessages = new LinkedList<>();
        expectedMessages.add(new TextMessage("1"));
        expectedMessages.add(new TextMessage("2"));
        when(baseChatHandler.handleTextMessageEvent(any(), any()))
                .thenReturn(expectedMessages);
        String expectedReplyToken = "re";
        botController.handleTextMessageEvent(new MessageEvent<>(
                expectedReplyToken, null, null, null
        ));
        verify(lineMessageReplyService).reply(eq(expectedReplyToken), eq(expectedMessages));
    }

    @Test
    public void testHandleImageMessageEvent() {
        List<Message> expectedMessages = new LinkedList<>();
        String dummyUrl = "https://null.null";
        expectedMessages.add(new ImageMessage(dummyUrl, dummyUrl));
        expectedMessages.add(new ImageMessage(dummyUrl, dummyUrl));
        when(baseChatHandler.handleImageMessageEvent(any(), any()))
                .thenReturn(expectedMessages);
        String expectedReplyToken = "re";
        botController.handleImageMessageEvent(new MessageEvent<>(
                expectedReplyToken, null, null, null
        ));
        verify(lineMessageReplyService).reply(eq(expectedReplyToken), eq(expectedMessages));
    }

    @Test
    public void testHandleAudioMessageEvent() {
        List<Message> expectedMessages = new LinkedList<>();
        String dummyUrl = "https://null.null";
        expectedMessages.add(new AudioMessage(dummyUrl, 1));
        expectedMessages.add(new AudioMessage(dummyUrl, 3));
        when(baseChatHandler.handleAudioMessageEvent(any(), any()))
                .thenReturn(expectedMessages);
        String expectedReplyToken = "re";
        botController.handleAudioMessageEvent(new MessageEvent<>(
                expectedReplyToken, null, null, null
        ));
        verify(lineMessageReplyService).reply(eq(expectedReplyToken), eq(expectedMessages));
    }

    @Test
    public void testHandleStickerMessageEvent() {
        List<Message> expectedMessages = new LinkedList<>();
        String dummyId = "2fgfr874";
        expectedMessages.add(new StickerMessage(dummyId, dummyId));
        expectedMessages.add(new StickerMessage(dummyId, dummyId));
        when(baseChatHandler.handleStickerMessageEvent(any(), any()))
                .thenReturn(expectedMessages);
        String expectedReplyToken = "re";
        botController.handleStickerMessageEvent(new MessageEvent<>(
                expectedReplyToken, null, null, null
        ));
        verify(lineMessageReplyService).reply(eq(expectedReplyToken), eq(expectedMessages));
    }

    @Test
    public void testHandleLocationMessageEvent() {
        List<Message> expectedMessages = new LinkedList<>();
        String dummyId = "2fgfr874";
        expectedMessages.add(new LocationMessage(dummyId, dummyId, 23, 123));
        expectedMessages.add(new TextMessage(dummyId));
        when(baseChatHandler.handleLocationMessageEvent(any(), any()))
                .thenReturn(expectedMessages);
        String expectedReplyToken = "re";
        botController.handleLocationMessageEvent(new MessageEvent<>(
                expectedReplyToken, null, null, null
        ));
        verify(lineMessageReplyService).reply(eq(expectedReplyToken), eq(expectedMessages));
    }

    @Test
    public void testDefaultMessageEvent() {
        Event event = mock(Event.class);
        Source source = mock(Source.class);
        when(event.getSource()).thenReturn(source);
        when(event.getTimestamp()).thenReturn(Instant.now());
        botController.handleDefaultMessage(event);
        verify(baseChatHandler, never()).handleTextMessageEvent(any(), any());
    }


}
