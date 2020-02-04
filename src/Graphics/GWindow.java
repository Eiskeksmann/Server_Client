package Graphics;

import Numbers.Constants;
import javax.swing.*;
import java.awt.*;

public class GWindow <G extends BenPanel> extends JFrame {

    private String id;
    private G gui;
    public GWindow(G gui){

        this.gui = gui;
        switch (this.gui.getClass().toString()){

            case("class Graphics.ServerGUI"):
                this.id = "SERVER";
                init(Constants.SERVER_RES_X, Constants.SERVER_RES_Y);
                break;

            case("class Graphics.ClientGUI"):
                id = "CLIENT";
                init(Constants.CLIENT_RES_X, Constants.CLIENT_RES_Y);
                break;

            case("class Graphics.GameGUI"):
                id = "GAME";
                init(Constants.GAME_RES_X, Constants.GAME_RES_Y);
                break;
        }
    }

    public String getId(){ return id; }
    private void init(int res_x, int res_y){

        setTitle(id);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(this.gui);
        setPreferredSize(new Dimension(res_x, res_y));
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
    public G getGUI(){ return this.gui;}
}
