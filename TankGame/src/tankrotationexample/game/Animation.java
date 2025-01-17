package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Animation {
    float x, y;
    private List<BufferedImage> frames;
    private long timeSinceUpdate = 0;
    private long delay = 40;
    private int currentFrame = 0;
    private boolean isRunning = false;
    public static List<BufferedImage> getAnimation(String type){

        return ResourceManager.getAnimation(type);
    }

    public Animation(float x, float y, List<BufferedImage> frames) {
        this.x = x;
        this.y = y;
        this.frames = frames;
        isRunning = true;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public List<BufferedImage> getFrames() {
        return frames;
    }

    public void update(){
        if(timeSinceUpdate + delay < System.currentTimeMillis()){
            this.timeSinceUpdate = System.currentTimeMillis();
            this.currentFrame++;
            if(this.currentFrame == frames.size()){
                isRunning = false;
            }
        }

    }
    public void drawImage(Graphics2D g){
        if(isRunning){
            g.drawImage(this.frames.get(currentFrame), (int)x,(int)y, null);
        }
    }
}
