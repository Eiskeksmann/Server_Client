package Graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {

    private BufferedImage SPRITESHEET = null;
    private BufferedImage[][] spriteArray;
    private int w;
    private int h;
    private int wSprite;
    private int hSprite;

    public Sprite(String file, int w, int h){

        this.w = w;
        this.h = h;

        System.out.println("Loading" + file + "...");
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }

    public void setSize(int width, int height){

        setWidth(width);
        setHeight(height);
    }
    public void setWidth(int w){
        this.w = w;
        wSprite = SPRITESHEET.getWidth() / w;
    }
    public void setHeight(int h){
        this.h = h;
        hSprite = SPRITESHEET.getHeight() / h;
    }

    public int getWidth(){ return wSprite;}
    public int getHeigth(){return hSprite;}

    private BufferedImage loadSprite(String file){

        BufferedImage sprite = null;
        try{

            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));

        }
        catch(Exception e){
            System.out.println("ERROR: couldnt load file:" +  file);
        }
        return sprite;
    }
    public void loadSpriteArray(){

        spriteArray = new BufferedImage[wSprite][hSprite];

        for(int x =  0; x < wSprite; x++){

            for(int y = 0; y < hSprite; y++){
                spriteArray[x][y] = getSprite(x, y);
            }
        }

    }
    public BufferedImage getSpriteSheet(){
        return SPRITESHEET;
    }
    public BufferedImage getSprite(int x, int y){
        return SPRITESHEET.getSubimage(x * w, y* h, w, h);
    }

    public void drawImage(Graphics g, BufferedImage img, int xpo, int ypo, int w, int h){

        g.drawImage(img, xpo, ypo, w,  h, null);
    }
    public static void drawSprite(Graphics2D g, Sprite spr, int xsh, int ysh,
                                  int xpo, int ypo, int w, int h){

        g.drawImage(spr.getSprite(xsh, ysh), xpo, ypo, w, h, null);
    }
}
