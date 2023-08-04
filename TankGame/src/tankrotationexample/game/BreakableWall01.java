package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {
    public BreakableWall(float x, float y, BufferedImage breakable) {
        super( x,  y,  breakable);
    }

    @Override
    public void drawImage(Graphics buffer) {
        buffer.drawImage( this.img,(int)x,(int)y, null);
    }


}
