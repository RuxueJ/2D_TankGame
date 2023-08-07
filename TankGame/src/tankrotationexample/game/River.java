package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class River extends GameObject implements Unreachable{
    public River(float x, float y, BufferedImage river) {
        super( x,  y,  river);
    }

    @Override
    public void drawImage(Graphics buffer) {
        buffer.drawImage(img,(int)x, (int)y, null);
    }


}
