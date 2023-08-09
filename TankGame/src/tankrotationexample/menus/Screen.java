package tankrotationexample.menus;

import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class Screen extends JPanel {
    public BufferedImage menuBackground;
    public final Launcher lf;

    public Screen(Launcher lf) {
        this.lf = lf;
        this.setBackground(Color.BLACK);
        this.setLayout(null);
    }

    abstract public void paintComponent(Graphics g);
}
