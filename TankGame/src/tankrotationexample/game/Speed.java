package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class Speed extends PowerUps {
    public Speed(float x, float y, BufferedImage unbreak) {
        super(x,y,unbreak);
    }

    @Override
    public void collides(GameObject obj2) {

    }
}
