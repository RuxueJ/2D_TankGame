package tankrotationexample.menus;


import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartMenuPanel extends Screen {

    public StartMenuPanel(Launcher lf) {
        super(lf);

        this.menuBackground = ResourceManager.getSprite("menu");

        JButton mode1= new JButton("1 VS 1");
        mode1.setFont(new Font("Courier New", Font.BOLD, 24));
        mode1.setBounds(150, 280, 150, 50);
        mode1.addActionListener(actionEvent -> this.lf.setFrame("game"));

        JButton mode2= new JButton("Team");
        mode2.setFont(new Font("Courier New", Font.BOLD, 24));
        mode2.setBounds(150, 350, 150, 50);
        mode2.addActionListener(actionEvent -> this.lf.setFrame("game"));

        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 420, 150, 50);
        exit.addActionListener((actionEvent -> this.lf.closeGame()));

        this.add(mode1);
        this.add(mode2);
        this.add(exit);
    }

}
