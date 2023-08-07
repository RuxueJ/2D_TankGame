package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources.ResourceManager;
import tankrotationexample.Resources.ResourcePool;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Bullet extends GameObject implements Moveable{

    private float vx;
    private float vy;
    float angle;
    private float R = 5;
    private int id;

    private Animation bullethit;




    Bullet(float x, float y, float vx, float vy, float angle, BufferedImage img,int ID) {
        super(x,y,img);
        this.vx = vx; // may be deleted because of abstraction
        this.vy = vy; // may be deleted because of abstraction
        this.angle = angle; // may be deleted because of abstraction
        this.id = ID;

    }



    void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        this.hitBox.setLocation((int)x,(int)y);
        if(bullethit!=null){
            bullethit.update();
        }

    }


    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        if(!ifHasDestroyed()){
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(this.angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);

            g2d.drawImage(this.img, rotation, null);

        }
        if(bullethit!=null){
            bullethit.drawImage(g2d);
        }
    }; // graphic object that you mean in the memory


    @Override
    public void collide(GameObject obj) {

        if (obj instanceof Tank && (((Tank) obj).getId() != this.id)) {
            ((Tank) obj).setBlood(((Tank) obj).getBlood() - 10);
            ((Tank) obj).checkCondition();


            this.setHasDestroyed(true);
        }
        if (obj instanceof Wall) {

            ((Wall)obj).collides();
            this.setHasDestroyed(true);
        }
        bullethit = new Animation(x,y, ResourceManager.getAnimation("bullethit"));


    }

}