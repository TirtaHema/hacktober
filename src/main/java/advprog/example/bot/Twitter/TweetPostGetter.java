package advprog.example.bot.Twitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.Scanner;

import java.util.List;

public class TweetPostGetter {
    /**
     * Usage: java twitter4j.examples.timeline.GetUserTimeline
     *
     * @param numTweet String[]
     */
    public void getTweet(int numTweet) {
        // gets Twitter instance with default credentials
        Twitter twitter = new TwitterFactory().getInstance();
        List<Status> statuses;
        String user;
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Please input the username: ");
            user = in.nextLine();
            statuses = twitter.getUserTimeline(user);
            System.out.println("Showing @" + user + "'s user timeline.");
            for (int i=0; i < numTweet; i++) {
                System.out.println(statuses.get(i).getText() + "(" + statuses.get(i).getCreatedAt() + ")");
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
}
