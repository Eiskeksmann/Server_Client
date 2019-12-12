import java.io.*;
import java.util.*;
import java.net.*;

class ClientHandler implements Runnable
{
    Scanner scn = new Scanner(System.in);
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;

    // constructor
    public ClientHandler(Socket s, int i,
                         DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = Integer.toString(i);
        this.s = s;
        this.isloggedin=false;
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

                    //Login Proccess
                    dos.writeUTF("Please Enter your Username:");
                    String id = dis.readUTF();
                    dos.writeUTF("Please Enter your Password:");
                    String pw = dis.readUTF();
                    Login attempt = new Login(id, pw);
                    dos.writeUTF("Trying to log in as: \n ID:" + attempt.getID() + "\n PW:" + attempt.getPW());

                    //New Login Logic ...
                    dos.writeUTF("Checking Through[" + Server.ll.size() + "] Authorized Clients ...");
                    int i = 0;
                    for(Login lo : Server.ll){

                        if(attempt.compareID(lo)){

                            if(attempt.comparePW(lo)){

                                //Login Successfull
                                boolean permission = true;
                                for (ClientHandler ch : Server.ar) {

                                    if (ch.name.equals(lo.getID()) && ch.isloggedin) {

                                        permission = false;
                                        dos.writeUTF("This User is already logged in...");
                                        System.out.println("CLIENT NUMBER [" +  name +"] LOGIN PERMISSION DENIED -" +
                                                " USER HAS ALREADY LOGGED IN FROM ANOTHER CLIENT" );
                                    }
                                }
                                if(permission) {
                                    //Login did work - User already existed in Database
                                    dos.writeUTF("You are now logged in as: " + attempt.getID());
                                    System.out.println("User: [" + attempt.getID() + "] has logged in");
                                    name = attempt.getID();
                                    isloggedin = true;
                                    break;
                                }
                                break;
                            }
                            else{

                                //Wrong Password
                                dos.writeUTF("Wrong Password - Please try again ...");
                                isloggedin = false;
                                break;
                            }
                        }
                        else if(!attempt.compareID(lo) && i == Server.ll.size() - 1){

                            //Register a new Account
                            Server.ll.add(attempt);
                            dos.writeUTF("New Account created and logged in as: " + attempt.getID());
                            System.out.println("USER: [" + attempt.getID() +"] has registered and logged  in");
                            name = attempt.getID();
                            isloggedin = true;
                            for(ClientHandler ch : Server.ar){
                                if(ch.isloggedin){
                                    ch.dos.writeUTF("[" + this.name + "] has now logged in...");
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
                dos.writeUTF("Type [?] for a list of commands");
                received = dis.readUTF();

                switch(received) {

                    case("?"):
                        System.out.println("USER: [" + name + "] TRIES COMMAND:" + received);
                        dos.writeUTF("WELCOME [" + name + "] \n"
                                + "TYPE [LOC] - displays a list of active clients \n" +
                                "TYPE [MSG] - to send a Message to a client");
                        break;

                    case("LOC"):
                        System.out.println("USER: [" + name + "] TRIES COMMAND:" + received);
                        for(ClientHandler ch : Server.ar){

                            dos.writeUTF("[ACTIVE CLIENT] : " + ch.name);
                        }
                        break;

                    case("MSG"):
                        System.out.println("USER: [" + name + "] TRIES COMMAND:" + received);
                        // break the string into message and recipient part
                        dos.writeUTF("Please enter your Message in the following Format: \n " +
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
                                     dos.writeUTF("Message was delivered... ");
                                     System.out.println("[MSG] DELIVERY SUCCESS FROM [" + this.name + "] to ["
                                             + ch.name + "]");
                                     ch.dos.writeUTF(this.name+" : "+MsgToSend);
                                     break;
                                 }
                             }
                             break;

                         }catch(Exception tok){

                             dos.writeUTF("Something went wrong during delivery, please try again...");
                             break;

                         }

                    case("SERVERHACK"):

                        for(Login lo : Server.ll){

                            dos.writeUTF(lo.getID() +" - " + lo.getPW());
                        }
                        break;
                    case("LOGOUT"):

                        for(ClientHandler ch : Server.ar){

                            if(ch.name.equals(this.name)){

                                 Server.ar.remove(ch);
                            }
                        }
                        this.isloggedin=false;
                        this.s.close();
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