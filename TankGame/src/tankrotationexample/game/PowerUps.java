package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class PowerUps extends GameObject{
    public PowerUps(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void collides(GameObject obj2) {

    }
}
