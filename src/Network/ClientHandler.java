package Network;

import Graphics.ServerPanel;
import Graphics.Window;
import Numbers.Login;

import java.io.*;
import java.util.*;
import java.net.*;

class ClientHandler implements Runnable
{
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Window gui;
    ServerPanel pan;
    Socket s;
    boolean isloggedin;

    // constructor
    public ClientHandler(Socket s, int i,
                         DataInputStream dis, DataOutputStream dos,
                         Window gui) {
        this.gui = gui;
        this.dis = dis;
        this.dos = dos;
        this.name = Integer.toString(i);
        this.s = s;
        this.isloggedin=false;
        this.pan = (ServerPanel) gui.getBpanel();
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

                    //Numbers.Login Proccess
                    dos.writeUTF("[SERVER]-Please Enter your Username:");
                    String id = dis.readUTF();
                    dos.writeUTF("[SERVER]-Please Enter your Password:");
                    String pw = dis.readUTF();
                    Login attempt = new Login(id, pw);
                    dos.writeUTF("[SERVER]-Trying to log in as: \n ID:" + attempt.getID() + "\n PW:" + attempt.getPW());

                    //New Numbers.Login Logic ...
                    dos.writeUTF("[SERVER]-Checking Through[" + Server.ll.size() + "] Authorized Clients ...");
                    int i = 0;
                    for(Login lo : Server.ll){

                        if(attempt.compareID(lo)){

                            if(attempt.comparePW(lo)){

                                //Login Successfull
                                boolean permission = true;
                                for (ClientHandler ch : Server.ar) {

                                    if (ch.name.equals(lo.getID()) && ch.isloggedin) {

                                        permission = false;
                                        dos.writeUTF("[SERVER]-This User is already logged in...");
                                        pan.addLog("CLIENT NUMBER [" +  name +"] LOGIN PERMISSION DENIED -" +
                                                " USER HAS ALREADY LOGGED IN FROM ANOTHER CLIENT");
                                    }
                                }
                                if(permission) {
                                    //Login did work - User already existed in Database
                                    dos.writeUTF("[SERVER]-You are now logged in as: " + attempt.getID());
                                    pan.addLog("USER: [" + attempt.getID() + "] has logged in");
                                    name = attempt.getID();
                                    pan.addClient(name);
                                    isloggedin = true;
                                    for(ClientHandler ch : Server.ar){
                                        if(ch.isloggedin){
                                            ch.dos.writeUTF("[LOGIN]-"+ name);
                                            if(!ch.name.equals(this.name))
                                            dos.writeUTF("[LOGIN]-"+ch.name);
                                        }
                                    }
                                    break;
                                }
                                break;
                            }
                            else{

                                //Wrong Password
                                dos.writeUTF("[SERVER]-Wrong Password - Please try again ...");
                                isloggedin = false;
                                break;
                            }
                        }
                        else if(!attempt.compareID(lo) && i == Server.ll.size() - 1){

                            //Register a new Account
                            Server.ll.add(attempt);
                            dos.writeUTF("[SERVER]-New Account created and logged in as: " + attempt.getID());
                            System.out.println("USER: [" + attempt.getID() +"] has registered and logged  in");
                            pan.addLog("USER: [" + attempt.getID() +"] has registered and logged  in");
                            name = attempt.getID();
                            pan.addClient(name);
                            isloggedin = true;
                            for(ClientHandler ch : Server.ar){
                                if(ch.isloggedin){
                                    ch.dos.writeUTF("[LOGIN]-"+ name);
                                    if(!ch.name.equals(this.name))
                                    dos.writeUTF("[LOGIN]-"+ch.name);
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
                        pan.addLog("USER: [" + name + "] TRIES COMMAND:" + received);
                        dos.writeUTF("[SERVER]-WELCOME [" + name + "] \n"
                                + "TYPE [LOC] - displays a list of active clients \n" +
                                "TYPE [MSG] - to send a Message to a client \n" +
                                "TYPE [LOGOUT] - to simply logout");
                        break;

                    case("LOC"):
                        System.out.println("USER: [" + name + "] TRIES COMMAND:" + received);
                        pan.addLog("USER: [" + name + "] TRIES COMMAND:" + received);
                        for(ClientHandler ch : Server.ar){

                            dos.writeUTF("[SERVER]-[ACTIVE CLIENT] : " + ch.name);
                        }
                        break;

                    case("MSG"):
                        System.out.println("USER: [" + name + "] TRIES COMMAND:" + received);
                        pan.addLog("USER: [" + name + "] TRIES COMMAND:" + received);
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
                             if(MsgToSend.equals(null) || recipient.equals(null)){

                                 msgFailed();
                                 break;
                             }
                             for (ClientHandler ch : Server.ar)
                             {
                                 // if the recipient is found, write on its
                                 // output stream
                                 if (ch.name.equals(recipient) && ch.isloggedin==true)
                                 {
                                     dos.writeUTF("[SERVER]-Message was delivered... ");
                                     System.out.println("[MSG] DELIVERY SUCCESS FROM [" + this.name + "] to ["
                                             + ch.name + "]");
                                     pan.addLog("[MSG] DELIVERY SUCCESS FROM [" + this.name + "] to ["
                                             + ch.name + "]");
                                     ch.dos.writeUTF("[MSG]-"+ this.name + " : "+MsgToSend);
                                     break;
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
                    case("LOGOUT"):

                        pan.addLog("USER: [" + name +"] has successfully logged out");
                        for(ClientHandler ch: Server.ar){

                            if(ch.isloggedin){
                                ch.dos.writeUTF("[LOGOUT]-" + this.name);
                                if(ch.name.equals(this.name)){
                                    dos.writeUTF("[LOGOUT]-" + this.name);
                                }
                            }
                        }
                        Server.ar.remove(this);
                        pan.removeClient(this.name);
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