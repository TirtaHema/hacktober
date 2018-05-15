package advprog.example.bot.Twitter;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TweetPostGetter {
    /**
     * Usage: java twitter4j.examples.timeline.GetUserTimeline
     *
     * @param numTweet String[]
     */
    public String[] getTweet(int numTweet, String user) {
        // gets Twitter instance with default credentials
        Twitter twitter = new TwitterFactory().getInstance();
        List<Status> statuses;
        String[] result = new String[numTweet];
        try {
            statuses = twitter.getUserTimeline(user);
            System.out.println("Showing @" + user + "'s user timeline.");
            for (int i = 0; i < numTweet; i++) {
                result[i] = statuses.get(i).getText() + "(" + statuses.get(i).getCreatedAt() + ")";
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }

        return result;
    }
}
