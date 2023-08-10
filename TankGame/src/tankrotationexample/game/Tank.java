package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources.ResourceManager;
import tankrotationexample.GameWorld;

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
    private float original_x = this.x;
    private float original_y = this.y;
    private float vx;
    private float vy;
    private float angle;
    long timeSinceLastShot = 0L;
    long cooldown = 500;
    private boolean isVisible = true;
    private long invisibleEndTime;
    private boolean isTransformed = false;
    private int bulletNumber = GameConstants.TANK_FULL_BULLET;
    List<Bullet>ammo = new ArrayList<>();
    Random random = new Random();
    private int id = random.nextInt(10);
    private Animation bulletShoot;

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public void setInVisible() {
        isVisible = false;
        this.invisibleEndTime = System.currentTimeMillis() + 15000;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }


    public void setTransformed(boolean transformed) {
        isTransformed = transformed;
    }



    public int getBulletNumber() {
        return bulletNumber;
    }


    public void setLifeCount(int lifeCount) {
        this.lifeCount = lifeCount;
    }

    private int lifeCount = GameConstants.TANk_LIFE_COUNT;


    public int getId() {
        return id;
    }


    private float R = 3;
    private float ROTATIONSPEED = 3.0f;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    public int getBlood() {
        return blood;
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }
    private int blood = GameConstants.TANK_FULL_BLOOD;

    public void setBulletNumber(int bulletNumber) {
        this.bulletNumber = bulletNumber;
    }



    private boolean islive = true;
    public boolean isIslive() {
        return islive;
    }


    private Animation powerpick ;

    public Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        super(x,y,img);
        this.vx = vx; // may be deleted because of abstraction
        this.vy = vy; // may be deleted because of abstraction
        this.angle = angle; // may be deleted because of abstraction
    }

    public void setX(float x){ this.x = x; }
    public void setY(float y) { this. y = y;}




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
    public void update(GameWorld gw) {
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
        if((this.getBulletNumber() > 0) && this.ShootPressed && ((this.timeSinceLastShot + this.cooldown) < System.currentTimeMillis())){

            this.timeSinceLastShot = System.currentTimeMillis();
            Bullet b;
            if(!isTransformed){
                b = new NormalBullet(x+25,y+25,vx,vy, angle, ResourceManager.getSprite("bullet"),id);
            }else{
                b = new TransformBullet(x+25,y+25,vx,vy, angle, ResourceManager.getSprite("bigBullet"),id);
            }


            this.bulletNumber--;
            ammo.add(b);
            System.out.println("ammo list size: " + ammo.size());
            gw.getGobjs().add(b);
            bulletShoot = new Animation(x,y,ResourceManager.getAnimation("bulletshoot"));
            bulletShoot.update();
//            gw.getAnims().add();
            ResourceManager.getSound("bullet").playSound();
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
        checkVisible();
        Graphics2D g2d = (Graphics2D) g;
        if(this.isVisible){
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);

            g2d.drawImage(this.img, rotation, null);

            this.ammo.forEach(bullet -> bullet.drawImage(g2d));
            this.drawBlood(g2d);
            this.drawBullet(g2d);
            this.drawHeart(g2d);
            if(powerpick != null){
                this.powerpick.update();
                this.powerpick.drawImage(g2d);
            }

        }
        if(bulletShoot!=null){
            bulletShoot.drawImage(g2d);
        }




    }

    private void checkVisible() {
        if(System.currentTimeMillis() < invisibleEndTime){
            setVisible(false);
        }else{
            setVisible(true);
        }

    }

    private void drawHeart(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        int heartWidth = 8;
        int heartHeight = 8;

        for(int i = 0; i < this.lifeCount; i++){
            int heartX = (int) x + i * (heartWidth + 10); // Adjust the spacing between hearts
            int heartY = (int) y + 50;

            // Draw a heart shape (example)
            g2d.fillOval(heartX, heartY, heartWidth, heartHeight);
        }

    }

    private void drawBlood(Graphics2D g2d){
        g2d.setColor(Color.GREEN);
        g2d.drawRect((int)x,(int)y-30, 100,10);
        int currentBlood = this.blood;
        if(currentBlood > GameConstants.TANK_FULL_BLOOD){
            currentBlood = GameConstants.TANK_FULL_BLOOD;
        }
        g2d.fillRect((int)x,(int)y - 30, 100*(int)currentBlood/GameConstants.TANK_FULL_BLOOD,10);

        String bloodNumber = String.valueOf(currentBlood);
        g2d.setColor(Color.WHITE);
        int textX = (int) (x + 100 + 10); // Adjust the X position of the text
        int textY = (int) (y - 20); // Adjust the Y position of the text
        g2d.drawString(bloodNumber, textX, textY);
    }

    public void checkCondition(){
        if(this.blood <= 0){
            this.lifeCount--;
            ResourceManager.getSound("explosion").playSound();
            if(lifeCount <= 0){
                System.out.println("check condition function tank died");
                islive = false;
            }else{
                this.blood = GameConstants.TANK_FULL_BLOOD;
                this.x = this.original_x;
                this.y = this.original_y;
            }
        }
    }
    private void drawBullet(Graphics2D g2d){
        g2d.setColor(Color.BLUE);
        g2d.drawRect((int)x,(int)y-20, 100,10);
        int currentBullet  = this.bulletNumber;
        if(currentBullet > GameConstants.TANK_FULL_BULLET){
            currentBullet = GameConstants.TANK_FULL_BULLET;
        }
        if(currentBullet < 0){
            currentBullet = 0;
        }
        g2d.fillRect((int)x,(int)y - 20, 100*(int)currentBullet/GameConstants.TANK_FULL_BULLET,10);

        String bulletNumber = String.valueOf(currentBullet);
        g2d.setColor(Color.WHITE);
        int textX = (int) (x + 100 + 10); // Adjust the X position of the text
        int textY = (int) (y - 5); // Adjust the Y position of the text
        g2d.drawString(bulletNumber, textX, textY);
    }



    @Override
    public void collide(GameObject obj) {
            if (obj instanceof Wall || obj instanceof River) {
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
                powerpick = new Animation(x,y, ResourceManager.getAnimation("powerpick"));


            }
        }





    }

