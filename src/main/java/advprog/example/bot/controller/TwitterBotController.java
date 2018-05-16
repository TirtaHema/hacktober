package advprog.example.bot.controller;

import advprog.example.bot.twittter.TweetPostGetter;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

public class TwitterBotController {

    @Autowired
    private LineMessagingClient lineMessagingClient;
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
    //    public String[] handleTextEvent(MessageEvent<TextMessageContent> messageEvent){
    //        System.out.println("asu");
    //        String pesan = messageEvent.getMessage().getText().toLowerCase();
    //        String[] pesanSplit = pesan.split(" ");
    //        String[] replyPosts = new String[5];
    //        if(pesanSplit[0].equals("/tweet") && pesanSplit[1].equals("recent")){
    //            String user = pesanSplit[2];
    //            replyPosts = tweetGetter.getTweet(5, user);
    //            String replyToken = messageEvent.getReplyToken();
    //            for (int i = 0; i < replyPosts.length; i++) {
    //                String tweet = replyPosts[i];
    //                balasChatDenganRandomJawaban(replyToken, tweet);
    //            }
    //        }
    //        return replyPosts;
    //    }
    //
    //    private void balasChatDenganRandomJawaban(String replyToken, String jawaban){
    //        TextMessage response = new TextMessage(jawaban);
    //        try {
    //            CompletableFuture<BotApiResponse> msg = lineMessagingClient
    //              .replyMessage(new ReplyMessage(replyToken, response));
    //            System.out.println("lalalala");
    //            System.out.println(msg.toString());
    //            msg.get();
    //        } catch (InterruptedException | ExecutionException e) {
    //            System.out.println("Ada error saat ingin membalas chat");
    //        }
    //    }
}

