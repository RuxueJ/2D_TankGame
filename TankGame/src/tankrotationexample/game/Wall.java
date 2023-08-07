package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class Wall extends GameObject implements Unreachable{
    public Wall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    abstract public void collides();
}
