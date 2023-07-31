package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {
    public BreakableWall(float x, float y, BufferedImage breakable) {
        super( x,  y,  breakable);
    }

    @Override
    public void collides(GameObject obj2) {

    }
}
