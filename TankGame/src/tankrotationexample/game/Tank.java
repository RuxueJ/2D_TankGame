package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources.ResourceManager;
import tankrotationexample.Resources.ResourcePool;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject implements Moveable{

    private float screen_x, screen_y;
    private float vx;
    private float vy;
    private float angle;

    long timeSinceLastShot = 0L;
    long cooldown = 500;
    GameWorld gw;
List<Bullet>ammo = new ArrayList<>();
    Random random = new Random();

    private int id = random.nextInt(10);

    public int getId() {
        return id;
    }


    private float R = 5;
    private float ROTATIONSPEED = 3.0f;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    public int getBlood() {
        return blood;
    }
    public void setBlood(int blood) {
        this.blood = blood;
    }
    private int blood = GameConstants.TANK_FULL_LIFE;
    private boolean islive = true;



    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img, GameWorld gw) {
        super(x,y,img);
        this.vx = vx; // may be deleted because of abstraction
        this.vy = vy; // may be deleted because of abstraction
        this.angle = angle; // may be deleted because of abstraction
        this.gw = gw;
    }

    void setX(float x){ this.x = x; }
    void setY(float y) { this. y = y;}




    public float getScreen_x() {
        return screen_x;
    }

    public float getScreen_y() {
        return screen_y;
    }



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
    void update(GameWorld gw) {
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

            this.timeSinceLastShot = System.currentTimeMillis();

            Bullet b = new Bullet(x+25,y+25,vx,vy, angle, ResourceManager.getSprite("bullet"),id, this.gw);
            ammo.add(b);
            gw.gobjs.add(b);
            gw.anims.add(new Animation(x,y,ResourceManager.getAnimation("bulletshoot")));
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
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
        this.ammo.forEach(bullet -> bullet.drawImage(g2d));

       g2d.setColor(Color.GREEN);
       g2d.drawRect((int)x,(int)y-20, 100,15);
       long currentWidth = this.blood;
       if(currentWidth > 100){
           currentWidth = 100;
       }
       g2d.fillRect((int)x,(int)y - 20, (int)currentWidth,15);

    }


    @Override
    public void collide(GameObject obj) {
        System.out.println("hit");
        if (obj instanceof Wall) {
            if (UpPressed) {
                this.x -= vx;
                this.y -= vy;
            }
            if (DownPressed) {
                this.x += vx;
                this.y += vy;
            }
        }
        if(obj instanceof PowerUps){
            ((PowerUps) obj).applyPowerUp(this);
        }

    }



//        if(obj instanceof PowerUps){
//            ((PowerUps) obj).applyPowerUp(this);
//        }
//        if(obj instanceof Wall){
//            System.out.println("tank collides Wall");
//
//        }

    }

