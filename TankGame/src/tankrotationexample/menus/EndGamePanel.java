package tankrotationexample.menus;

import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class EndGamePanel extends Screen {

    private String winnerMessage;

    public void setWinner(int winner) {
        if(winner == 1){
            this.winnerMessage = "RED TANK WINS!";
        }else{
            this.winnerMessage = "BLUE TANK WINS";
        }

    }

    public EndGamePanel(Launcher lf) {
        super(lf);

        this.menuBackground = ResourceManager.getSprite("menu");

        JButton start = new JButton("Restart Game");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 300, 250, 50);
        start.addActionListener((actionEvent -> {this.lf.setFrame("game");
        }));




        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 400, 250, 50);
        exit.addActionListener((actionEvent -> this.lf.closeGame()));


        this.add(start);
        this.add(exit);

    }
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(this.menuBackground, 0, 0, null);


        Font font = new Font("Courier New", Font.BOLD, 24);
        g2.setFont(font);
        if(this.winnerMessage == "RED TANK WINS!"){
            g2.setColor(Color.RED);
        }else{
            g2.setColor(Color.BLUE);
        }


        g2.drawString(this.winnerMessage, 150,300);


    }

}
