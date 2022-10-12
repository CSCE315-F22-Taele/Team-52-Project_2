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

public class ServerScreen {
  Window window;
  JPanel screen;
  BufferedImage background;

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
    cheesePizzaButton.setPreferredSize(new Dimension(250, 50));
    cheesePizzaButton.setFont(new Font("Arial", Font.PLAIN, 18));
    cheesePizzaButton.setBackground(Color.BLACK);
    cheesePizzaButton.setForeground(Color.WHITE);

    JButton oneToppingPizzaButton = new JButton("1 Topping");
    oneToppingPizzaButton.setPreferredSize(new Dimension(250, 50));
    oneToppingPizzaButton.setFont(new Font("Arial", Font.PLAIN, 18));
    oneToppingPizzaButton.setBackground(Color.BLACK);
    oneToppingPizzaButton.setForeground(Color.WHITE);

    JButton BYOpizzaButton = new JButton("Build Your Own");
    BYOpizzaButton.setPreferredSize(new Dimension(250, 50));
    BYOpizzaButton.setFont(new Font("Arial", Font.PLAIN, 18));
    BYOpizzaButton.setBackground(Color.BLACK);
    BYOpizzaButton.setForeground(Color.WHITE);

    JPanel greenPanelCol = new JPanel();
    greenPanelCol.setBackground(Color.green);
    greenPanelCol.setBounds(0, 0, window.screenWidth, window.screenHeight);
    greenPanelCol.setPreferredSize(new Dimension(100, window.screenHeight));

    
    panel.add(cheesePizzaButton);
    panel.add(oneToppingPizzaButton);
    panel.add(BYOpizzaButton);
    panel.add(greenPanelCol);
    f.add(panel);
    f.setLayout(null);
    f.setVisible(true);
  }
}
