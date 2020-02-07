package Graphics;

import Util.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BFont {

    private BufferedImage FONTSHEET = null;
    private BufferedImage[][] fontArray;
    private final int TILE_SIZE = 32;
    public int w;
    public int h;
    private final int wLetter = 8;
    private final int hLetter = 8;

    public BFont(String file){

        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("Loading" + file + "...");
        FONTSHEET = loadFont(file);

        loadFontArray();
    }

    public BFont(String file, int w, int h){

        this.w = w;
        this.h = h;

        System.out.println("Loading" + file + "...");
        FONTSHEET = loadFont(file);

        loadFontArray();
    }

    public void setSize(int width, int height){

        setWidth(width);
        setHeight(height);
    }
    public void setWidth(int w){
        this.w = w;
    }
    public void setHeight(int h){
        this.h = h;
    }

    public int getWidth(){ return w;}
    public int getHeigth(){ return h;}

    private BufferedImage loadFont(String file){

        BufferedImage font = null;
        try{

            font = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        }
        catch(Exception e){
            System.out.println("ERROR: couldnt load file:" +  file);
        }
        return font;
    }
    public void loadFontArray(){

        fontArray = new BufferedImage[wLetter][hLetter];

        for(int x =  0; x < wLetter; x++){

            for(int y = 0; y < hLetter; y++){
                fontArray[x][y] = getLetter(x, y);
            }
        }

    }
    public BufferedImage getFontSheet(){
        return FONTSHEET;
    }
    public BufferedImage getLetter(int x, int y){
        return FONTSHEET.getSubimage(x * w, y* h, w, h);
    }

    public BufferedImage getFont(char letter){

        int value = letter;

        int x = value % wLetter;
        int y = value / hLetter;
        return getLetter(x, y);
    }

    public static void drawArray(Graphics2D g, BFont f, String word, Vector2f pos, int width, int height, int xoffset, int yoffset){
        float x = pos.x;
        float y = pos.y;

        for(int i  = 0; i < word.length(); i++){
            g.drawImage(f.getFont(word.charAt(i)), (int) x, (int) y, width, height, null);
            x+= xoffset;
            y+= yoffset;
        }
    }
}
