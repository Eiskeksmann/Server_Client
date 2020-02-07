package States;

import Logic.Futtern;
import Logic.Spiel;
import Logic.Spieler;
import Logic.VierGewinnt;
import Util.KeyHandler;
import Util.MouseHandler;

import java.awt.*;
import java.io.IOException;

public class PlayState extends GameState{

    Spiel game;

    //Viergewinnt vs KI
    public PlayState(GameStateManager gsm, Spieler creator) throws IOException {

        super(gsm);
        game = new VierGewinnt(creator.getName_());
        game.durchGang();
    }
    //Viergewinnt 1 v 1
    public PlayState(GameStateManager gsm, Spieler creator, Spieler opponent) throws IOException {

        super(gsm);
        game = new VierGewinnt(creator.getName_(), opponent.getName_());
        game.durchGang();
    }
    //Futtern vs KI
    public PlayState(GameStateManager gsm, Spieler creator, int width, int height) throws IOException{

        super(gsm);
        game = new Futtern(creator.getName_(), width, height);
        game.durchGang();
    }
    //Futtern 1 v 1
    public PlayState(GameStateManager gsm, Spieler creator, Spieler opponent, int width, int height) throws IOException{

        super(gsm);
        game = new Futtern(creator.getName_(), opponent.getName_(), width, height);
        game.durchGang();
    }
    public void update(){

        game.update();
    }
    public void input(MouseHandler mouse, KeyHandler key){

        game.input(mouse, key);
    }
    public void render(Graphics2D g, float interpolation){

        game.render(g, interpolation);
    }
}
