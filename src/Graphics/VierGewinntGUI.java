package Graphics;

import Logic.GameLoop;
import Logic.Spieler;
import Numbers.Constants;
import Util.Location;

import java.awt.*;
import java.io.IOException;

public class VierGewinntGUI extends GameGUI{

    //Member Space
    private GameLoop clock;
    private Spieler sp1, sp2;

    //Constructor Space
    public VierGewinntGUI(Spieler sp1, Spieler sp2) throws IOException {

        this.sp1 = sp1;
        this.sp2 = sp2;
        super.setSize(new Dimension(Constants.GAME_RES_X, Constants.GAME_RES_Y));
        super.gametype = "V";
        sp1.setTurn_(true);
        clock = new GameLoop(this, "V", sp1, sp2);
    }
    public VierGewinntGUI(Spieler sp1) throws IOException {

        this.sp1 = sp1;
        this.sp2 = new Spieler("BOT", true);
        super.setSize(new Dimension(Constants.GAME_RES_X, Constants.GAME_RES_Y));
        super.gametype = "V";
        sp1.setTurn_(true);
        clock = new GameLoop(this, "V", sp1, sp2);
    }

    @Override
    public void startGame() throws IOException {

        clock.startGame();
    }

    @Override
    public String getHostId(){ return sp1.getName_(); }

    @Override
    public void setSp1Turn(Location loc){

        sp1.insertTurnCoordinate(loc);
        sp1.setSynced(true);
    }

    @Override
    public void setSp2Turn(Location loc){

        sp2.insertTurnCoordinate(loc);
        sp2.setSynced(true);
    }
}
