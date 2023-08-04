package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources.ResourcePool;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements Moveable{

    private float vx;
    private float vy;
    private float angle;
    private float R = 5;
    private int id;
    GameWorld gw;

    boolean isCollide = false;




    Bullet(float x, float y, float vx, float vy, float angle, BufferedImage img,int ID, GameWorld gw) {
        super(x,y,img);
        this.vx = vx; // may be deleted because of abstraction
        this.vy = vy; // may be deleted because of abstraction
        this.angle = angle; // may be deleted because of abstraction
        this.id = ID;
        this.gw = gw;
    }



    void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation((int)x,(int)y);

    }


    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - 80) {
            x = GameConstants.GAME_WORLD_WIDTH - 80;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 80) {
            y = GameConstants.GAME_WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    @Override
    public void drawImage(Graphics g) {  // graphic object that you mean in the memory
        if(!isCollide){
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);
            g2d.setColor(Color.RED);
            //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
            g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());

        }

    }



    @Override
    public void collide(GameObject obj) {



        if (obj instanceof Tank && (((Tank) obj).getId() != this.id)) {
            ((Tank) obj).setBlood(((Tank) obj).getBlood() - 20);
            System.out.println("bullet hit tank!");
            isCollide = true;
        }
        if (obj instanceof Wall) {
            isCollide = true;

        }

        this.gw.gobjs.remove(this);


    }

}