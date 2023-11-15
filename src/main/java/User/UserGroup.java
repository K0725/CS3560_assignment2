package User;

import java.util.ArrayList;
import java.util.List;

/**
 * The UserGroup class represents a group of users in the MiniTwitter application.
 * Each group has a unique group ID, a list of members, and a list of subgroups.
 */
public class UserGroup {
    private String groupID;
    private List<User> members;
    private List<UserGroup> subGroups;

    /**
     * Constructor for the UserGroup class.
     *
     * @param groupID The unique identifier for the group.
     */
    public UserGroup(String groupID) {
        this.groupID = groupID;
        this.members = new ArrayList<>();
        this.subGroups = new ArrayList<>();
    }

    /**
     * Gets the group ID of the user group.
     *
     * @return The group ID.
     */
    public String getGroupID() {
        return groupID;
    }

    /**
     * Gets the list of members in the user group.
     *
     * @return The list of members.
     */
    public List<User> getMembers() {
        return members;
    }

    /**
     * Gets the list of subgroups in the user group.
     *
     * @return The list of subgroups.
     */
    public List<UserGroup> getSubGroups() {
        return subGroups;
    }

    /**
     * Adds a user to the user group.
     *
     * @param user The user to be added to the group.
     */
    public void addUserToGroup(User user) {
        members.add(user);
    }

    /**
     * Adds a subgroup to the user group.
     *
     * @param subGroup The subgroup to be added.
     */
    public void addSubGroup(UserGroup subGroup) {
        // Check if the user is already in another group
        for (User user : subGroup.getMembers()) {
            if (isUserInAnotherGroup(user)) {
                // Handle the scenario where the user is in another group
                // You might want to remove the user from the other group or handle it according to your requirements
                // Here's an example of just printing a message
                System.out.println("User " + user.getUserID() + " is already in another group");
                return;
            }
        }

        subGroups.add(subGroup);
    }

    /**
     * Returns the string representation of the user group.
     *
     * @return The group ID as the string representation.
     */
    @Override
    public String toString() {
        return groupID;
    }

    /**
     * Checks if the user is in another group.
     *
     * @param user The user to check.
     * @return True if the user is in another group, false otherwise.
     */
    private boolean isUserInAnotherGroup(User user) {
        // Search through existing subgroups to see if the user is present in any other group
        for (UserGroup group : subGroups) {
            if (group.getMembers().contains(user)) {
                return true; // User is already in another group
            }
            if (group.getSubGroups().size() > 0 && group.isUserInAnotherGroup(user)) {
                return true; // User is found in a subgroup
            }
        }
        return false; // User is not in another group
    }
}
