package tankrotationexample.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class TransformBullet extends Bullet{
    TransformBullet(float x, float y, float vx, float vy, float angle, BufferedImage img, int ID) {
        super(x, y, vx, vy, angle, img, ID);
    }


}
