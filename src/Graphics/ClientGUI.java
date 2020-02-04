package Graphics;

import Logic.ChatInfrastructure;
import Logic.ChatPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends BenPanel implements ActionListener {

    //Member Space
    private String id;

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private JPanel pan_west;
    private JPanel pan_north;
    private JPanel pan_center, pan_center_left, pan_center_right, pan_center_bottom;
    private JPanel pan_chat;

    private JButton cmd_send;
    private JButton cmd_inv;

    private JTextField txt_commandline;

    private JTabbedPane jtp_chat;
    private TextArea jta_clientlog;
    private TextArea jta_chatlog;

    private List lst_clients;
    private Label lbl_server_north;
    private Label lbl_clients;

    private boolean confirmed;

    private ChatInfrastructure<String, ChatPanel> chats;

    public ClientGUI(){

        this.id = "";
        this.confirmed = false;
        init();
    }

    //Getter - Setter
    public boolean isConfirmed(){ return this.confirmed; }
    public String getCommand(){

        this.confirmed = false;
        return this.txt_commandline.getText();
    }
    public String getId(){ return this.id; }

    public void setId(String id){ this.id = id; }

    private void initComponents(){

        pan_west = new JPanel();
        pan_north = new JPanel();
        pan_center = new JPanel();
        pan_center_left = new JPanel();
        pan_center_right = new JPanel();
        pan_center_bottom = new JPanel();

        cmd_send = new JButton("CONFIRM");
        cmd_send.addActionListener(this);
        jta_clientlog = new TextArea("",10, 1,TextArea.SCROLLBARS_VERTICAL_ONLY);
        jtp_chat = new JTabbedPane();
        txt_commandline = new JTextField();

        lst_clients = new List();
        lbl_server_north = new Label();
        lbl_clients = new Label("Clients");
    }
    private void initStyle(){

        setDefaultPanelStyle(pan_west);
        setDefaultPanelStyle(pan_north);
        setDefaultPanelStyle(pan_center);
        setDefaultMasterPanelStyle(pan_center_left, "  SERVERLOG  ");
        setDefaultMasterPanelStyle(pan_center_right, "  ■ ■ ■  ");
        setDefaultMasterPanelStyle(pan_center_bottom, "  COMMAND LINE  ");

        setDefaultButtonStyle(cmd_send);
        setDefaultListStyle(lst_clients);
        setDefaultTextAreaStyle(jta_clientlog);
        setDefaultTabbedPaneStyle(jtp_chat);
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
        gbc.weighty = 0.5;
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        pan_center.add(pan_center_left, gbc);
        pan_center_left.setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        pan_center_left.add(jta_clientlog, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        pan_center_left.add(pan_center_bottom, gbc);

        pan_center_bottom.setLayout(gbl);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        pan_center_bottom.add(txt_commandline, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        pan_center_bottom.add(cmd_send, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.5;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        pan_center.add(pan_center_right, gbc);
        pan_center_right.setLayout(gbl);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        pan_center_right.add(jtp_chat, gbc);
        resetGridBagConstraints(gbc);
    }

    //GUI Interactions
    public void addClient(String name){

        lst_clients.add(name);
    }
    public void addClientLog(String add){

        String depr = jta_clientlog.getText();
        depr += add + "\n";
        jta_clientlog.setText(depr);
    }
    public void removeClient(String name){

        lst_clients.remove(name);
    }
    public void addLog(String add){

        String was = jta_clientlog.getText();
        jta_clientlog.setText(was += add + "\n");
    }
    public void clearCommandLine(){
        txt_commandline.setText("");
    }
    private void createChatGUI(String name){

        pan_chat = new JPanel();
        setDefaultMasterPanelStyle(pan_chat, "  CHAT WITH: " + name + "  ");
        pan_chat.setLayout(gbl);

        cmd_inv = new JButton(" INVITE ");
        setDefaultButtonStyle(cmd_inv);

        jta_chatlog = new TextArea();
        setDefaultTextAreaStyle(jta_chatlog);

        resetGridBagConstraints(gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        pan_chat.add(jta_chatlog, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pan_chat.add(cmd_inv, gbc);

        jtp_chat.addTab(name, pan_chat);
    }
    public void transmittMessage(String transmitter, String message, String receiver, boolean selfdisplayed){

        if(!chats.isCreated(transmitter) && !selfdisplayed){

            //Chat Window has not been created on this GUI -> displaying a
            //message from transmitter to this receiver GUI
            //tab name -> transmitter != id
            createChatGUI(transmitter);
            chats.addChatGUI(new ChatPanel(cmd_inv, jta_chatlog), transmitter);
            System.out.println("CLient : " + id + "| chats with: ->" + chats.getS().toString());
            chats.addNotSelfDisplayedMessage(transmitter, message);

        } else if (!chats.isCreated(receiver) && selfdisplayed){

            //Chat Window has not been created on this GUI -> displaying this
            //users written message on his GUI
            //tab name -> id -> transmitter
            createChatGUI(receiver);
            chats.addChatGUI(new ChatPanel(cmd_inv, jta_chatlog), receiver);
            System.out.println("CLient : " + id + "| chats with: ->" + chats.getS().toString());
            chats.addSelfDisplayedMessage(transmitter, message, receiver);

        } else if (chats.isCreated(receiver) && selfdisplayed) {

            //Chat Window has been created
            //Displaying a message that has been sent from someone else...
            //tab name -> transmitter
            for (String s : chats.getS()) {

                if (s.equals(receiver)) {

                    chats.addSelfDisplayedMessage(transmitter, message, receiver);
                }
            }
        } else if (chats.isCreated(transmitter) && !selfdisplayed) {

            //Chat Window has been created
            //Displaying a message that has been sent from someone else...
            //tab name -> transmitter
            for (String s : chats.getS()) {

                if (s.equals(transmitter)) {

                    chats.addNotSelfDisplayedMessage(transmitter, message);
                }
            }
        }
    }

    //BenPanel (4)
    @Override
    public void init() {

        chats = new ChatInfrastructure<String, ChatPanel>();
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

    //ActionListener (1)
    @Override
    public void actionPerformed(ActionEvent ae) {

        if(ae.getSource() == cmd_send){
            confirmed = true;
        }
    }
}