package Driver;

import User.AdminControlPanel;

public class Driver {
    public static void main(String[] args) {
        AdminControlPanel adminPanel = new AdminControlPanel();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                adminPanel.createGUI();
            }
        });
    }
}

