package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall {

    public UnbreakableWall(float x, float y, BufferedImage unbreak) {
        super(x,y,unbreak);
    }

    @Override
    public void collide() {

    }

    @Override
    public void drawImage(Graphics buffer) {
        buffer.drawImage( this.img,(int)x,(int)y, null);
    }

}
