package Graphics;

import Util.Location;

import javax.swing.*;
import java.io.IOException;

public abstract class GameGUI extends JPanel {

    String gametype;

    public String getGametype(){ return gametype; }

    public abstract void setSp1Turn(Location loc);
    public abstract void setSp2Turn(Location loc);
    public abstract void startGame()throws IOException;
    public abstract String getHostId();
}
