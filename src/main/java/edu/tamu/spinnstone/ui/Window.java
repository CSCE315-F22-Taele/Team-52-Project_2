package edu.tamu.spinnstone.ui;

import java.nio.file.Path;

import javax.swing.JFrame;

public class Window {
  public final int screenWidth = 1366;
  public final int screenHeight = 768;

  static final String projectRoot = System.getProperty("user.dir");


  // public String rootRelativePath(String path) {
  //   return Path.of(projectRoot, path).toString();
  // }

  public JFrame frame;

  public Window() {
    frame = new JFrame("Spinnstone");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(screenWidth, screenHeight);
  }

  public void show() {
    frame.setVisible(true);
  }

}
