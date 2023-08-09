package tankrotationexample.menus;


import tankrotationexample.game.TankControl;
import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;
import tankrotationexample.game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable, KeyListener {

  private boolean GameRunning = true;

    private BufferedImage world;

    public void setGameRunning(boolean gameRunning) {
        GameRunning = gameRunning;
    }

    private Tank t1;
    private Tank t2;

    private int winner;


    private final Launcher lf;
    private long tick = 0;

    Sound bg = ResourceManager.getSound("bg");

    private List<GameObject> gobjs = new ArrayList<>(800);


    public List<GameObject> getGobjs() {
        return gobjs;
    }

    public List<Animation> getAnims() {
        return anims;
    }

    private List<Animation> anims = new ArrayList<>();
    /**
     *
     */
    public GameWorld(Launcher lf) {

        this.lf = lf;

    }

    @Override
    public void run() {

        this.resetGame();

//        this.playStartSoundAndWait();
//        System.out.println("play start sound and wait function ends");
//        this.playBgSound();
//        System.out.println("play bg sound function ends");
        ResourceManager.getSound("start").playSound();
        bg.setLooping();
        bg.playSound();
        try {


            while (this.t1.isIslive() && this.t2.isIslive()) {

                this.tick++;
                this.t1.update(this); // update tank
                this.t2.update(this); // update tank

                this.anims.forEach(animation -> animation.update());
                this.checkCollision();
                this.gobjs.removeIf(gameObject -> gameObject.ifHasDestroyed());

                this.repaint();   // redraw game
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our 
                 * loop run at a fixed rate per/sec. 
                */
                Thread.sleep(1000 / 144);
            }
            setWinnerMessage();


            this.lf.setFrame("end");
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }


    private void setWinnerMessage(){
        winner = this.t1.isIslive() ? 1 : 2;
        this.lf.setWinnerMessage(winner);
        this.setGameRunning(false);
    }

    private void checkCollision() {

        for(int i = 0; i < this.gobjs.size(); i++){

            GameObject obj1 = this.gobjs.get(i);
            if(obj1 instanceof Moveable){  // obj1 should be tank or bullet
                for(int j = 0; j < this.gobjs.size(); j++){
                    if(i == j) continue;
                    GameObject obj2 = this.gobjs.get(j);
                    if(obj1.getHitBox().intersects(obj2.getHitBox())){
                        ((Moveable)obj1).collide(obj2);

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
        this.gobjs.clear();
        InitializeGame();

    }
//    public void playStartSoundAndWait(){
//        Sound startSound = ResourceManager.getSound("start");
//        System.out.println("function playStartSoundAndWait");
//        CountDownLatch latch = new CountDownLatch(1);
//
//        Thread startSoundThread = new Thread(()->{
//            startSound.playSound();
//            startSound.waitUntilFinished(); // Wait until the "start" sound finishes playing
//            latch.countDown();
//        });
//        startSoundThread.start();
//        try {
//            latch.await(); // Wait until the latch is released (sound finishes)
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//    private void playBgSound(){
//        System.out.println("playing bg sound");
//        Sound bgSound = ResourceManager.getSound("bg");
//        bg.setLooping();
//        bg.playSound();
//    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */

    /**
     * 0: nothing
     * 1: breakable wall 1
     * 2: breakable wall 2
     * 3: river
     * 4 grass
     * 5: rocket bullet
     * 6: bloodsupply
     * 7: bulletsupply
     * 8: invisibility
     * 9: unbreakbale wall*/
    private void loadMap(String mapPath){
        InputStreamReader isr = new InputStreamReader(ResourceManager.class.getClassLoader().getResourceAsStream(mapPath));
        try(BufferedReader mapReader = new BufferedReader(isr)){
            int row = 0;
            String[] gameItems;
            while(mapReader.ready()){
                gameItems = mapReader.readLine().strip().split(",");
                for(int col = 0; col < gameItems.length; col++){
                    String gameObject = gameItems[col];
                    if("0".equals(gameObject)) continue;
                    this.gobjs.add(GameObject.newInstance(gameObject,col*30, row*30));
                }
                row++;

            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

       this.loadMap("maps/map4.csv");

////      this.anims.add(new Animation(300,300,ResourceManager.getAnimation("bullethit")));
//        this.anims.add(new Animation(350,300,ResourceManager.getAnimation("bulletshoot")));
//        this.anims.add(new Animation(400,300,ResourceManager.getAnimation("powerpick")));
//        this.anims.add(new Animation(450,300,ResourceManager.getAnimation("puffsmoke")));
//        this.anims.add(new Animation(500,300,ResourceManager.getAnimation("rocketflame")));
//        this.anims.add(new Animation(550,300,ResourceManager.getAnimation("rockethit")));
        t1 = new Tank(GameConstants.TANK1_ORIGINAL_POSITION_X,
                GameConstants.TANK1_ORIGINAL_POSITION_Y,
                0,
                0,
                (short) 0,
                ResourceManager.getSprite("tank1"));

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);
        t2 = new Tank(GameConstants.TANK2_ORIGINAL_POSITION_X,
                GameConstants.TANK2_ORIGINAL_POSITION_Y,
                0,
                0,
                (short) 0,
                ResourceManager.getSprite("tank2"));
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

        List<GameObject> copyOfGobjs = new ArrayList<>(this.gobjs);
        copyOfGobjs.forEach(gameObject -> gameObject.drawImage(buffer));


        this.anims.forEach(animation -> animation.drawImage(buffer));

        g2.drawImage(world, 0, 0, null);

        renderSplitScreen(g2);
        renderMiniMap(g2);
       }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        int keyCode = e.getKeyCode();
//        if(keyCode == KeyEvent.VK_F9){
//            if(GameRunning){
//
//               GameRunning = false;
//               notify();
//            }else{
//                this.GameRunning = true;
//                this.lf.setFrame("pause");
//            }
//        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
