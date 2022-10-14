package edu.tamu.spinnstone;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.ui.Actions;
import edu.tamu.spinnstone.ui.ScreenManager;
import edu.tamu.spinnstone.ui.screens.ServerScreen;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public final class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Spin N' Stone");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Database db = new Database();
        db.connect();

        Actions.getDatabase.onNext(db);

        // disable console output
        PrintStream ps = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        });

        System.setOut(ps);

        ScreenManager screenManager = new ScreenManager(db);

        javax.swing.SwingUtilities.invokeLater(
                () -> {
                    try {
                        UIManager.put("ToggleButton.select", Color.getColor("#197278"));
                    } catch (Exception e) {
                        System.out.println("unable to set look and feel");
                    }
                    frame.setSize(980, 735);
                    frame.setContentPane(screenManager.manageContainer);
                    frame.setVisible(true);
                });
    }
}
