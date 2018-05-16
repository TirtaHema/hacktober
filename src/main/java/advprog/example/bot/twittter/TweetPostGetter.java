package advprog.example.bot.twittter;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TweetPostGetter {
    /**
     * Usage: java twitter4j.examples.timeline.GetUserTimeline
     *
     * @param numTweet String[]
     */
    public String[] getTweet(int numTweet, String user) {
        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("GD0eZjIgvHzqHEr6maDr6GUX3")
                .setOAuthConsumerSecret("ce4ZV0eEACo2gwPIOy8mFhsQf18KGGY84ZSryGLsXwF6AcdtoS")
                .setOAuthAccessToken("1590441104-We6TVAGDt6iQwoHKyWb16xUwQ3S72GFPyjlxVlS")
                .setOAuthAccessTokenSecret("ifHYd1YdixfohK11lsxRtmB7SRPP7PUA54aL7SYAJNUyB");

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        List<Status> statuses;
        String[] result = new String[numTweet];
        try {
            statuses = twitter.getUserTimeline(user);
            System.out.println("Showing @" + user + "'s user timeline.");
            for (int i = 0; i < numTweet; i++) {
                result[i] = statuses.get(i).getText() + "(" + statuses.get(i).getCreatedAt().toLocaleString() + ")";
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }

        return result;
    }
}
