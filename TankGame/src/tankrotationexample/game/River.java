package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class River extends GameObject {
    public River(float x, float y, BufferedImage river) {
        super( x,  y,  river);
    }

    @Override
    public void collides(GameObject obj2) {

    }
}
