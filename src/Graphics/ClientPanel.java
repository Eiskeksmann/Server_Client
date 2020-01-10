package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClientPanel extends BenPanel implements ActionListener {

    //Member
    List clients;
    List inbox;
    TextArea servercom;
    TextField input;
    Button cmd;
    boolean sendCommand;
    BoxLayout lay;

    //Constructor Space
    public ClientPanel(int res_x, int res_y){

        super.setRes_x(res_x);
        super.setRes_y(res_y);
        sendCommand = false;
        init();
    }
    public String getCommand(){
        sendCommand = false;
        return input.getText();
    }
    public Boolean getSendCommand(){
        return sendCommand;
    }

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

        //List
        inbox = new List();
        clients = new List();

        //TextField
        input = new TextField(20);

        //Button
        cmd = new Button("SUBMIT COMMAND");
        cmd.addActionListener(this);

        //TextArea
        servercom = new TextArea();
        servercom.setEditable(false);
    }
    private void initLayout(){

        lay = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(lay);

        this.add("Inbox", inbox);
        this.add("Client List", clients);

        this.add(cmd);
        this.add(input);

        this.add(servercom);
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

    @Override
    public void actionPerformed(ActionEvent ae) {

        if(ae.getSource() == cmd){
            sendCommand = true;
        }
    }

    //Method Space
    public void setCommandText(String text){
        input.setText(text);
    }
    public void addServerCom(String add){

        String t = servercom.getText() + "\n";
        t += add;
        servercom.setText(t);
    }
    public void addInbox(String add){
        inbox.add(add);
    }
    public void addClient(String name){ clients.add(name); }
    public void removeClient(String name){ clients.remove(name); }
    public void addChat(String add){

        add += " ... send from me";
        inbox.add(add);
    }
}
