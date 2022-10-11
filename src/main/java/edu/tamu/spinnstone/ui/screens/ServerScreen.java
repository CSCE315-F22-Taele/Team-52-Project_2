package edu.tamu.spinnstone.ui.screens;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import edu.tamu.spinnstone.ui.Window;

public class ServerScreen {
  Window window;
  JPanel screen;
  BufferedImage background;
  public ServerScreen(Window window) throws IOException{
    this.window = window;
    screen = new JPanel();
    screen.setSize(window.screenWidth, window.screenHeight);
    background = ImageIO.read(new File(
      window.rootRelativePath("src/main/java/edu/tamu/spinnstone/ui/assets/server_view.png")
    ));

    
  }
  
  public void render() {
    JLabel label = new JLabel(new ImageIcon(background));
    screen.add(label);
    label.setSize(window.screenWidth, window.screenHeight);
    window.frame.add(screen);

  }
}
