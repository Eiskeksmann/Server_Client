package Logic;

import Numbers.Constants;
import Util.KeyHandler;
import Util.MouseHandler;
import Graphics.Sprite;
import java.awt.*;
import java.util.Random;

public class VierGewinnt extends Spiel{

    private VierGewinntMap map_;

    public VierGewinnt(String _sp1_name, String _sp2_name){

        sp1_ = new Spieler(_sp1_name, false);
        sp1_.setId_("ID_SP1");
        sp2_ = new Spieler(_sp2_name, false);
        sp2_.setId_("ID_SP2");
        spr = new Sprite("sprite/viergewinnt.png",
                Constants.SPRITE_STANDARD, Constants.SPRITE_STANDARD);
        map_ = new VierGewinntMap(6,7, spr);
        map_.initMap();
    }
    public VierGewinnt(String _sp1_name){

        sp1_ = new Spieler(_sp1_name, false);
        sp1_.setId_("ID_SP1");
        sp2_ = new Spieler("bot", true);
        sp2_.setId_("ID_BOT");
        spr = new Sprite("sprite/viergewinnt.png",
                Constants.SPRITE_STANDARD, Constants.SPRITE_STANDARD);
        map_ = new VierGewinntMap(6,7, spr);
        map_.initMap();
    }

    //Spiel(3)
    @Override
    public void update() {

        durchGang();
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

    public void spielZug(Spieler _sp){

        if(!_sp.getKi_() && _sp.isSynced()) {
            map_.setGrid(_sp, _sp.getX());
            durchGang();
        }
        else if (_sp.getKi_() && _sp.isSynced()){

            if(firstBehaviour(_sp)){

                return;
            }

            if(secondBehaviour(_sp)){

                return;
            }

            if(thirdBehaviour(_sp)){

                return;
            }
        }
    }
    public boolean firstBehaviour(Spieler _sp){

        boolean flag = false;
        for(int i = 1; i < map_.getY_() + 1; i++){

            if(map_.pretendSetGrid(sp2_, i) && !flag){
                map_.setGrid(_sp, map_.getWinning_Col_());
                return true;
            }
            if(map_.pretendSetGrid(sp1_, i) && flag){
                map_.setGrid(_sp, map_.getWinning_Col_());
                return true;
            }
            if(i == map_.getY_() && !flag){

                flag = true;
                i = 1;
            }
        }
        return false;
    }
    public boolean secondBehaviour(Spieler _sp){

        Random lcr = new Random();
        int k = lcr.nextInt(3);
        switch(k){
            case(0):
                //links von letztem Zug
                if(sp1_.getLast_y_() == 0){break;}
                map_.setGrid(_sp, sp1_.getLast_y_());
                return true;
            case(1):
                //über dem letzten Zug
                map_.setGrid(_sp, sp1_.getLast_y_() + 1);
                return true;
            case(2):
                //rechts vom letzten Zug
                if(sp1_.getLast_y_() == 6){break;}
                map_.setGrid(_sp, sp1_.getLast_y_() + 2);
                return true;
        }
        return false;
    }
    public boolean thirdBehaviour(Spieler _sp){

        Random complete = new Random();
        int j = complete.nextInt(7);
        j++;
        map_.setGrid(_sp, j);
        return true;
    }
    public void durchGang(){

        if(!sp1_.getTurn_()){/*WallsOfText.playerRotation(sp1_.getName_());*/ spielZug(sp1_);}
        else if(!sp2_.getTurn_() && sp1_.getTurn_()){/*WallsOfText.playerRotation(sp2_.getName_());*/ spielZug(sp2_);}
        else if(sp1_.getTurn_() && sp2_.getTurn_()){/*WallsOfText.rotationEnd(); sp1_.setTurn_(false);*/ sp2_.setTurn_(false); durchGang();}
    }
}

