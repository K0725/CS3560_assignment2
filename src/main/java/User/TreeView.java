package User;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * A JPanel class representing a tree view for managing users and user groups.
 */
public class TreeView extends JPanel {

    private DefaultMutableTreeNode rootNode; // The root node of the tree
    private JTree tree; // The JTree component to display the hierarchy

    /**
     * Constructs a new TreeView with an initial root node.
     */
    public TreeView() {
        rootNode = new DefaultMutableTreeNode(new UserGroup("Root"));
        tree = new JTree(rootNode);

        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        // Set the selection path to the root node initially
        tree.setSelectionPath(new TreePath(rootNode));
    }

    /**
     * Gets the JTree component.
     *
     * @return The JTree component.
     */
    public JTree getTree() {
        return tree;
    }

    /**
     * Adds a user to the selected node in the tree.
     *
     * @param userID The ID of the user to be added.
     */
    public void addUserToTree(String userID) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode != null) {
            Object userObject = selectedNode.getUserObject();

            if (userObject instanceof UserGroup) {
                // If the selected node is a UserGroup, add a new user to it
                UserGroup selectedGroup = (UserGroup) userObject;
                User newUser = new User(userID);

                selectedGroup.addUserToGroup(newUser);
                DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(newUser);
                selectedNode.add(userNode);

                // Reload the model to reflect changes
                ((DefaultTreeModel) tree.getModel()).reload(selectedNode);

                // Set the selection path to the added user
                tree.setSelectionPath(new TreePath(selectedNode.getPath()));
                System.out.println("Added user " + userID + " to " + selectedGroup.getGroupID());

                // Check if the group was initially empty and expand the group node
                if (selectedNode.getChildCount() == 1) {
                    TreePath path = new TreePath(((DefaultTreeModel) tree.getModel()).getPathToRoot(selectedNode));
                    tree.expandPath(path);
                }
            } else {
                System.out.println("You can only add a user to a group, not to another user or root.");
            }
        } else {
            System.out.println("Please select a node to add the user.");
        }
    }

    /**
     * Adds a user group to the selected node in the tree.
     *
     * @param groupID The ID of the user group to be added.
     */
    public void addUserGroupToTree(String groupID) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode != null) {
            Object userObject = selectedNode.getUserObject();

            if (userObject instanceof UserGroup) {
                // If the selected node is a UserGroup, add a new group to it
                UserGroup selectedGroup = (UserGroup) userObject;
                UserGroup newGroup = new UserGroup(groupID);

                DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(newGroup.getGroupID());
                selectedGroup.addSubGroup(newGroup);
                groupNode.setUserObject(newGroup);

                selectedNode.add(groupNode);

                // Reload the model to reflect changes
                ((DefaultTreeModel) tree.getModel()).reload(selectedNode);
                System.out.println("Added group " + groupID + " to " + selectedGroup.getGroupID());

                // Check if the group was initially empty and expand the group node
                if (selectedNode.getChildCount() == 1) {
                    TreePath path = new TreePath(((DefaultTreeModel) tree.getModel()).getPathToRoot(selectedNode));
                    tree.expandPath(path);
                }
            } else {
                System.out.println("You can only add a group to another group, not a user.");
            }
        } else {
            System.out.println("Please select a node to add the group.");
        }
    }
}
