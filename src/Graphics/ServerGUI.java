package Graphics;

import javax.swing.*;
import java.awt.*;

public class ServerGUI extends BenPanel{

    //Member Space
    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private JPanel pan_west;
    private JPanel pan_north;
    private JPanel pan_center, pan_center_left, pan_center_right, pan_center_bottom;

    private JButton cmd_send;
    private List lst_clients;
    private TextArea jta_serverlog;
    private JTextField txt_commandline;

    private Label lbl_server_north;
    private Label lbl_clients;

    public ServerGUI(){

        init();
    }
    private void initComponents(){

        pan_west = new JPanel();
        pan_north = new JPanel();
        pan_center = new JPanel();
        pan_center_left = new JPanel();
        pan_center_right = new JPanel();
        pan_center_bottom = new JPanel();

        cmd_send = new JButton("CONFIRM");
        lst_clients = new List();
        jta_serverlog = new TextArea();
        txt_commandline = new JTextField();

        lbl_server_north = new Label();
        lbl_clients = new Label("Clients");
    }
    private void initStyle(){

        setDefaultPanelStyle(pan_west);
        setDefaultPanelStyle(pan_north);
        setDefaultPanelStyle(pan_center);
        setDefaultMasterPanelStyle(pan_center_left, "SERVERLOG");
        setDefaultMasterPanelStyle(pan_center_right, "FUNCTIONS");
        setDefaultMasterPanelStyle(pan_center_bottom, "COMMAND LINE");

        setDefaultButtonStyle(cmd_send);
        setDefaultListStyle(lst_clients);
        setDefaultTextAreaStyle(jta_serverlog);
        setDefaultTextFieldStyle(txt_commandline);

        setDefaultLabelStyle(lbl_server_north);
        setDefaultLabelStyle(lbl_clients);
    }

    private void initLayout(){

        this.setLayout(new BorderLayout());
        //Main Layer(ML) - BorderLayout
        //(ML) West
        this.add(pan_west, BorderLayout.WEST);
        initWest();

        //(ML) Center
        this.add(pan_center, BorderLayout.CENTER);
        initCenter();
    }
    private void initWest(){

        //West Panel
        pan_west.setLayout(gbl);
        pan_west.setBackground(super.getDefaultColor());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weighty = 0.1;

        //add Client Label
        pan_west.add(lbl_clients, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(4,4,4,4);

        //add Client List Box
        pan_west.add(lst_clients, gbc);

        //reset gbc Settings
        resetGridBagConstraints(gbc);
    }
    private void initCenter(){

        //Center Panel
        pan_center.setLayout(gbl);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        pan_center.add(pan_center_left, gbc);
        pan_center_left.setLayout(gbl);
        pan_center_left.add(jta_serverlog, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.2;
        pan_center_left.add(pan_center_bottom, gbc);

        pan_center_bottom.setLayout(gbl);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        pan_center_bottom.add(txt_commandline, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        pan_center_bottom.add(cmd_send, gbc);

        resetGridBagConstraints(gbc);
    }

    //GUI Interactions
    public void addClient(String name){

        lst_clients.add(name);
    }
    public void removeClient(String name){

        lst_clients.remove(name);
    }
    public void addLog(String add){

        String was = jta_serverlog.getText();
        jta_serverlog.setText(was + ("\n" + add));
    }

    @Override
    public void init() {

        setFocusable(true);
        requestFocus();

        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        resetGridBagConstraints(gbc);

        initComponents();
        initStyle();
        initLayout();
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
}
