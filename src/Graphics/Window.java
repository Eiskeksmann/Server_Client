package Graphics;

import Graphics.BenPanel;
import Graphics.ClientPanel;
import Graphics.ServerPanel;
import Numbers.Constants;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Window extends JFrame {

    private BenPanel bpanel;
    Container container;

    public Window(String title){

        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        switch (title){

            case("SERVER"):
                bpanel = new ServerPanel(Constants.SERVER_RES_X,Constants.SERVER_RES_Y);
                setContentPane(bpanel);
                break;
            case("CLIENT"):
                bpanel = new ClientPanel(Constants.CLIENT_RES_X,Constants.CLIENT_RES_Y);
                setContentPane(bpanel);
                break;
        }
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
    public BenPanel getBpanel(){
        return bpanel;
    }
}