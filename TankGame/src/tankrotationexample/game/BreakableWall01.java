package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall01 extends Wall {
    public BreakableWall01(float x, float y, BufferedImage breakable) {

        super( x,  y,  breakable);
    }

    @Override
    public void collides() {
        this.setHasDestroyed(true);
        ResourceManager.getSound("shotfire").playSound();

    }

    @Override
    public void drawImage(Graphics buffer) {
        if(!this.ifHasDestroyed()){
            buffer.drawImage( this.img,(int)x,(int)y, null);
        }

    }


}
