package Logic;

import Graphics.GameGUI;
import Numbers.Constants;
import States.GameStateManager;
import States.PlayState;
import Util.MouseHandler;
import Util.KeyHandler;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameLoop implements Runnable{

    private Thread t;
    private GameGUI gui;
    private Graphics2D g;
    private boolean running, pause;
    private GameStateManager gsm;
    private PlayState play;
    private BufferedImage img;
    private MouseHandler mouse;
    private KeyHandler key;

    private int fps = 0, frameCount = 0;

    public GameLoop(GameGUI gui, String type, Spieler sp1, Spieler sp2) throws IOException {

        this.gui = gui;
        this.gsm = new GameStateManager(gui);
        switch(type){

            case("V"):
                play = new PlayState(gsm, sp1, sp2);
                break;
            case("F"):
                play = new PlayState(gsm, sp1, sp2, 1,1);
                break;
        }
        init();
        initGraphics();
    }
    public void startGame() throws IOException {

        gsm.addGameState(play);
    }
    private void init(){

        running = true;
        pause = false;
        mouse = new MouseHandler(gui);
        key = new KeyHandler(gui);
        if(t == null){

            t = new Thread(this, "gamethread");
            t.start();
        }
    }
    private void initGraphics(){

        img = new BufferedImage(gui.getWidth(), gui.getHeight(), BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();
        g.scale(Constants.SCALE, Constants.SCALE);
    }
    //Runnable(1)
    @Override
    public void run() {

        //This value would probably be stored elsewhere.
        final double GAME_HERTZ = 144.0;
        //Calculate how many ns each frame should take for our target game hertz.
        final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
        //At the very most we will update the game this many times before a new render.
        //If you're worried about visual hitches more than perfect timing, set this to 1.
        final int MAX_UPDATES_BEFORE_RENDER = 5;
        //We will need the last update time.
        double lastUpdateTime = System.nanoTime();
        //Store the last time we rendered.
        double lastRenderTime;

        //If we are able to get as high as this FPS, don't render again.
        final double TARGET_FPS = 144;
        final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

        //Simple way of finding FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        while (running)
        {
            double now = System.nanoTime();
            int updateCount = 0;

            if (!pause)
            {
                //Do as many game updates as we need to, potentially playing catchup.
                while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
                {
                    update();
                    input(this.mouse, this.key);
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                    updateCount++;
                }

                //If for some reason an update takes forever, we don't want to do an insane number of catchups.
                //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
                if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
                {
                    lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                }

                //Render. To do so, we need to calculate interpolation for a smooth render.
                float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
                render(interpolation);
                draw(interpolation);
                lastRenderTime = now;
                frameCount++;

                //Update the frames we got.
                int thisSecond = (int) (lastUpdateTime / 1000000000);
                if (thisSecond > lastSecondTime)
                {
                    //System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
                    fps = frameCount;
                    frameCount = 0;
                    lastSecondTime = thisSecond;
                }

                //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
                {
                    Thread.yield();

                    //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
                    //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
                    //FYI on some OS's this can cause pretty bad stuttering.
                    try {Thread.sleep(1);} catch(Exception e) {}

                    now = System.nanoTime();
                }
            }
        }
    }

    private void update(){

        gsm.update();
    }
    private void input(MouseHandler mouse, KeyHandler key){

        gsm.input(mouse, key);
    }
    private void render(float interpolation){

        gsm.render(g, interpolation);
    }
    private void draw(float interpolation){

        if(!(gui.getGraphics() == null)) {
            Graphics2D g2 = (Graphics2D) gui.getGraphics();
            g2.drawImage(img, 0, 0, gui.getWidth(), gui.getHeight(), null);
            g2.dispose();
        }
    }
}
