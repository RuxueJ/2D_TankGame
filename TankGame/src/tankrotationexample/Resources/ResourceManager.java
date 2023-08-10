package tankrotationexample.Resources;

import tankrotationexample.game.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class ResourceManager {
    private final static Map<String, BufferedImage> sprites = new HashMap<>();
    private final static Map<String, List<BufferedImage>> animations = new HashMap<>();
    private final static Map<String, Sound> sounds = new HashMap<>();
    private final static Map<String,Integer> animationInfo = new HashMap<>(){{
        put("bullethit",24);
        put("bulletshoot",24);
        put("powerpick",32);
        put("puffsmoke",32);
        put("rocketflame",16);
        put("rockethit",32);

    }};



    private static BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(ResourceManager.class.getClassLoader().getResource(path));
    }

    private static void initSprites(){
        try {
            ResourceManager.sprites.put("tank1",loadSprite("tank/tank1.png"));
            ResourceManager.sprites.put("tank2",loadSprite("tank/tank2.png"));
            ResourceManager.sprites.put("bullet",loadSprite("bullet/bullet.jpg"));
            ResourceManager.sprites.put("bigBullet",loadSprite("bullet/bigBullet.png"));
            ResourceManager.sprites.put("menu",loadSprite("menu/title.png"));
            ResourceManager.sprites.put("unbreak",loadSprite("walls/greatWall.png"));
            ResourceManager.sprites.put("break1",loadSprite("walls/break2.jpg"));
            ResourceManager.sprites.put("break2",loadSprite("walls/break1.jpg"));
            ResourceManager.sprites.put("river",loadSprite("floor/river.png"));
            ResourceManager.sprites.put("grass",loadSprite("floor/grass.png"));
            ResourceManager.sprites.put("invisibility",loadSprite("powerups/invisibility.png"));
            ResourceManager.sprites.put("bulletsupply",loadSprite("powerups/bulletsupply.png"));
            ResourceManager.sprites.put("bloodsupply",loadSprite("powerups/bloodsupply.png"));
            ResourceManager.sprites.put("rocket",loadSprite("powerups/rocket.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
    }

    private static void initAnimations(){
        String baseName = "animations/%s/%s_%04d.png";
        animationInfo.forEach((animationName,frameCount)-> {
            List<BufferedImage> frames = new ArrayList<>();
            try {
                for(int i = 0 ; i < frameCount; i++){
               String spritePath = baseName.formatted(animationName,animationName,i);

                   frames.add(loadSprite(spritePath));
               }
                ResourceManager.animations.put(animationName,frames);

            } catch (IOException e) {
                System.out.println(e);
                   throw new RuntimeException(e);
            }

        });
    }


    private static Sound loadSound(String path) throws UnsupportedOperationException, IOException, LineUnavailableException, UnsupportedAudioFileException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResource(path)));
        Clip c = AudioSystem.getClip();
        c.open(ais);
        Sound s = new Sound(c);
        s.setVolume(.2f);
        return s;
    }

    private static void initSound(){
        try{
            ResourceManager.sounds.put("bullet",loadSound("sounds/bullet.wav"));
            ResourceManager.sounds.put("start",loadSound("sounds/stage_start.wav"));
            ResourceManager.sounds.put("bullet_shoot",loadSound("sounds/bullet_shoot.wav"));
            ResourceManager.sounds.put("explosion",loadSound("sounds/explosion.wav"));
            ResourceManager.sounds.put("bg",loadSound("sounds/Music.mid"));
            ResourceManager.sounds.put("pickup",loadSound("sounds/pickup.wav"));
            ResourceManager.sounds.put("shotfire",loadSound("sounds/shotexplosion.wav"));
        }
        catch (Exception e){
            System.out.println(e);
        }

    }



    public static BufferedImage getSprite(String type) {
        if(!ResourceManager.sprites.containsKey(type)){
            throw new RuntimeException("%s is missing from spirite resources".formatted(type));
        }

        return ResourceManager.sprites.get(type);
    }
    public static List<BufferedImage> getAnimation(String type){
        if(!ResourceManager.animations.containsKey(type)){
            throw new RuntimeException("%s is missing from animation resources".formatted(type));
        }
        return ResourceManager.animations.get(type);
    }
    public static Sound getSound(String type){
        if(!ResourceManager.sounds.containsKey(type)){
            throw new RuntimeException("%s is missing from animation resources".formatted(type));
        }
        return ResourceManager.sounds.get(type);
    }


    public static void loadResouces(){
        ResourceManager.initSprites();
        ResourceManager.initAnimations();
        ResourceManager.initSound();
    }

//    public static void main(String[] args) {
//
//        ResourceManager.loadResouces();
//        Sound bg = ResourceManager.getSound("bg");
//        bg.setLooping();
//        bg.playSound();
//        while(true){
//
//        }
//        }
}
