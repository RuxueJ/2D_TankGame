package tankrotationexample.game;

import java.awt.image.BufferedImage;

public class Wall extends GameObject implements Unreachable{
    public Wall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }


}
