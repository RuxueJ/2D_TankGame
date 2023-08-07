package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Invisibility extends GameObject implements PowerUps{
    public Invisibility(float x, float y, BufferedImage img) {
        super( x,  y,  img);
    }

    @Override
    public void drawImage(Graphics buffer) {
        if(!this.ifHasDestroyed()){
            buffer.drawImage(img,(int)x, (int)y, null);
        }

    }


    @Override
    public void applyPowerUp(Tank tank) {
        ResourceManager.getSound("pickup").playSound();
        this.setHasDestroyed(true);
        tank.setInVisible();
        tank.setBlood(tank.getBlood()+20);

    }
}
