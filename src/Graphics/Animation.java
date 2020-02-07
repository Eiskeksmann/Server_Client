package Graphics;

import Numbers.Constants;

import Util.Vector2f;

import java.awt.*;

public class Animation {

    //Member Variable Space
    private Sprite spr;
    private Vector2f pos;
    private int cframe;
    private int nframes;

    private int count;
    private int delay;

    private int tplayed;

    private boolean isActive;

    //Constructor Space
    public Animation(Sprite spr, Vector2f pos){

        tplayed = 0;
        this.pos = pos;
        this.nframes = spr.getWidth();
        this.spr = spr;
        this.isActive = false;
        initFrames();
    }

    // Getter Setter Space
    public boolean getActive(){ return isActive; }
    public void setActive(boolean set){ isActive = set; }
    public int getDelay(){
        return delay;
    }
    public int getCFrame(){
        return cframe;
    }
    public int getCount(){
        return count;
    }
    public boolean hasPlayedOnce(){
        return tplayed > 0;
    }
    public boolean hasPlayed(int howoften){
        return tplayed == howoften;
    }

    //Method Space
    public void initFrames(){

        cframe = 0;
        count = 0;
        tplayed = 0;
        delay = -1;
    }
    public void setDelay(int set){
        delay = set;
    }

    //Game Loop - UIR
    public void update(Vector2f pos){

        this.pos = pos;
        if(delay == -1) return;
        count++;

        if(count == delay){
            cframe++;
            count = 0;
        }
        if(cframe == nframes){
            cframe = 0;
            tplayed++;
        }
    }
    public void render(Graphics2D g, float interpolation){

        Sprite.drawSprite(g, spr, cframe, 0, (int)pos.x, (int)pos.y, Constants.SPRITE_STANDARD, Constants.SPRITE_STANDARD);
        delay++;
    }
}
