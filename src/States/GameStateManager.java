package States;

import Util.KeyHandler;
import Util.MouseHandler;
import Util.Vector2f;
import Graphics.GameGUI;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameStateManager {

    //Member Space
    private ArrayList<GameState> states;

    public static Vector2f map;

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GOVER = 3;

    //Constructor Space
    public GameStateManager(GameGUI gui) throws IOException {

        map = new Vector2f(gui.getWidth(), gui.getHeight());
        Vector2f.setWorldVar(map.x, map.y);

        states  = new ArrayList<>();;
    }

    //Method Space
    public void addGameState(GameState state){

        states.add(state);
    }
    public void popGameState(GameState state){

        states.remove(state);
    }

    public void update(){

        Vector2f.setWorldVar(map.x, map.y);
        for (GameState s : states) {
            s.update();
        }
    }
    public void input(MouseHandler mouse, KeyHandler key){

        for (GameState s : states) {
            s.input(mouse, key);
        }
    }
    public void render(Graphics2D g, float interpolation){

        for (GameState s : states) {
            s.render(g, interpolation);
        }
    }
}
