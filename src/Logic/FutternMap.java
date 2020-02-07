package Logic;

import Numbers.Constants;
import Util.KeyHandler;
import Util.Location;
import Util.MouseHandler;

import Graphics.Sprite;
import Util.RenderInfo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FutternMap extends Spielfeld {

    //Member var space
    private int turn_count;
    private int[][] grid_;
    private ArrayList<String> turn_mem;
    private ArrayList<Location> changes;
    private RenderInfo<Location, BufferedImage> renderinfo;
    private int winning_col_;
    private Sprite spr;
    private boolean rendered;

    //Constructor
    public FutternMap(int _x, int _y, Sprite spr){

        super.x_ = _x;
        super.y_ = _y;
        this.turn_count = 0;
        this.grid_ = new int[_x][_y];
        this.turn_mem = new ArrayList<>();
        this.winning_col_ = 0;
        this.spr = spr;
        this.renderinfo = new RenderInfo<>();
        this.changes = new ArrayList<>();
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

    //Spielfeld(4)
    @Override
    public void initMap(){

        for(int i = 0; i < x_; i++){

            for(int j = 0; j < y_; j++){

                grid_[i][j] = EMPTY;
                changes.add(new Location(i, j));
            }
        }
        for(int i = 0; i < (x_ * y_); i++){

            turn_mem.set(i, INIT);
        }
        grid_[EMPTY][EMPTY] = ANCHOR;
        changes.add(new Location(EMPTY, EMPTY));
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

                case(ANCHOR):
                    renderinfo.add(l, spr.getSprite(4,0));
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
    }

    //Methods Chomp
    public void chompSetGrid(Spieler _sp, int _y, int _x){

        //System.out.println(_sp.getId_() + ": TRYING WITH X" + Integer.toString(_x));
        //System.out.println(_sp.getId_() + ": TRYING WITH Y" + Integer.toString(_y));
        if(_y - 1 < 0 || _x - 1 < 0  || _y - 1 >= y_ || _x - 1 >= x_ ){

            //WallsOfText.illegalCoordinateChosen(); //Außerhalb des Feldes
            return;
        }
        if(chompWinProof(_x - 1, _y - 1)){
            //WallsOfText.looserMessage(_sp.getName_());
            changes.add(new Location(_x - 1, _y - 1));
            quitMap();
            return;
        }
        if(grid_[_x - 1][_y - 1] != EMPTY ){

            //WallsOfText.illegalCoordinateChosen(); //bereits besetztes Feld
            return;
        }
        if(grid_[_x - 1][_y - 1] == EMPTY){

            addTurn(_sp, _x -1, _y - 1);
            changes.add(new Location(_x - 1, _y - 1));
            for(int i = _x - 1; i < x_; i++){

                for(int j = _y - 1; j < y_; j++){

                    if(grid_[i][j] != EMPTY){ break;}
                    if(grid_[i][j] == EMPTY){
                        _sp.setLast_x_(i);
                        _sp.setLast_y_(j);
                        grid_[i][j] = getIdAsInt(_sp.getId_());
                        changes.add(new Location(i, j));
                    }
                }
            }
            _sp.setTurn_(true);
        }
    }
    private int getIdAsInt(String _id){

        switch(_id){

            case("ID_SP1"):
                return ID_SP1;
            case("ID_SP2"):
                return ID_SP2;
            case("ID_BOT"):
                return ID_BOT;
        }
        return 0;
    }
    public boolean chompWinProof(int _x, int _y){

        if(_x == 0 && _y == 0){return true;}
        return false;
    }
    private void quitMap(){

        //System.exit(0);
    }
}
