package User;

/**
 * The UserObserver interface defines a contract for classes that want to observe or listen for updates
 * related to a user in the MiniTwitter application.
 */
public interface UserObserver {

    /**
     * This method is called when there's an update related to a user.
     *
     * @param tweet The update information, typically a tweet message.
     */
    void update(String tweet);
}
