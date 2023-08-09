package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

 public abstract class GameObject {

     protected float x;
    protected float y;
    public float getX() {
         return x;
     }

     public float getY() {
         return y;
     }


     private boolean hasDestroyed = false;

     public boolean ifHasDestroyed() {
         return hasDestroyed;
     }
     public void setHasDestroyed(boolean hasCollide) {
         this.hasDestroyed = hasCollide;
     }

     protected BufferedImage img;
    protected Rectangle hitBox;
    public GameObject(float x, float y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitBox = new Rectangle((int)x,(int) y, this.img.getWidth(),this.img.getHeight());
    }
    public Rectangle getHitBox(){
        return this.hitBox.getBounds();
    }
    public static GameObject newInstance(String type, float x, float y)throws UnsupportedOperationException{

        return switch (type){
            case "1" -> new BreakableWall01(x, y, ResourceManager.getSprite("break1"));
            case "2" -> new BreakableWall02(x, y, ResourceManager.getSprite("break2"));
            case "3"-> new River(x, y, ResourceManager.getSprite("river"));
            case "4" -> new Grass(x, y, ResourceManager.getSprite("grass"));
            case "5" -> new Tranform(x, y, ResourceManager.getSprite("rocket"));
            case "6" -> new BloodSupply(x, y, ResourceManager.getSprite("bloodsupply"));
            case "7" -> new BulletSupply(x, y, ResourceManager.getSprite("bulletsupply"));
            case "8" -> new Invisibility(x, y, ResourceManager.getSprite("invisibility"));
            case "9" -> new UnbreakableWall(x, y, ResourceManager.getSprite("unbreak"));
            default -> throw new IllegalArgumentException("type" + type);
        };
    }


    abstract public void drawImage(Graphics buffer);


}
