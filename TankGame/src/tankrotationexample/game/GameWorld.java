package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private final Launcher lf;
    private long tick = 0;

    List<GameObject> gobjs = new ArrayList<>(500);

    /**
     *
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update(); // update tank
                 this.checkCollision();

                this.repaint();   // redraw game
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our 
                 * loop run at a fixed rate per/sec. 
                */
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    private void checkCollision() {

        for(int i = 0; i < this.gobjs.size(); i++){


            GameObject obj1 = this.gobjs.get(i);
            if(obj1 instanceof Bullet || obj1 instanceof Tank){
                for(int j = 0; j < this.gobjs.size(); j++){
                    if(i == j) continue;
                    GameObject obj2 = this.gobjs.get(j);
//                System.out.println(this.gobjs.size());
                    if(obj1.getHitBox().intersects(obj2.getHitBox())){
//                        System.out.println("hit");
                        obj1.collides(obj2);
                    }

                }
            }

        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
        this.t2.setX(800);
        this.t2.setY(300);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */

    /**
     * 0: nothing
     * 9: unbreakable but non-collidable
     * 3: unbreakable wall
     * 2: breakable wall
     * 4 grass
     * 5: river
     * 6: shield
     * 7: speed
     * 8: health*/
//    private void loadMap(String mapPath){
//        InputStreamReader isr = new InputStreamReader(ResourceManager.class.getClassLoader().getResourceAsStream(mapPath));
//        try(BufferedReader mapReader = new BufferedReader(isr)){
//
//            int row = 0;
//            String[] gameItems;
//            while(mapReader.ready()){
//                gameItems = mapReader.readLine().strip().split(",");
//                for(int col = 0; col < gameItems.length; col++){
//                    String gameObject = gameItems[col];
//                    if("0".equals(gameObject)) continue;
//                    this.gobjs.add(GameObject.newInstance(gameObject,col*30, row*30));
//                }
//                row++;
//
//            }
//        }catch (IOException e){
//            throw new RuntimeException(e);
//        }
//    }
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

//       this.loadMap("maps/map3.csv");
        InputStreamReader isr = new InputStreamReader(ResourceManager.class.getClassLoader().getResourceAsStream("maps/map4.csv"));
        try(BufferedReader mapReader = new BufferedReader(isr)){

            int row = 0;
            String[] gameItems;
//            int count = 0;
            while(mapReader.ready()){
                gameItems = mapReader.readLine().strip().split(",");
                for(int col = 0; col < gameItems.length; col++){
                    String gameObject = gameItems[col];
//                    count++;
//                    System.out.println(count + ": " + gameObject);
//                    System.out.println("Before " + row + "Col: " + col);
                    if("0".equals(gameObject)){
                       continue;}

                    this.gobjs.add(GameObject.newInstance(gameObject,col*30, row*30));
                }
                row++;

            }
//            System.out.println(this.gobjs.size());
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        t1 = new Tank(300, 300, 0, 0, (short) 0, ResourceManager.getSprite("tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);
        t2 = new Tank(800, 300, 0, 0, (short) 0, ResourceManager.getSprite("tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_CONTROL);
        this.lf.getJf().addKeyListener(tc2);
        this.gobjs.add(t1);
        this.gobjs.add(t2);
    }

    static double scaleFactor = 0.2;
    private void renderMiniMap(Graphics2D g){
        BufferedImage mm = this.world.getSubimage(0,0,GameConstants.GAME_WORLD_WIDTH,GameConstants.GAME_WORLD_HEIGHT);
        var mmX = GameConstants.GAME_SCREEN_WIDTH/2 - GameConstants.GAME_SCREEN_WIDTH*scaleFactor/2-45;
        var mmY = GameConstants.GAME_SCREEN_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT*scaleFactor-130;
        AffineTransform mmTransform = AffineTransform.getTranslateInstance(mmX,mmY);
        mmTransform.scale(scaleFactor,scaleFactor);
        g.drawImage(mm,mmTransform,null);
    }

    private void renderSplitScreen(Graphics2D g){
//        BufferedImage lh = world.getSubimage((int)obj.getScreen_x(),(int)obj.getScreen_y(),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage lh = world.getSubimage((int)t1.getScreen_x(),(int)t1.getScreen_y(),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rh = world.getSubimage((int)t2.getScreen_x(),(int)t2.getScreen_y(),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);

        g.drawImage(lh,0,0,null);
        g.drawImage(rh,GameConstants.GAME_SCREEN_WIDTH/2 +4,0,null);
    }
    @Override
    public void paintComponent(Graphics g) {  // Graphics refer to the JPanel
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.GAME_WORLD_WIDTH, GameConstants.GAME_WORLD_HEIGHT);
        this.gobjs.forEach(gameObject -> gameObject.drawImage(buffer));
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);


        g2.drawImage(world, 0, 0, null);
//        BufferedImage mm = world.getSubimage(0,0,GameConstants.GAME_WORLD_WIDTH,GameConstants.GAME_WORLD_HEIGHT);
//        g2. scale(.2,.2);
//        g2.drawImage(mm,
//                GameConstants.GAME_SCREEN_WIDTH*5/2 - GameConstants.GAME_WORLD_WIDTH/2,
//                GameConstants.GAME_SCREEN_HEIGHT*5-GameConstants.GAME_WORLD_HEIGHT-180,
//                null);

        renderSplitScreen(g2);
        renderMiniMap(g2);
    }
}
