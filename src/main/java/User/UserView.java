package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

/**
 * The UserView class represents the graphical user interface for individual users in a MiniTwitter application.
 * It displays the user's information, such as followers, following users, and their news feed.
 * Implements the TweetObserver interface to receive updates when the user posts a new tweet.
 */
public class UserView implements UserObserver {

    private User new_user;
    private DefaultListModel<String> newsFeedModel;
    private JList<String> newsFeedList;

    /**
     * Creates and displays the graphical user interface for a specific user.
     *
     * @param user The User object for which the view is created.
     */
    public void createAndShowUserView(User user) {
        this.new_user = user;
        new_user.addObserver(this);

        // Header Title
        JFrame frame = new JFrame("MiniTwitter");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(800, 200);

        // Grid for the Panel
        JPanel userPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Top panel with user ID input and Follow User button
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JTextField t1 = placeholderText("User ID");
        JButton b1 = new JButton("Follow User");
        topPanel.add(t1);
        topPanel.add(b1);

        List<User> followingUsers = new_user.getFollowings();
        DefaultListModel<String> followingListModel = new DefaultListModel<>();
        for (User following : followingUsers) {
            followingListModel.addElement(following.getUserID());
        }
        JList<String> followingList = new JList<>(followingListModel);

        JScrollPane scrollPane = new JScrollPane(followingList);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        userPanel.add(topPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        userPanel.add(scrollPane, gbc);

        // Bottom panel with news feed, tweet input, and Post Tweet button
        List<String> newsFeed = new_user.getNewsFeed();
        newsFeedModel = new DefaultListModel<>();
        for (String tweet : newsFeed) {
            newsFeedModel.addElement(tweet);
        }
        newsFeedList = new JList<>(newsFeedModel);

        JScrollPane scrollPane2 = new JScrollPane(newsFeedList);

        JPanel middlePanel = new JPanel(new GridLayout(1, 2, 0, 0));
        JTextField t2 = placeholderText("Tweet Message");
        JButton b2 = new JButton("Post Tweet");
        middlePanel.add(t2);
        middlePanel.add(b2);

        b2.addActionListener(e -> {
            String newTweet = t2.getText();
            new_user.postTweet(newTweet); // Post a new tweet
        });

        JPanel bottomPanelContainer = new JPanel(new GridBagLayout());
        GridBagConstraints bottomGBC = new GridBagConstraints();

        bottomGBC.fill = GridBagConstraints.HORIZONTAL;
        bottomGBC.weightx = 1.0;
        bottomGBC.anchor = GridBagConstraints.NORTH;
        bottomPanelContainer.add(middlePanel, bottomGBC);

        bottomGBC.gridy = 1;
        bottomGBC.weighty = 1.0;
        bottomGBC.fill = GridBagConstraints.BOTH;
        bottomPanelContainer.add(scrollPane2, bottomGBC);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        userPanel.add(bottomPanelContainer, gbc);

        // Add the userPanel to the frame
        frame.add(userPanel);

        // Display the window
        frame.pack();
        frame.setSize(300, 600);
        frame.setVisible(true);
    }

    /**
     * Updates the news feed view when a new tweet is posted by the observed user.
     *
     * @param tweet The new tweet posted by the user.
     */
    @Override
    public void update(String tweet) {
        SwingUtilities.invokeLater(() -> newsFeedModel.addElement(tweet));
        newsFeedList.setModel(newsFeedModel); // Refreshes the view
    }

    /**
     * Creates a JTextField with a placeholder text.
     *
     * @param placeholder The placeholder text to be displayed initially in the text field.
     * @return JTextField with a placeholder.
     */
    private static JTextField placeholderText(String placeholder) {
        JTextField text = new JTextField(10);
        text.setText(placeholder);
        text.setForeground(Color.GRAY);

        // Add FocusListener to remove placeholder when the text field gains focus
        text.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (text.getText().equals(placeholder)) {
                    text.setText("");
                    text.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (text.getText().isEmpty()) {
                    text.setText(placeholder);
                    text.setForeground(Color.GRAY);
                }
            }
        });

        return text;
    }
}