package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources.ResourceManager;
import tankrotationexample.Resources.ResourcePool;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject{

    private float screen_x, screen_y;
    private float vx;
    private float vy;
    private float angle;
    List<Bullet> ammo = new ArrayList<>();
    long timeSinceLastShot = 0L;
    long cooldown = 500;

    private float R = 5;
    private float ROTATIONSPEED = 3.0f;

    static ResourcePool<Bullet> bPool;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
// static{
//     bPool = new ResourcePool<>("bullet",300);
//     bPool.fillPool(Bullet.class,300);
// }


    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        super(x,y,img);
        this.vx = vx; // may be deleted because of abstraction
        this.vy = vy; // may be deleted because of abstraction
        this.angle = angle; // may be deleted because of abstraction
    }

    void setX(float x){ this.x = x; }
    public float getX(){
     return x;
    }
    public float getY(){
        return y;
    }




    public float getScreen_x() {
        return screen_x;
    }

    public float getScreen_y() {
        return screen_y;
    }

    void setY(float y) { this. y = y;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }
    public void toggleShootPressed() {
     this.ShootPressed = true;
    }
    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleShootPressed() {
     this.ShootPressed = false;
    }
    void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.ShootPressed && ((this.timeSinceLastShot + this.cooldown) < System.currentTimeMillis())){
//            b = bPool.getResource();
            this.timeSinceLastShot = System.currentTimeMillis();

            this.ammo.add(new Bullet(x+25,y+25,angle, ResourceManager.getSprite("bullet")));

        }
        this.ammo.forEach(bullet -> bullet.update());
        this.hitBox.setLocation((int)x,(int)y);


    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
       checkBorder();
       centerScreen();
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        centerScreen();
    }

    private void centerScreen(){
     this.screen_x = this.x -GameConstants.GAME_SCREEN_WIDTH/4;
     this.screen_y = this.y - GameConstants.GAME_SCREEN_HEIGHT/2;
     this.checkScreen();
    }
    private void checkScreen(){
        if (screen_x < 0) {
            screen_x = 0;
        }
        if (screen_x >= GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2) {
            screen_x = GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2;
        }
        if (screen_y < 0) {
            screen_y = 0;
        }
        if (screen_y >= GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT) {
            screen_y = GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }
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
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
//        g2d.setColor(Color.RED);
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
       this.ammo.forEach(b->b.drawImage(g2d));

       g2d.setColor(Color.GREEN);
       g2d.drawRect((int)x,(int)y-20, 100,15);
       long currentWidth = 100 - ((this.timeSinceLastShot + this.cooldown) - System.currentTimeMillis());
       if(currentWidth > 100){
           currentWidth = 100;
       }
       g2d.fillRect((int)x,(int)y - 20, (int)currentWidth,15);

    }

    @Override
    public void collides(GameObject obj2) {
        if(obj2 instanceof Wall){
            // stop
        }else if(obj2 instanceof PowerUps){
//            obj2.applyPowerUp(this);

        }

    }


}
