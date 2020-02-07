package Logic;

import Graphics.VierGewinntGUI;
import Graphics.Sprite;
import Numbers.Constants;
import Util.KeyHandler;
import Util.Location;
import Util.MouseHandler;
import Util.RenderInfo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class VierGewinntMap extends Spielfeld {

    //Member var space
    private int turn_count;
    private VierGewinntGUI gui;
    private int[][] grid_;
    private ArrayList<String> turn_mem;
    private ArrayList<Location> changes;
    private RenderInfo<Location, BufferedImage> renderinfo;
    private int winning_col_;
    private Sprite spr;
    private boolean rendered;

    //Constructor
    public VierGewinntMap(int _x, int _y, Sprite spr){

        this.x_ = _x;
        this.y_ = _y;
        this.turn_count = 0;
        this.grid_ = new int[_x][_y];
        this.turn_mem = new ArrayList<>();
        this.winning_col_ = 0;
        this.spr = spr;
        this.changes = new ArrayList<>();
        this.renderinfo = new RenderInfo<>();
    }

    //Getter - Setter
    public int getTurn_Count(){return turn_count;}
    public int[][] getGrid_(){return grid_;}
    public int getGridAt(int _x, int _y){return grid_[_x][_y];}
    public int getX_(){return x_;}
    public int getY_(){return y_;}
    public int getWinning_Col_(){return winning_col_;}

    //Interface Methods
    public void addTurn(Spieler _sp, int _x, int _y){

        if(turn_mem.get(turn_count).equals(INIT) || turn_mem.get(turn_count).equals(DELETE)) {
            turn_mem.set(turn_count,_sp.getId_() + "|" + Integer.toString(_x) + "|" + Integer.toString(_y));
            turn_count++;
        }
        else
        {
            //WallsOfText.exeption_ADD_TURN();
        }
        printTurn();

    }
    public void deleteTurn(){

        if(turn_count >= 1) {
            turn_mem.set(turn_count, DELETE);
            turn_count--;
        }
        else{

            //WallsOfText.exeption_DELETE_TURN();
        }
    }
    public void printTurn(){

        System.out.println("[" + turn_mem.size() + "] - " + turn_mem.get(turn_mem.size() - 1));
    }

    //Methods VierGewinnt
    public void setGrid(Spieler _sp, int _column){

        if(_column <= 0 || _column > y_){
            _sp.setTurn_(false);
            return;
        }
        if(grid_[0][_column -1] != EMPTY){
            _sp.setTurn_(false);
            return;
        }
        int set = 0;
        switch(_sp.getId_()){

            case("ID_SP1"):
                set = ID_SP1;
                break;

            case("ID_SP2"):
                set = ID_SP2;
                break;

            case("ID_BOT"):
                set = ID_BOT;
                break;
        }
        for(int i = x_ - 1; i >= 0; i--) {
            if(grid_[i][_column-1] == EMPTY){
                grid_[i][_column-1] = set;
                _sp.setLast_x_(i);
                _sp.setLast_y_(_column-1);
                addTurn(_sp, i, _column-1);
                changes.add(new Location(i, _column-1));
                if(winProof(set, i, _column-1)){
                    System.out.println("DER GEWINNER IST: " + _sp.getName_());
                    //System.exit(0);
                    return;
                }
                _sp.setTurn_(true);
                break;}
        }
    }
    public boolean pretendSetGrid(Spieler _sp, int _column){

        if(grid_[0][_column -1] != EMPTY){
            _sp.setTurn_(false);
            return false;
        }
        int set = 0;
        switch(_sp.getId_()){

            case("ID_SP1"):
                set = ID_SP1;
                break;

            case("ID_SP2"):
                set = ID_SP2;
                break;

            case("ID_BOT"):
                set = ID_BOT;
                break;
        }
        for(int i = x_ - 1; i >= 0; i--) {
            if(grid_[i][_column-1] == EMPTY){
                if(winProof(set, i, _column-1)){winning_col_ = _column; return true;}
                break;}
        }
        return false;
    }
    public boolean winProof(int _set, int _x, int _y){

        //Grenzen für Array Zugriffe: links oben 0 - 0 links unten 5 - 0 rechts unten 5 - 6 rechts oben 0 - 6
        //Horizontale Prüfung
        if(_y + 3 <= 6){
            //Checking right, right, right
            if(grid_[_x][_y+1] == _set && grid_[_x][_y+2] == _set && grid_[_x][_y+3] == _set){return true;}

        }
        if(_y + 2 <= 6 && _y - 1 >= 0){
            //Checking left, right, right
            if(grid_[_x][_y-1] == _set && grid_[_x][_y+1] == _set && grid_[_x][_y+2] == _set){return true;}
        }
        if(_y + 1 <= 6 && _y - 2 >= 0){
            //Checking left, left, right
            if(grid_[_x][_y-2] == _set && grid_[_x][_y-1] == _set && grid_[_x][_y+1] == _set){return true;}
        }
        if(_y - 3 >= 0){
            //Checking left, left, left
            if(grid_[_x][_y-3] == _set && grid_[_x][_y-2] == _set && grid_[_x][_y-1] == _set){return true;}
        }

        //Vertikale Prüfung
        if(_x + 3 <= 5){
            //Checking  down, down, down
            if(grid_[_x+1][_y] == _set && grid_[_x+2][_y] == _set && grid_[_x+3][_y] == _set){return true;}

        }
        if(_x + 2 <= 5 && _x - 1 >= 0){
            //Checking down, down, up
            if(grid_[_x-1][_y] == _set && grid_[_x+1][_y] == _set && grid_[_x+2][_y] == _set){return true;}
        }
        if(_x + 1 <= 5 && _x - 2 >= 0){
            //Checking down, up, up
            if(grid_[_x-2][_y] == _set && grid_[_x-1][_y] == _set && grid_[_x+1][_y] == _set){return true;}
        }
        if(_x - 3 >= 0){
            //Checking up, up, up
            if(grid_[_x-3][_y] == _set && grid_[_x-2][_y] == _set && grid_[_x-1][_y] == _set){return true;}
        }

        //Diagonale Prüfung von left up - right down
        if(_x - 3 >= 0 && _y - 3 >= 0){
            //Checking left up, left up, left up
            if(grid_[_x-1][_y-1] == _set && grid_[_x-2][_y-2] == _set && grid_[_x-3][_y-3] == _set){return true;}
        }
        if(_x - 2 >= 0 && _y - 2 >= 0 && _x + 1 <= 5 && _y + 1 <= 6){
            //Checking left up, left up, right down
            if(grid_[_x-1][_y-1] == _set && grid_[_x-2][_y-2] == _set && grid_[_x+1][_y+1] == _set){return true;}
        }
        if(_x - 1 >= 0 && _y - 1 >= 0 && _x + 2 <= 5 && _y + 2 <= 6){
            //Checking left up, right down, right down
            if(grid_[_x-1][_y-1] == _set && grid_[_x+1][_y+1] == _set && grid_[_x+2][_y+2] == _set){return true;}
        }
        if( _x + 3 <= 5 && _y + 3 <= 6){
            //Checking right down, right down, right down
            if(grid_[_x+1][_y+1] == _set && grid_[_x+2][_y+2] == _set && grid_[_x+3][_y+3] == _set){return true;}
        }

        //Diagonale Prüfung von left down - right up
        if(_x - 3 >= 0 && _y + 3 <= 6){
            //Checking right up, right up, right up
            if(grid_[_x-1][_y+1] == _set && grid_[_x-2][_y+2] == _set && grid_[_x-3][_y+3] == _set){return true;}
        }
        if(_x - 2 >= 0 && _y + 2 <= 6 && _x + 1 <= 5 && _y - 1 >= 0){
            //Checking right up, right up, left down
            if(grid_[_x-1][_y+1] == _set && grid_[_x-2][_y+2] == _set && grid_[_x+1][_y-1] == _set){return true;}
        }
        if(_x - 1 >= 0 && _y + 1 <= 6 && _x + 2 <= 5 && _y - 2 >= 0){
            //Checking right up, left down, left down
            if(grid_[_x-1][_y+1] == _set && grid_[_x+2][_y-2] == _set && grid_[_x+1][_y-1] == _set){return true;}
        }
        if(_x + 3 <= 5 && _y - 3 >= 0){
            //Checking right up, left down, left down
            if(grid_[_x+1][_y-1] == _set && grid_[_x+2][_y-2] == _set && grid_[_x+3][_y-3] == _set){return true;}
        }
        return false;

    }

    @Override
    public void initMap() {

        for(int i = 0; i < x_; i++){

            for(int j = 0; j < y_; j++){

                grid_[i][j] = EMPTY;
                changes.add(new Location(i, j));
            }
        }
    }

    @Override
    public void update() {

        for(Location l : changes){

            switch(grid_[l.getX()][l.getY()]){

                case(EMPTY):
                    renderinfo.add(l, spr.getSprite(0,0));
                    rendered = false;
                    break;

                case(ID_BOT):
                    renderinfo.add(l, spr.getSprite(1,0));
                    rendered = false;
                    break;

                case(ID_SP1):
                    renderinfo.add(l, spr.getSprite(2,0));
                    rendered = false;
                    break;

                case(ID_SP2):
                    renderinfo.add(l, spr.getSprite(3,0));
                    rendered = false;
                    break;
            }
        }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

    }

    @Override
    public void render(Graphics2D g, float interpolation) {

        if(!rendered) {
            for (int i = 0; i < renderinfo.getSize(); i++)
                spr.drawImage(g, renderinfo.getBAt(i), renderinfo.getLAt(i).getX() * Constants.SPRITE_STANDARD,
                        renderinfo.getLAt(i).getY() * Constants.SPRITE_STANDARD,
                        Constants.SPRITE_STANDARD, Constants.SPRITE_STANDARD);

            rendered = true;
            renderinfo.resetRenderInfo();
        }

        for(int i = 0; i < x_; i++){

            for(int j = 0; j < y_; j++){

                BufferedImage img;

                switch (grid_[i][j]){

                    case(EMPTY):
                        img = spr.getSprite(0,0);
                        break;
                    case(ID_SP1):
                        img = spr.getSprite(1,0);
                        break;
                    case(ID_SP2):
                        img = spr.getSprite(2,0);
                        break;
                    default:
                        img = spr.getSprite(3,0);
                        break;
                }
                spr.drawImage(g,img,i *Constants.SPRITE_STANDARD,j * Constants.SPRITE_STANDARD, Constants.SPRITE_STANDARD, Constants.SPRITE_STANDARD);
            }
        }
    }
}
