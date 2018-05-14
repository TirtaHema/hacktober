package advprog.example.bot.Twitter;

public class TweetMain {

    public static void main(String[] args) {
        TweetPostGetter tweetGetter = new TweetPostGetter();
        tweetGetter.getTweet(5);
    }
}
