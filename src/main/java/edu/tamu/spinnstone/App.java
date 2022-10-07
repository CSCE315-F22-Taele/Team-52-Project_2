package edu.tamu.spinnstone;

import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JLabel;

import edu.tamu.spinnstone.ui.Window;
import edu.tamu.spinnstone.ui.screens.ServerScreen;

public final class App {
  
  public static void main(String[] args) throws Exception {
    Window window = new Window();
    ServerScreen serverScreen = new ServerScreen(window);
    javax.swing.SwingUtilities.invokeLater(() -> {
      window.show();
      serverScreen.render();
    });
  }
}
