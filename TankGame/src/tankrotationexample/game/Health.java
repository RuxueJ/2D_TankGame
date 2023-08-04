package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class Health extends GameObject implements PowerUps{
    public Health(float x, float y, BufferedImage unbreak) {
        super( x,  y,  unbreak);
    }


    @Override
    public void applyPowerUp(Tank tank) {

    }
}
