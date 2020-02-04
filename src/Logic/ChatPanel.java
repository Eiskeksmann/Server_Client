package Logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPanel extends JPanel implements ActionListener {

    private JButton inv;
    private TextArea chatlog;
    private String chat;

    public ChatPanel(JButton inv, TextArea chatlog){

        this.inv = inv;
        this.inv.addActionListener(this);
        this.chatlog = chatlog;
        this.chat = "";
    }
    public void setChat(String transmitter, String message){

        chatlog.setText(chat += transmitter + ": " + message + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == inv){

            inv.setEnabled(false);
        }
    }
}
