package Util;

import Graphics.GameGUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyHandler implements KeyListener{

    public static List<Key> keys = new ArrayList<>();

    public KeyHandler(GameGUI gui){

        gui.addKeyListener(this);
    }
    public class Key{
        public int presses, absorbs;
        public boolean down, clicked;

        public Key(){
            keys.add(this);
        }

        public void toggle(boolean pressed){
            if(pressed != down){
                down = pressed;
            }
            if(pressed){
                presses++;
            }
        }

        public void tick(){
            if(absorbs < presses){
                absorbs++;
                clicked = true;
            }
            else{
                clicked = false;
            }
        }
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key attack = new Key();
    public Key menu = new Key();
    public Key enter = new Key();
    public Key escape = new Key();

    public void releaseAll(){
        for(Key k : keys){
            k.down = false;
        }
    }

    public void tick(){

        for(Key k : keys){
            k.tick();
        }
    }

    public void toggle(KeyEvent e, boolean pressed){

        switch(e.getKeyCode()){

            case(KeyEvent.VK_W):
                up.toggle(pressed);
                break;

            case(KeyEvent.VK_A):
                left.toggle(pressed);
                break;

            case(KeyEvent.VK_S):
                down.toggle(pressed);
                break;

            case(KeyEvent.VK_D):
                right.toggle(pressed);
                break;

            case(KeyEvent.VK_ENTER):
                enter.toggle(pressed);
                break;

            case(KeyEvent.VK_X):
                attack.toggle(pressed);
                break;

            case(KeyEvent.VK_ESCAPE):
                escape.toggle(pressed);
                break;

            case(KeyEvent.VK_P):
                menu.toggle(pressed);
                break;
        }
    }
    public void keyTyped(KeyEvent e){

    }
    public void keyPressed(KeyEvent e){

        toggle(e, true);
    }
    public void keyReleased(KeyEvent e){

        toggle(e, false);
    }
}
