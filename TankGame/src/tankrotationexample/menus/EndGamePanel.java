package tankrotationexample.menus;

import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class EndGamePanel extends Screen {

    private String winnerMessage;



    public EndGamePanel(Launcher lf, String winnerMessage) {
        super(lf);

        this.menuBackground = ResourceManager.getSprite("menu");
        this.winnerMessage = winnerMessage;

        System.out.println("EndPanel: " + this.winnerMessage);

        JLabel winner =  new JLabel(winnerMessage);
        winner.setFont(new Font("Courier New", Font.BOLD, 24));
        winner.setBounds(150, 300, 250, 50);

        JButton start = new JButton("Restart Game");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 300, 250, 50);
        start.addActionListener((actionEvent -> {this.lf.setFrame("game");
        }));


        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 400, 250, 50);
        exit.addActionListener((actionEvent -> this.lf.closeGame()));

        this.add(winner);
        this.add(start);
        this.add(exit);

    }

}
