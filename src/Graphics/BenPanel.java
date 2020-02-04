package Graphics;

import Numbers.Constants;
import util.Cmd;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public abstract class BenPanel extends JPanel{

    private static final Color COL_DARKISHBLUE = new Color(18, 38, 58);
    private static final Font FONT_CASUAL = new Font("Arial", Font.BOLD, 12);

    private int res_x;
    private int res_y;

    public Color getDefaultColor(){ return COL_DARKISHBLUE; }
    public Font getDefaultFont(){ return FONT_CASUAL; }
    public int getRes_x(){ return res_x; }
    public int getRes_y(){ return res_y; }

    public void setRes_x(int res_x){ this.res_x = res_x; }
    public void setRes_y(int res_y){ this.res_y = res_y; }

    public abstract void init();

    public abstract void update();
    public abstract void input();
    public abstract void render(Graphics g);

    public static Font getFONT_CASUAL(){ return FONT_CASUAL; }
    public static Color getCOL_DARKISHBLUE(){ return COL_DARKISHBLUE; }

    protected void setDefaultTextAreaStyle(TextArea txa){

        txa.setFont(FONT_CASUAL);
        txa.setForeground(Color.WHITE);
        txa.setBackground(COL_DARKISHBLUE);
        txa.setEditable(false);
        txa.setRows(5);
    }
    protected void setDefaultTextFieldStyle(JTextField tf){

        tf.setFont(FONT_CASUAL);
        tf.setForeground(COL_DARKISHBLUE);
    }
    protected void setDefaultCmdStyle(Cmd cmd){

        cmd.setFont(FONT_CASUAL);
        cmd.setForeground(COL_DARKISHBLUE);
    }
    protected void setDefaultListStyle(List l){

        l.setFont(FONT_CASUAL);
        l.setForeground(COL_DARKISHBLUE);
        l.setMultipleMode(true);
    }
    protected void setDefaultButtonStyle(JButton b){

        b.setBackground(Color.WHITE);
        b.setForeground(COL_DARKISHBLUE);
        b.setFont(FONT_CASUAL);
    }
    protected void setDefaultLabelStyle(Label l){

        l.setAlignment(Label.CENTER);
        l.setFont(FONT_CASUAL);
        l.setForeground(Color.WHITE);
        l.setBackground(COL_DARKISHBLUE);
    }
    protected void setDefaultPanelStyle(JPanel jp){

        jp.setBackground(Color.WHITE);
        jp.setBorder(new LineBorder(COL_DARKISHBLUE, 3));
        jp.setDoubleBuffered(true);
    }
    protected void setDefaultMasterPanelStyle(JPanel jp, String title){

        jp.setBackground(Color.WHITE);
        jp.setBorder(new TitledBorder(new LineBorder(COL_DARKISHBLUE, 3),
                title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION ,
                FONT_CASUAL));
    }
    protected void setDefaultTabbedPaneStyle(JTabbedPane jtp){

        jtp.setFont(FONT_CASUAL);
        jtp.setForeground(Color.WHITE);
        jtp.setBackground(COL_DARKISHBLUE);
        jtp.setTabPlacement(JTabbedPane.BOTTOM);
    }
    protected void resetGridBagConstraints(GridBagConstraints g) {

        g.weighty = 0.0;
        g.weightx = 0.0;
        g.anchor = GridBagConstraints.FIRST_LINE_START;
        g.insets = new Insets(Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO);
        g.ipady = 0;
        g.ipadx = 0;
        g.fill = GridBagConstraints.NONE;
        g.gridheight = 1;
        g.gridwidth = 1;
    }

}
