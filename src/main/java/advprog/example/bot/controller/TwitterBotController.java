package advprog.example.bot.controller;

import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.example.bot.twittter.TweetPostGetter;
import com.linecorp.bot.model.event.MessageEvent;

import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;


public class TwitterBotController extends AbstractLineChatHandlerDecorator {

    @Autowired
    private TweetPostGetter tweetGetter;
    private static final Logger LOGGER = Logger.getLogger(TwitterBotController.class.getName());

    public TwitterBotController() {
        tweetGetter = new TweetPostGetter();
    }

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] pesanSplit = contentText.split(" ");
        String[] replyPosts = new String[5];
        String res = "";
        if (pesanSplit[0].equals("/tweet") && pesanSplit[1].equals("recent")) {
            String user = pesanSplit[2];
            replyPosts = tweetGetter.getTweet(5, user);
            for (int i = 0; i < replyPosts.length; i++) {
                String tweet = replyPosts[i];
                res += tweet += "\n";
            }
        } else if (pesanSplit[0].equals("/echo")) {
            String replyText = contentText.replace("/echo", "");
            return new TextMessage(replyText.substring(1));
        }
        return new TextMessage(res);
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return false;
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

