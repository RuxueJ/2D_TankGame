package tankrotationexample.menus;

import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class PauseGamePanel extends Screen{
    public PauseGamePanel(Launcher lf) {
        super(lf);
        this.menuBackground = ResourceManager.getSprite("menu");

        JButton resume = new JButton("Resume");

        resume.setFont(new Font("Courier New", Font.BOLD, 24));
        resume.setBounds(150, 300, 150, 50);
        resume.addActionListener(actionEvent -> this.lf.setFrame("game"));

        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 400, 150, 50);
        exit.addActionListener((actionEvent -> this.lf.closeGame()));

        this.add(resume);
        this.add(exit);
    }

    @Override
    public void paintComponent(Graphics g) {

    }
}
