package Util;

import Graphics.ClientGUI;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Cmd extends JTextField implements KeyListener {

    private ClientGUI client_gui;

    public Cmd(ClientGUI client_gui){

        this.client_gui = client_gui;
        this.addKeyListener(this);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        toggle(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {

        toggle(e.getKeyCode(), false);
    }

    private void toggle(int keycode, boolean pressed){

        if(keycode == KeyEvent.VK_ENTER){
            if(pressed){
                client_gui.setConfirmed(true);
            }else
                client_gui.setConfirmed(false);
        }
    }
}
