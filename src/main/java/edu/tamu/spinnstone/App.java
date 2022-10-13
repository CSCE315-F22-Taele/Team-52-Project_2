package edu.tamu.spinnstone;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.ui.ScreenManager;
import edu.tamu.spinnstone.ui.screens.ServerScreen;

import javax.swing.*;
import java.awt.*;

public final class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Spin N' Stone");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Database db = new Database();
        db.connect();
        ServerScreen serverScreen = new ServerScreen(db);

        javax.swing.SwingUtilities.invokeLater(
                () -> {
                    try {
                        UIManager.put("ToggleButton.select", Color.getColor("#197278"));
                    } catch (Exception e) {
                        System.out.println("unable to set look and feel");
                    }
                    frame.setSize(980, 735);
                    frame.setContentPane(serverScreen.screen);
                    frame.setVisible(true);
                });
    }
}
