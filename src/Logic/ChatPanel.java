package Logic;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {

    private JButton inv;
    private TextArea chatlog;

    private String chat;

    public ChatPanel(JButton inv, TextArea chatlog){

        this.inv = inv;
        this.chatlog = chatlog;
        this.chat = "";
    }
    public void setChat(String transmitter, String message){

        chatlog.setText(chat += transmitter + ": " + message + "\n");
    }
}
