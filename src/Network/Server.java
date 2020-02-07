package Network;

import Graphics.GWindow;
import Graphics.ServerGUI;
import Logic.Game;
import Numbers.Login;

import java.io.*;
import java.util.*;
import java.net.*;

// Network.Server class
public class Server
{
    private ServerGUI server_gui;
    private Vector<ClientHandler> ar;
    private Vector<Game> g;

    public Server(){

        ar = new Vector<>();
        g = new Vector<>();
        server_gui = new ServerGUI();
        new GWindow<ServerGUI>(server_gui);
    }

    public Vector<ClientHandler> getAr(){
        return ar;
    }
    public Vector<Game> getG(){ return g; }
    public ServerGUI getServerGUI(){
        return server_gui;
    }

    // counter for clients
    static int i = 0;

    // List to store authorized Logins
    static List<Login> ll = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        // server is listening on port 1234
        Server server = new Server();

        ServerSocket ss = new ServerSocket(40000);

        Socket s;

        ll.add(new Login( "admin", "admin"));
        ll.add(new Login("testid", "testpw"));
        ll.add(new Login("user","password"));
        // running infinite loop for getting
        // client request

        while (true)
        {

            try {

                // Accept the incoming request
                s = ss.accept();
                System.out.println("New client request received : " + s);

                // obtain input and output streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Creating a new handler for this client...");

                // Create a new handler object for handling this request.
                ClientHandler mtch = new ClientHandler(i, dis, dos, server);

                // Create a new Thread with this object.
                Thread t = new Thread(mtch);

                System.out.println("Adding this client to active client list");

                // add this client to active clients list
                server.getAr().add(mtch);

                // start the thread.
                t.start();

                // increment i for new client.
                // i is used for naming only, and can be replaced
                // by any naming scheme
                i++;

            }catch(IOException e){

                System.out.println("ERROR");
                break;
            }
        }
    }
}