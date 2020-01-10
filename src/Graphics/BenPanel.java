package Graphics;

import javax.swing.*;
import java.awt.*;

public abstract class BenPanel extends JPanel{

    private int res_x;
    private int res_y;

    public int getRes_x(){ return res_x; }
    public int getRes_y(){ return res_y; }
    public void setRes_x(int res_x){ this.res_x = res_x; }
    public void setRes_y(int res_y){ this.res_y = res_y; }

    public abstract void init();

    public abstract void update();
    public abstract void input();
    public abstract void render(Graphics g);
}
