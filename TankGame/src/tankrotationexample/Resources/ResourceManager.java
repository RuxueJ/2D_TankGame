package tankrotationexample.Resources;

import tankrotationexample.game.Bullet;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResourceManager {
    private final static Map<String, BufferedImage> sprites = new HashMap<>();
    private final static Map<String, List<BufferedImage>> animation = new HashMap<>();
    private final static Map<String, Clip> sounds = new HashMap<>();

    private static BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(ResourceManager.class.getClassLoader().getResource(path));
    }
    private static void initSprites(){
        try {
            ResourceManager.sprites.put("tank1",loadSprite("tank/tank1.png"));
            ResourceManager.sprites.put("tank2",loadSprite("tank/tank2.png"));
            ResourceManager.sprites.put("bullet",loadSprite("bullet/bullet.jpg"));
            ResourceManager.sprites.put("menu",loadSprite("menu/title.png"));
            ResourceManager.sprites.put("unbreak",loadSprite("walls/unbreak.jpg"));
            ResourceManager.sprites.put("break",loadSprite("walls/break2.jpg"));
            ResourceManager.sprites.put("river",loadSprite("floor/river.png"));
            ResourceManager.sprites.put("grass",loadSprite("floor/grass.png"));
            ResourceManager.sprites.put("health",loadSprite("powerups/health.png"));
            ResourceManager.sprites.put("shield",loadSprite("powerups/shield.png"));
            ResourceManager.sprites.put("speed",loadSprite("powerups/speed.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
    }
    public static void loadResouces(){
        ResourceManager.initSprites();
    }


    public static BufferedImage getSprite(String type) {
        if(!ResourceManager.sprites.containsKey(type)){
            throw new RuntimeException("%s is missing from spirite resources".formatted(type));
        }

        return ResourceManager.sprites.get(type);
    }

    public static void main(String[] args) {
        ResourcePool<Bullet> bPool = new ResourcePool<>("bullet",300);
        bPool.fillPool(Bullet.class,300);
        ResourceManager.loadResouces();
    }
}
