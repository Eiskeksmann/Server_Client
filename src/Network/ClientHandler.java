package Network;

import Graphics.ServerGUI;
import Logic.Game;
import Numbers.Login;

import java.io.*;
import java.util.*;
import java.net.*;

class ClientHandler implements Runnable
{
    private String name;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private Server server;
    private ServerGUI server_gui;
    private boolean isloggedin;

    // constructor
    public ClientHandler(int i,
                         DataInputStream dis, DataOutputStream dos,
                         Server server) {

        this.dis = dis;
        this.dos = dos;
        this.name = Integer.toString(i);
        this.isloggedin=false;
        this.server = server;
        this.server_gui = server.getServerGUI();

    }

    private void msgFailed(){

        try{

            dos.writeUTF("Violated Message Parameters try again...");
            System.out.println("COMMAND [MSG] FAILED FOR CLIENT: [" + this.name + "] \n" +
                    "WRONG FORMAT OR NO SUCH RECIPIENT");
        }
        catch ( IOException e ){

            e.printStackTrace();
        }
    }

    @Override
    public void run() {


        String received;
        boolean isactive = true;
        while (isactive)
        {
            try
            {
                while(!isloggedin){

                    //Numbers.Login Process
                    dos.writeUTF("[SERVER]-Please Enter your Username:");
                    String id = dis.readUTF();
                    dos.writeUTF("[SERVER]-Please Enter your Password:");
                    String pw = dis.readUTF();
                    Login attempt = new Login(id, pw);
                    dos.writeUTF("[SERVER]-Trying to log in as: \n ID:" + attempt.getID()
                            + "\n PW:" + attempt.getPW());

                    //New Numbers.Login Logic ...
                    dos.writeUTF("[SERVER]-Checking Through[" + Server.ll.size() + "] Authorized Clients ...");
                    int i = 0;
                    for(Login lo : Server.ll){

                        if(attempt.compareID(lo)){

                            if(attempt.comparePW(lo)){

                                //Login Successfull
                                boolean permission = true;
                                for (ClientHandler ch : server.getAr()) {

                                    if (ch.name.equals(lo.getID()) && ch.isloggedin) {

                                        permission = false;
                                        dos.writeUTF("[SERVER]-This User is already logged in...");
                                        server_gui.addLog("CLIENT NUMBER [" +  name +"] LOGIN PERMISSION DENIED -" +
                                                " USER HAS ALREADY LOGGED IN FROM ANOTHER CLIENT");
                                    }
                                }
                                if(permission) {
                                    //Login did work - User already existed in Database
                                    dos.writeUTF("[SERVER]-You are now logged in as: " + attempt.getID());
                                    server_gui.addLog("USER: [" + attempt.getID() + "] has logged in");
                                    name = attempt.getID();
                                    server_gui.addClient(name);
                                    isloggedin = true;
                                    for(ClientHandler ch : server.getAr()){
                                        if(ch.isloggedin){
                                            ch.dos.writeUTF("[LOGIN]-"+ name);
                                            if(!ch.name.equals(this.name))
                                            dos.writeUTF("[MYLOGIN]-"+ch.name);
                                        }
                                    }
                                    break;
                                }
                            }
                            else{

                                //Wrong Password
                                dos.writeUTF("[SERVER]-Wrong Password - Please try again ...");
                                isloggedin = false;
                            }
                            break;
                        }
                        else if(!attempt.compareID(lo) && i == Server.ll.size() - 1){

                            //Register a new Account
                            Server.ll.add(attempt);
                            dos.writeUTF("[SERVER]-New Account created and logged in as: " + attempt.getID());
                            System.out.println("USER: [" + attempt.getID() +"] has registered and logged  in");
                            server_gui.addLog("USER: [" + attempt.getID() +"] has registered and logged  in");
                            name = attempt.getID();
                            server_gui.addClient(name);
                            isloggedin = true;
                            for(ClientHandler ch : server.getAr()){
                                if(ch.isloggedin){
                                    ch.dos.writeUTF("[LOGIN]-"+ name);
                                    if(!ch.name.equals(this.name))
                                    dos.writeUTF("[LOGIN]-"+ ch.name);
                                }
                            }
                            break;
                        }
                        else{

                            i++;
                        }
                    }
                }
                // receive the string
                dos.writeUTF("[SERVER]-Type [?] for a list of commands");
                received = dis.readUTF();

                switch(received) {

                    case("?"):
                        System.out.println("USER: [" + name + "] TRIES COMMAND:" + received);
                        server_gui.addLog("USER: [" + name + "] TRIES COMMAND:" + received);
                        dos.writeUTF("[SERVER]-WELCOME [" + name + "] \n"
                                + "TYPE [LOC] - displays a list of active clients \n" +
                                "TYPE [MSG] - to send a Message to a client \n" +
                                "TYPE [LOGOUT] - to simply logout");
                        break;

                    case("LOC"):
                        System.out.println("USER: [" + name + "] TRIES COMMAND:" + received);
                        server_gui.addLog("USER: [" + name + "] TRIES COMMAND:" + received);
                        for(ClientHandler ch : server.getAr()){

                            dos.writeUTF("[SERVER]-[ACTIVE CLIENT] : " + ch.name);
                        }
                        break;

                    case("MSG"):
                        System.out.println("USER: [" + name + "] TRIES COMMAND:" + received);
                        server_gui.addLog("USER: [" + name + "] TRIES COMMAND:" + received);
                        // break the string into message and recipient part
                        dos.writeUTF("[SERVER]-Please enter your Message in the following Format: \n " +
                                "MESSAGE[TEXT]-RECIPIENT[NAME]");
                        String rec = dis.readUTF();
                         try {
                             StringTokenizer st = new StringTokenizer(rec, "-");
                             String MsgToSend = st.nextToken();
                             String recipient = st.nextToken();

                             // search for the recipient in the connected devices list.
                             // ar is the vector storing client of active users
                             if(MsgToSend == null || recipient == null){

                                 msgFailed();
                                 break;
                             }
                             for (ClientHandler ch : server.getAr())
                             {
                                 // if the recipient is found, write on its
                                 // output stream
                                 if (ch.name.equals(recipient) || recipient.equals("ALL") && ch.isloggedin)
                                 {
                                     server_gui.addLog("[MSG] DELIVERY SUCCESS FROM [" + this.name + "] to ["
                                             + ch.name + "]");
                                     ch.dos.writeUTF("[MSG]-" + name + ": " + MsgToSend + ":" + ch.name);
                                     if(!name.equals(ch.name)) {
                                         dos.writeUTF("[ME]-" + name + ": " + MsgToSend + ":" + ch.name);
                                     }
                                     dos.writeUTF("[SERVER]-Message was delivered... ");
                                 }
                             }
                             break;

                         }catch(Exception tok){

                             dos.writeUTF("[SERVER]-Something went wrong during delivery, please try again...");
                             break;

                         }
                    case("SERVERHACK"):

                        for(Login lo : Server.ll){

                            dos.writeUTF("[SERVER]-" + lo.getID() +" - " + lo.getPW());
                        }
                        break;
                    case("GAME"):
                        dos.writeUTF("[SERVER]-" + "Type Game Parameters in following Format : \n" +
                                "[GAMEID]|[VS PLAYERID] \n" + "[GAMEID] -> V for VierGewinnnt / F for Futtern \n" +
                                "[VS PLAYERID] -> 0 for AI / client from client list - to ask for a battle ...");


                        String in = dis.readUTF();
                        StringTokenizer t = new StringTokenizer(in, "|");
                        String gametype = t.nextToken();
                        String playerid = t.nextToken();

                        if(gametype == null || playerid == null) {

                            dos.writeUTF("[SERVER]-Something went wrong during Game Creation, please try again...");
                            break;
                        }
                        else if(playerid.equals("0")){

                            dos.writeUTF("[HOST]-"+ gametype +"-BOT");
                            break;
                        }

                        for(ClientHandler ch : server.getAr()){

                            if(playerid.equals(ch.name)){

                                dos.writeUTF("[HOST]-"+ gametype + "|" + ch.name);
                                ch.dos.writeUTF("[CONNECT]-"+ gametype + "|" + this.name);
                                server.getG().add(new Game(this.name + "|" + ch.name));
                            }
                        }
                        break;
                    case("VTURN"):

                        dos.writeUTF("[SERVER]-" + "Type Game Parameters in following Format : \n" +
                                "[OPPONENTID]|[int COLUMN] \n" + "[OPPONENTID] -> Name of your current Game Opponent \n" +
                                "[int COLUMN] -> Number between 1 and 7");
                        String vturn = dis.readUTF();
                        StringTokenizer vtur = new StringTokenizer(vturn, "|");
                        String vturnopponent = vtur.nextToken();
                        String vturnval = vtur.nextToken();

                        for(Game g : server.getG()){

                            if(!g.getGameid().equals(this.name + "|" +  vturnopponent) &&
                                    !g.getGameid().equals(vturnopponent + "|" + this.name)){

                                dos.writeUTF("[SERVER]- No such game found were afraid ... \n");
                                break;
                            }
                        }
                        if(vturnopponent.equals("BOT")){
                            dos.writeUTF("[OWNVTURN]-" + vturnval);
                        }
                        for(ClientHandler ch : server.getAr()){

                            if(ch.name.equals(vturnopponent) && ch.isloggedin){

                                ch.dos.writeUTF("[VTURN]-" + vturnval);
                                dos.writeUTF("[OWNVTURN]-" + vturnval);
                                break;
                            }
                        }
                        break;
                    case("FTURN"):
                        dos.writeUTF("[SERVER]-" + "Type Game Parameters in following Format : \n" +
                                "[OPPONENTID]|[int COLUMN] \n" + "[OPPONENTID] -> Name of your current Game Opponent \n" +
                                "[int COLUMN] -> Number between 1 and 7");
                        String fturn = dis.readUTF();
                        StringTokenizer ftur = new StringTokenizer(fturn, "|");
                        String fturnopponent = ftur.nextToken();
                        String fturnvalx = ftur.nextToken();
                        String fturnvaly = ftur.nextToken();

                        for(Game g : server.getG()){

                            if(!g.getGameid().equals(this.name + "|" +  fturnopponent) &&
                                    !g.getGameid().equals(fturnopponent + "|" + this.name)){

                                dos.writeUTF("[SERVER]- No such game found were afraid ... \n");
                                break;
                            }
                        }
                        if(fturnopponent.equals("BOT")){
                            dos.writeUTF("[OWNFTURN]-" + fturnvalx + "|" + fturnvaly);
                        }
                        for(ClientHandler ch : server.getAr()){

                            if(ch.name.equals(fturnopponent) && ch.isloggedin){

                                ch.dos.writeUTF("[FTURN]-" + "|" + fturnvalx + "|" +  fturnvaly);
                                dos.writeUTF("[OWNFTURN]-" + fturnvalx + "|" + fturnvaly);
                                break;
                            }
                        }
                        break;
                    case("LOGOUT"):

                        server_gui.addLog("USER: [" + name +"] has successfully logged out");
                        for(ClientHandler ch: server.getAr()){

                            if(ch.isloggedin){
                                ch.dos.writeUTF("[LOGOUT]-" + this.name);
                                if(ch.name.equals(this.name)){
                                    dos.writeUTF("[LOGOUT]-" + this.name);
                                }
                            }
                        }
                        server.getAr().remove(this);
                        server_gui.removeClient(this.name);
                        this.isloggedin=false;
                        isactive = false;
                        break;
                }

                if(!isactive){

                    break;
                }

            } catch (SocketException e) {

                System.out.println("ERROR");
                isactive = false;
            }
            catch (IOException e){

                e.printStackTrace();
                isactive = false;
            }
        }
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}