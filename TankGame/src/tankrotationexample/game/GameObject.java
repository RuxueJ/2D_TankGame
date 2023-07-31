package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

 public class GameObject {
    protected float x;
    protected float y;
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
            case "2" -> new BreakableWall(x, y, ResourceManager.getSprite("break"));
            case "3","9" -> new UnbreakableWall(x, y, ResourceManager.getSprite("unbreak"));
            case "4" -> new Grass(x, y, ResourceManager.getSprite("grass"));
            case "5" -> new River(x, y, ResourceManager.getSprite("river"));
            case "6" -> new Shield(x, y, ResourceManager.getSprite("shield"));
            case "7" -> new Speed(x, y, ResourceManager.getSprite("speed"));
            case "8" -> new Health(x, y, ResourceManager.getSprite("health"));
            default -> throw new IllegalArgumentException("type" + type);
        };
    }

    public void drawImage(Graphics buffer) {

        buffer.drawImage( this.img,(int)x,(int)y, null);


    }

     public void collides(GameObject obj2) {
    }
}
