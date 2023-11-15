package User;

import java.util.ArrayList;
import java.util.List;

/**
 * The User class represents a user in the MiniTwitter application.
 * Each user has a unique user ID, followers, a list of users they are following,
 * a news feed, and a list of tweet observers.
 */
public class User {
    private String userID;
    private List<User> followers;
    private List<User> following;
    private List<String> newsFeed;
    private List<UserObserver> Observers;

    /**
     * Constructor for the User class.
     *
     * @param userID The unique identifier for the user.
     */
    public User(String userID) {
        this.userID = userID;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
        this.Observers = new ArrayList<>();
    }

    /**
     * Gets the user ID of the user.
     *
     * @return The user ID.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Gets the list of followers for the user.
     *
     * @return The list of followers.
     */
    public List<User> getFollowers() {
        return followers;
    }

    /**
     * Gets the list of users that the user is following.
     *
     * @return The list of users being followed.
     */
    public List<User> getFollowings() {
        return following;
    }

    /**
     * Gets the news feed of the user.
     *
     * @return The news feed.
     */
    public List<String> getNewsFeed() {
        return newsFeed;
    }

    /**
     * Posts a new tweet to the user's news feed.
     *
     * @param tweet The tweet message to be posted.
     */
    public void postTweet(String tweet) {
        newsFeed.add(tweet);
        // Notify observers about the new tweet
        notifyObservers(tweet);
    }

    /**
     * Adds a tweet observer to the user's list of observers.
     *
     * @param observer The observer to be added.
     */
    public void addObserver(UserObserver observer) {
        Observers.add(observer);
    }

    /**
     * Notifies all tweet observers about a new tweet.
     *
     * @param tweet The new tweet message.
     */
    private void notifyObservers(String tweet) {
        for (UserObserver observer : Observers) {
            observer.update(tweet);
        }
    }

    /**
     * Returns the string representation of the user.
     *
     * @return The user ID as the string representation.
     */
    @Override
    public String toString() {
        return userID;
    }
}

