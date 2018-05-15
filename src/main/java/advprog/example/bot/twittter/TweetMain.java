package advprog.example.bot.twittter;

import twitter4j.TwitterException;

public class TweetMain {

    public static void main(String[] args) throws TwitterException {
        TweetPostGetter tweetGetter = new TweetPostGetter();
        tweetGetter.getTweet(5, "williamrumanta");
    }
}
