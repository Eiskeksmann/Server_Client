package Graphics;

import javax.swing.*;
import javax.xml.soap.Text;
import java.awt.*;

public class ServerPanel extends BenPanel {

    //Member
    List log;
    List clients;
    TextField input;
    Button cmd;

    BoxLayout lay;

    //Constructor Space
    public ServerPanel(int res_x, int res_y){

        super.setRes_x(res_x);
        super.setRes_y(res_y);
        init();
    }

    //Getter Setter Space
    public List lgo(){ return log; }

    //Override Space
    @Override
    public void init() {

        setPreferredSize(new Dimension(super.getRes_x(), super.getRes_y()));
        setFocusable(true);
        requestFocus();

        //Specific
        initComponents();
        initLayout();
    }
    private void initComponents(){

        //Lists
        log = new List();
        clients = new List();

        //TextField
        input = new TextField(20);

        //Button
        cmd = new Button("SUBMIT COMMAND");

    }
    private void initLayout(){

        lay = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(lay);

        this.add("Server Log", log);
        this.add("Client List", clients);

        this.add(cmd);
        this.add(input);
    }

    @Override
    public void update() {

    }

    @Override
    public void input() {

    }

    @Override
    public void render(Graphics g) {

    }

    //Method Space
    public void addLog(String row){
        log.add(row);
    }
    public void addClient(String name){
        clients.add(name);
    }
    public void removeClient(String name){
        clients.remove(name);
    }
}
