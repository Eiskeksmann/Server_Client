package Graphics;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.xml.soap.Text;
import java.awt.*;

public class TestPanel extends BenPanel {

    //Member
    private final Color COL_DARKISHBLUE = new Color(18, 38, 58);
    private final Font FONT_CASUAL = new Font("Arial", Font.BOLD, 14);

    private Label lbl_clients;
    private Label lbl_inbox;
    private Label lbl_com_box;
    private Label lbl_com_line;
    private Label lbl_send;

    private List lst_clients;

    private TextArea txa_com;
    private TextArea txa_inbox;

    private TextField txt_input;

    private Button cmd_send;

    private JPanel pan_center,
            pan_center_left, pan_center_left_top, pan_center_left_bot,
            pan_center_right, pan_center_right_top, pan_center_right_bot;
    private JPanel pan_west;

    private BorderLayout lay_bor;
    private GridBagLayout lay_gbl;
    private FlowLayout lay_flow;
    private BoxLayout lay_box;

    private GridBagConstraints con_gbc;

    //Constructor Space
    public TestPanel(int res_x, int res_y){

        super.setRes_x(res_x);
        super.setRes_y(res_y);
        init();
    }

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

        //Layout
        lay_gbl = new GridBagLayout();
        con_gbc = new GridBagConstraints();
        lay_bor = new BorderLayout();
        lay_flow = new FlowLayout();

        //Labels
        lbl_clients = new Label("Clients");
        setDefaultLabelStyle(lbl_clients);

        lbl_com_box = new Label("Command Box");
        setDefaultLabelStyle(lbl_com_box);

        lbl_com_line = new Label("Command Line");
        setDefaultLabelStyle(lbl_com_line);

        lbl_inbox = new Label("Inbox");
        setDefaultLabelStyle(lbl_inbox);

        lbl_send = new Label("< - >");
        setDefaultLabelStyle(lbl_send);

        //Lists
        lst_clients = new List();

        //TextAreas
        txa_inbox = new TextArea("INBOX",10,55,TextArea.SCROLLBARS_BOTH);
        txa_com = new TextArea("SERVER LOG",10,55,TextArea.SCROLLBARS_VERTICAL_ONLY);

        //TextField
        txt_input = new TextField(55);

        //Button
        cmd_send = new Button("Confirm Command");
        setDefaultButtonStyle(cmd_send);

        //Panels
        pan_center = new JPanel();
        pan_center.setBackground(Color.WHITE);

        pan_center_left = new JPanel();
        setDefaultMasterPanelStyle(pan_center_left,"SERVER I/O");

        pan_center_left_top = new JPanel();
        setDefaultPanelStyle(pan_center_left_top);

        pan_center_left_bot = new JPanel();
        setDefaultPanelStyle(pan_center_left_bot);

        pan_center_right = new JPanel();
        setDefaultMasterPanelStyle(pan_center_right, "CHAT");

        pan_center_right_top = new JPanel();
        setDefaultPanelStyle(pan_center_right_top);

        pan_center_right_bot = new JPanel();
        setDefaultPanelStyle(pan_center_right_bot);

        pan_west = new JPanel();
        pan_west.setBackground(COL_DARKISHBLUE);

        this.setLayout(lay_bor);
    }
    private void setCenterPanel(){

        //Center
        pan_center.setLayout(lay_gbl);
        con_gbc.weighty = 1.0;
        con_gbc.weightx = 1.0;
        con_gbc.insets = new Insets(10,10,10,10);
        con_gbc.fill = GridBagConstraints.VERTICAL;
        con_gbc.anchor = GridBagConstraints.PAGE_START;
        con_gbc.gridx = 0;
        con_gbc.gridy = 0;
        pan_center.add(pan_center_left, con_gbc);

        con_gbc.gridx = 1;
        con_gbc.gridy = 0;
        pan_center.add(pan_center_right, con_gbc);

        //Left Settings
        pan_center_left.setLayout(lay_gbl);
        con_gbc.gridwidth = 1;
        //fill -> vertikal ✔
        //anchor -> FIRST_LINE_START ✔
        //weight -> 1.0 ✔

        //Left Top
        con_gbc.gridx = 0;
        con_gbc.gridy = 0;
        pan_center_left.add(pan_center_left_top, con_gbc);

        pan_center_left_top.setLayout(lay_gbl);
        con_gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        con_gbc.ipady = 0;
        con_gbc.fill = GridBagConstraints.HORIZONTAL;

        con_gbc.gridx = 0;
        con_gbc.gridy = 0;
        pan_center_left_top.add(lbl_com_box, con_gbc);
        con_gbc.fill = GridBagConstraints.VERTICAL;
        con_gbc.gridx = 0;
        con_gbc.gridy = 1;
        pan_center_left_top.add(txa_com, con_gbc);

        //Left Bot
        con_gbc.gridx = 0;
        con_gbc.gridy = 1;
        con_gbc.fill = GridBagConstraints.HORIZONTAL;
        con_gbc.anchor = GridBagConstraints.LAST_LINE_START;
        pan_center_left.add(pan_center_left_bot, con_gbc);
        pan_center_left_bot.setLayout(lay_gbl);

        con_gbc.gridx = 0;
        con_gbc.gridy = 0;
        pan_center_left_bot.add(lbl_com_line, con_gbc);

        con_gbc.gridx = 0;
        con_gbc.gridy = 1;
        pan_center_left_bot.add(txt_input, con_gbc);

        //Right Top
        pan_center_right.setLayout(lay_gbl);
        con_gbc.gridx = 0;
        con_gbc.gridy = 0;
        con_gbc.fill = GridBagConstraints.VERTICAL;
        con_gbc.anchor = GridBagConstraints.PAGE_START;
        pan_center_right.add(pan_center_right_top, con_gbc);

        pan_center_right_top.setLayout(lay_gbl);
        con_gbc.ipady = 0;
        con_gbc.fill = GridBagConstraints.HORIZONTAL;

        con_gbc.gridx = 0;
        con_gbc.gridy = 0;
        pan_center_right_top.add(lbl_inbox, con_gbc);
        con_gbc.fill = GridBagConstraints.VERTICAL;
        con_gbc.gridx = 0;
        con_gbc.gridy = 1;
        pan_center_right_top.add(txa_inbox, con_gbc);

        //Right Bot
        con_gbc.gridx = 0;
        con_gbc.gridy = 1;
        con_gbc.fill = GridBagConstraints.HORIZONTAL;
        con_gbc.anchor = GridBagConstraints.LAST_LINE_START;
        pan_center_right.add(pan_center_right_bot, con_gbc);
        pan_center_right_bot.setLayout(lay_gbl);

        con_gbc.gridx = 0;
        con_gbc.gridy = 0;
        pan_center_right_bot.add(lbl_send, con_gbc);

        con_gbc.gridx = 0;
        con_gbc.gridy = 1;
        pan_center_right_bot.add(cmd_send, con_gbc);
    }
    private void setWestPanel(){

        //West
        pan_west.setLayout(lay_gbl);
        con_gbc.anchor = GridBagConstraints.PAGE_START;
        con_gbc.weightx = 1.0;
        con_gbc.weighty = 1.0;
        con_gbc.ipady = 0;
        con_gbc.insets = new Insets(4,4,4,10);
        con_gbc.fill = GridBagConstraints.HORIZONTAL;
        con_gbc.ipady = 50;

        con_gbc.gridy = 0;
        con_gbc.gridx = 0;
        pan_west.add(lbl_clients, con_gbc);
        con_gbc.fill = GridBagConstraints.VERTICAL;
        pan_west.add(lst_clients, con_gbc);
    }
    private void initLayout(){

        this.setBorder(new LineBorder(COL_DARKISHBLUE, 4));

        setCenterPanel();
        setWestPanel();

        //Add Components to main Panel
        this.add(pan_center, BorderLayout.CENTER);
        this.add(pan_west, BorderLayout.WEST);
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
    public void addClient(String name){
        lst_clients.add(name);
    }
    public void removeClient(String name){
        lst_clients.remove(name);
    }
}
