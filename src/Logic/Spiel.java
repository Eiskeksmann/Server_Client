package Logic;

import Graphics.Sprite;
import Util.KeyHandler;
import Util.MouseHandler;

import java.awt.*;

public abstract class Spiel {

    Spieler sp1_, sp2_;
    Sprite spr;
    Spielfeld sf_;

    public abstract void spielZug(Spieler _sp);
    public abstract void durchGang();

    public abstract void update();
    public abstract void input(MouseHandler mouse, KeyHandler key);
    public abstract void render(Graphics2D g, float interpolation);
}
