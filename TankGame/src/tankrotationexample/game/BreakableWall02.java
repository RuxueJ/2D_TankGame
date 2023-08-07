package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall02 extends Wall{
    private int health = 2;

    public BreakableWall02(float x, float y, BufferedImage breakable) {

        super( x,  y,  breakable);
    }


    @Override
    public void collides() {
        this.health --;
        if(this.health == 1){
            this.img = ResourceManager.getSprite("break1");
        }
        if(this.health == 0){
            this.setHasDestroyed(true);
        }
    }
    @Override
    public void drawImage(Graphics buffer) {
        if(!this.ifHasDestroyed()){
            buffer.drawImage( this.img,(int)x,(int)y, null);
        }

    }

}
