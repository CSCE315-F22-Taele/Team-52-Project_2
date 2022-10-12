package edu.tamu.spinnstone.ui.screens;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import edu.tamu.spinnstone.ui.Window;


/*
 * @author ServerScreen
 * This class is the UI for the Server
 */
public class ServerScreen {
  Window window;
  JPanel screen;
  BufferedImage background;

  /*
   * @param window  This connects the window and the Jpanel screen
   * @throws IOException
   */
  public ServerScreen(Window window) throws IOException {
    this.window = window;
    screen = new JPanel();
    screen.setSize(window.screenWidth, window.screenHeight);
  }

  public void render() {
    // JLabel label = new JLabel(new ImageIcon(background));
    // screen.add(label);
    // label.setSize(window.screenWidth, window.screenHeight);

    JFrame f = window.frame;
    JPanel panel = new JPanel();
    panel.setBounds(0, 0, window.screenWidth, window.screenHeight);
    panel.setBackground(Color.LIGHT_GRAY);

    JButton cheesePizzaButton = new JButton("Cheese");
    generateButton(cheesePizzaButton, 100, 100);

    JButton oneToppingPizzaButton = new JButton("1 Topping");
    generateButton(oneToppingPizzaButton, 350, 100);

    JButton BYOpizzaButton = new JButton("Build Your Own");
    generateButton(BYOpizzaButton, 600, 100);

    JButton checkOutButton = new JButton("Checkout");
    generateButton(checkOutButton, 100, 600);
    
    // JPanel greenPanelCol = new JPanel();
    // greenPanelCol.setBackground(Color.green);
    // greenPanelCol.setPreferredSize(new Dimension(100, window.screenHeight));
    // greenPanelCol.setLocation(0, 0);

    // panel.add(greenPanelCol);
    panel.add(cheesePizzaButton);
    panel.add(oneToppingPizzaButton);
    panel.add(BYOpizzaButton);
    panel.add(checkOutButton);
    f.add(panel);
    f.setLayout(null);
    panel.setLayout(null);
    f.setVisible(true);
  }

  /*
   * @param JButton to generate the buttons on the GUI. Buttons all look the same
   * @param xCoord
   * @param yCoord
   */
  public void generateButton(JButton button, int xCoord, int yCorrd) {
    //button.setPreferredSize(new Dimension(250, 50));
    button.setBounds(xCoord, yCorrd, 200, 50);
    button.setFont(new Font("Arial", Font.PLAIN, 18));
    button.setBackground(Color.BLACK);
    button.setForeground(Color.WHITE);
  }
}
