package Logic;

import Numbers.Constants;
import Util.KeyHandler;
import Util.Location;
import Util.MouseHandler;
import Graphics.Sprite;

import java.awt.*;
import java.util.Random;

public class Futtern extends Spiel{

    FutternMap map_;
    int width_;
    int height_;

    //constructor
    public Futtern(String _sp1_name, String _sp2_name, int _x, int _y){

        sp1_ = new Spieler(_sp1_name, false);
        sp1_.setId_("ID_SP1");
        sp2_ = new Spieler(_sp2_name, false);
        sp2_.setId_("ID_SP2");
        width_ = _x;
        height_ = _y;
        spr = new Sprite("sprite/futtern.png",
                Constants.SPRITE_STANDARD, Constants.SPRITE_STANDARD);
        map_ = new FutternMap(width_, height_, spr);
        map_.initMap();
    }
    public Futtern(String _sp1_name, int _x, int _y){

        sp1_ = new Spieler(_sp1_name, false);
        sp1_.setId_("ID_SP1");
        sp2_ = new Spieler("bot", true);
        sp2_.setId_("ID_BOT");
        width_ = _x;
        height_ = _y;
        spr = new Sprite("sprite/futtern.png",
                Constants.SPRITE_STANDARD, Constants.SPRITE_STANDARD);
        map_ = new FutternMap(width_, height_, spr);
        map_.initMap();
    }
    public void spielZug(Spieler _sp){

        if(!_sp.getKi_() && _sp.isSynced()) {
            map_.chompSetGrid(_sp, _sp.getX(), _sp.getY());
            durchGang();
        }
        else if(_sp.getKi_() && _sp.isSynced()){

            if(firstBehaviour(_sp)) {

                durchGang();
                return;
            }
            if(secondBehaviour(_sp)){

                durchGang();
                return;
            }
            if(thirdBehaviour(_sp)){

                durchGang();
                return;
            }
        }
    }
    public void durchGang(){

        if(!sp1_.getTurn_()){/*WallsOfText.playerRotation(sp1_.getName_());*/ spielZug(sp1_);}
        else if(!sp2_.getTurn_() && sp1_.getTurn_()){/*WallsOfText.playerRotation(sp2_.getName_());*/ spielZug(sp2_);}
        else if(sp1_.getTurn_() && sp2_.getTurn_()){/*WallsOfText.rotationEnd();*/ sp1_.setTurn_(false); sp2_.setTurn_(false); durchGang();}
    }

    //Spiel (3)
    @Override
    public void update() {

        map_.update();
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

        map_.input(mouse, key);
    }

    @Override
    public void render(Graphics2D g, float interpolation) {

        map_.render(g, interpolation);
    }

    public boolean firstBehaviour(Spieler _sp){


        return false;
    }
    public boolean secondBehaviour(Spieler _sp){


        return false;
    }
    public boolean thirdBehaviour(Spieler _sp){

        Random var = new Random();
        int x = var.nextInt(map_.getX_());
        int y = var.nextInt(map_.getY_());

        if(x == 0 && y == 0){

            return false;
        }
        else{

            map_.chompSetGrid(_sp, x, y);
            return true;
        }
    }
}
