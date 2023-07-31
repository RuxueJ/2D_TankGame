package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class Grass extends GameObject {
    public Grass(float x, float y, BufferedImage grass) {
        super( x,  y,  grass);
    }

    @Override
    public void collides(GameObject obj2) {

    }
}
