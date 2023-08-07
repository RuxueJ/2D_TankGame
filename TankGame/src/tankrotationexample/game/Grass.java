package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Grass extends GameObject {
    public Grass(float x, float y, BufferedImage grass) {
        super( x,  y,  grass);
    }

    @Override
    public void drawImage(Graphics buffer) {
        buffer.drawImage( this.img,(int)x,(int)y, null);
    }


}
