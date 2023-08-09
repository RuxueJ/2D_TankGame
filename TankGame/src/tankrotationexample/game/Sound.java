package tankrotationexample.game;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.concurrent.CountDownLatch;

public class Sound {
    private Clip sound;

    private final CountDownLatch latch = new CountDownLatch(1);

    public Sound(Clip sound) {
        this.sound = sound;
    }

    public void playSound(){
        if(this.sound.isRunning()){
            this.sound.stop();
        }
        this.sound.setFramePosition(0);
        this.sound.start();

    }
    public void setVolume(float level){

        FloatControl volume = (FloatControl) this.sound.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(20.0f * (float)Math.log10(level));
    }

    public void setLooping(){
        this.sound.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        if(this.sound.isRunning()){
            this.sound.stop();
        }
    }


    public void waitUntilFinished() {
        try {
            latch.await(); // Wait until latch is released (sound finishes)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
