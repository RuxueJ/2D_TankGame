package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tranform extends GameObject implements PowerUps{
    public Tranform(float x, float y, BufferedImage img) {
        super(x, y, img);
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
        tank.setTransformed(true);
        tank.setCooldown(50);
        this.setHasDestroyed(true);
    }
}
