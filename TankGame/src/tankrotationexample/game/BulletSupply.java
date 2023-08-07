package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BulletSupply extends GameObject implements PowerUps{



    public BulletSupply(float x, float y, BufferedImage unbreak) {
        super(x,y,unbreak);
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

        tank.setBulletNumber(tank.getBulletNumber()+100);
        this.setHasDestroyed(true);

    }
}


