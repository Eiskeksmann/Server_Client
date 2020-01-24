package Graphics;

import Numbers.Constants;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private BenPanel bpanel;

    public Window(String title){

        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        switch (title){

            case("SERVER"):
                //bpanel = new ServerPanel();
                setContentPane(bpanel);
                break;
            case("CLIENT"):
                //bpanel = new ClientPanel();
                setContentPane(bpanel);
                break;
            case("TEST"):
                bpanel = new ServerGUI();
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
