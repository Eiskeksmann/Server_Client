package Logic;

import Util.Location;

public class Spieler{

    //Member Space
    private String name_;
    private String id_;
    private Location loc;

    private int last_x_ = 0;
    private int last_y_ = 0;
    private boolean ki_; //true für AI und false für Mensch
    private boolean turn_;
    private boolean synced;

    //Constructor
    public Spieler(String _name, boolean _ki){

        name_ = _name;
        ki_ = _ki;
        turn_ = false;
    }
    //Getter - Setter
    public String getId_(){return id_;}
    public String getName_(){
        return name_;
    }
    public boolean getKi_(){
        return ki_;
    }
    public int getLast_x_(){return last_x_;}
    public int getLast_y_(){ return last_y_;}
    public boolean getTurn_(){return turn_;}
    public int getX(){
        synced = false;
        return loc.getX();
    }
    public int getY(){
        synced = false;
        return loc.getY();
    }
    public boolean isSynced(){ return synced; }

    public void setId_(String _setter){

        id_ = _setter;
    }
    public void setName_(String _name){

        name_ = _name;
    }
    public void setKi_(boolean _ki){
        ki_ = _ki;
    }
    public void setTurn_(boolean _turn){ turn_ = _turn; }
    public void setLast_x_(int _last_x_){ last_x_ = _last_x_;}
    public void setLast_y_(int _last_y_){ last_y_ = _last_y_;}
    public void setSynced(boolean set){ synced = set; }

    //Methods
    public void resetPlayer(){

        turn_ = false;
        last_x_ = 0;
        last_y_ = 0;
    }
    public void insertTurnCoordinate(Location loc){

        this.loc = loc;
    }
    public Location getTurnCoordinate(){
        return loc;
    }
}

