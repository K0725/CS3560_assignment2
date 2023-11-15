package User;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The AdminControlPanel class represents the graphical user interface for the admin control panel
 * in a MiniTwitter application. It allows the admin to manage users and groups, view statistics,
 * and interact with the user tree.
 */
public class AdminControlPanel extends JFrame {

    private static TreeView treeView;
    private List<User> userList;
    private List<UserGroup> groupList;
    private TweetAnalyzer tweetAnalyzer;

    /**
     * Constructor for AdminControlPanel.
     * Initializes the tree view, user list, group list, and tweet analyzer.
     */
    public AdminControlPanel() {
        treeView = new TreeView();
        userList = new ArrayList<>();
        groupList = new ArrayList<>();
        tweetAnalyzer = new TweetAnalyzer();
    }

    /**
     * Creates and displays the graphical user interface for the admin control panel.
     */
    public void createGUI() {
        // Initializing the window
        JFrame frame = new JFrame("Mini Twitter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500, 200);

        JPanel adminPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 0, 0));
        JTextField t1 = placeholderText("User ID");
        JButton Add_User = new JButton("Add User");
        topPanel.add(t1);
        topPanel.add(Add_User);
        JTextField t2 = placeholderText("Group ID");
        JButton Add_Group = new JButton("Add Group");
        topPanel.add(t2);
        topPanel.add(Add_Group);

        JPanel middleColumn = new JPanel();
        middleColumn.setLayout(new BoxLayout(middleColumn, BoxLayout.Y_AXIS));
        middleColumn.add(Box.createVerticalStrut(2)); // Adjust the height
        JButton openUserViewButton = new JButton("Open User View");
        middleColumn.add(openUserViewButton);

        upperPanel.add(topPanel);
        centerPanel.add(upperPanel, BorderLayout.NORTH);
        centerPanel.add(middleColumn, BorderLayout.CENTER);

        // Initializing buttons
        JPanel bottomPanel = new JPanel(new GridLayout(2, 2, 0, 0));
        JButton userTotalButton = new JButton("Show User Total");
        JButton messageTotalButton = new JButton("Show Messages Total");
        JButton groupTotalButton = new JButton("Show Group Total");
        JButton positivePercentageButton = new JButton("Show Positive Percentage");
        bottomPanel.add(userTotalButton);
        bottomPanel.add(messageTotalButton);
        bottomPanel.add(groupTotalButton);
        bottomPanel.add(positivePercentageButton);
        lowerPanel.add(bottomPanel);
        centerPanel.add(lowerPanel, BorderLayout.SOUTH);

        // Set maximum size for buttons to expand horizontally
        userTotalButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, userTotalButton.getPreferredSize().height));
        messageTotalButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, messageTotalButton.getPreferredSize().height));
        groupTotalButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, groupTotalButton.getPreferredSize().height));
        positivePercentageButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, positivePercentageButton.getPreferredSize().height));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(treeView, BorderLayout.CENTER);
        treeView.setPreferredSize(new Dimension(300, 400));

        adminPanel.add(centerPanel, BorderLayout.CENTER);
        adminPanel.add(leftPanel, BorderLayout.WEST);
        frame.add(adminPanel);

        // Action listeners for buttons
        Add_User.addActionListener(e -> {
            String userID = t1.getText();
            boolean userExists = userList.stream().anyMatch(user -> user.getUserID().equals(userID));
            if (!userExists) {
                User newUser = new User(userID);
                userList.add(newUser);
                treeView.addUserToTree(userID);
            } else {
                System.out.println("User already exists!");
            }
        });

        Add_Group.addActionListener(e -> {
            String groupID = t2.getText();
            boolean groupExists = groupList.stream().anyMatch(group -> group.getGroupID().equals(groupID));
            if (!groupExists) {
                UserGroup newGroup = new UserGroup(groupID);
                groupList.add(newGroup);
                treeView.addUserGroupToTree(groupID);
            } else {
                System.out.println("Group already exists!");
            }
        });

        openUserViewButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeView.getTree().getLastSelectedPathComponent();
            if (selectedNode != null) {
                Object selectedObject = selectedNode.getUserObject();
                if (selectedObject instanceof User) {
                    User selectedUser = (User) selectedObject;
                    UserView userView = new UserView();
                    userView.createAndShowUserView(selectedUser);
                }
            }
        });

        userTotalButton.addActionListener(e -> {
            int totalUsers = getTotalUsers();
            JOptionPane.showMessageDialog(frame, "Total Users: " + totalUsers);
        });

        messageTotalButton.addActionListener(e -> {
            int totalMessages = getTotalTweets();
            JOptionPane.showMessageDialog(frame, "Total Messages: " + totalMessages);
        });

        groupTotalButton.addActionListener(e -> {
            int groupTotal = getTotalGroups();
            JOptionPane.showMessageDialog(frame, "Group Total: " + groupTotal);
        });

        positivePercentageButton.addActionListener(e -> {
            int totalPositiveWords = tweetAnalyzer.getTotalPositiveWords(userList);
            int totalWords = tweetAnalyzer.getTotalWords(userList);
            double positivePercentage = tweetAnalyzer.calculatePositivePercentage(userList);
            JOptionPane.showMessageDialog(frame, "Positive Percentage: " + positivePercentage + "%");
        });

        frame.pack();
        frame.setSize(900, 600);
        frame.setVisible(true);
    }

    /**
     * Adds a new user to the user list and updates the tree view.
     *
     * @param userID The ID of the new user.
     */
    public void addUser(String userID) {
        User newUser = new User(userID);
        userList.add(newUser);
        // Updating the Tree View
        TreeView treeView = getTreeView();
        treeView.addUserToTree(userID);
    }

    /**
     * Adds a new user group to the group list.
     *
     * @param groupID The ID of the new group.
     */
    public void addUserGroup(String groupID) {
        UserGroup newGroup = new UserGroup(groupID);
        groupList.add(newGroup); // Add the new user to the userList
    }

    /**
     * Gets the total number of users in the system.
     *
     * @return The total number of users.
     */
    public int getTotalUsers() {
        // Get the total count of users in the system
        return userList.size();
    }

    /**
     * Gets the total number of groups in the system.
     *
     * @return The total number of groups.
     */
    public int getTotalGroups() {
        // Get the total count of groups in the system
        return groupList.size();
    }

    /**
     * Gets the total number of tweets in the system.
     *
     * @return The total number of tweets.
     */
    public int getTotalTweets() {
        int totalTweets = 0;
        for (User user : userList) {
            totalTweets += user.getNewsFeed().size();
        }
        return totalTweets;
    }

    /**
     * Gets the TreeView instance associated with this AdminControlPanel.
     *
     * @return The TreeView instance.
     */
    public TreeView getTreeView() {
        return treeView;
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