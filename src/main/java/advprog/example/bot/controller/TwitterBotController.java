package advprog.example.bot.controller;

import advprog.example.bot.Twitter.TweetPostGetter;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;

public class TwitterBotController {

    @Autowired
    private LineMessagingClient lineMessagingClient;
    private TweetPostGetter tweetGetter;

    public TwitterBotController() {
        tweetGetter = new TweetPostGetter();
    }

    @EventMapping
    public String[] handleTextEvent(MessageEvent<TextMessageContent> messageEvent){
        System.out.println("asu");
        String pesan = messageEvent.getMessage().getText().toLowerCase();
        String[] pesanSplit = pesan.split(" ");
        String[] replyPosts = new String[5];
        if(pesanSplit[0].equals("/tweet") && pesanSplit[1].equals("recent")){
            String user = pesanSplit[2];
            replyPosts = tweetGetter.getTweet(5, user);
            String replyToken = messageEvent.getReplyToken();
            for (int i = 0; i < replyPosts.length; i++) {
                String tweet = replyPosts[i];
                balasChatDenganRandomJawaban(replyToken, tweet);
            }
        }
        return replyPosts;
    }

    private void balasChatDenganRandomJawaban(String replyToken, String jawaban){
        TextMessage jawabanDalamBentukTextMessage = new TextMessage(jawaban);
        try {
            lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, jawabanDalamBentukTextMessage))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Ada error saat ingin membalas chat");
        }
    }
}

