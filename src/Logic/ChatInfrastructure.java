package Logic;

import java.util.ArrayList;

public class ChatInfrastructure <S extends String, J extends ChatPanel>{

    private ArrayList<S> names;
    private ArrayList<J> panels;

    public ChatInfrastructure(){

        names = new ArrayList<>();
        panels = new ArrayList<>();
    }

    public ArrayList<S> getS(){ return names; }
    public boolean isCreated(String name){

        return names.contains(name);
    }
    public void addChatGUI(J j, S s){

        names.add(s);
        panels.add(j);
    }
    public void addNotSelfDisplayedMessage(String transmitter, String message){

        panels.get(names.indexOf(transmitter)).setChat(transmitter, message);
    }
    public void addSelfDisplayedMessage(String transmitter, String message, String receiver){

            panels.get(names.indexOf(receiver)).setChat(transmitter, message);
    }
}
