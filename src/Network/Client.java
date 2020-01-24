package Network;

import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import Graphics.ClientGUI;
import Graphics.ClientPanel;
import Graphics.GWindow;
import Graphics.Window;

public class Client
{
    final static int ServerPort = 40000;
    public GWindow gui;
    public ClientGUI client_gui;

    public GWindow<ClientGUI> getGUI(){ return gui;}
    public ClientGUI getClientGUI(){ return client_gui; }

    public Client(){

        client_gui = new ClientGUI();
        gui = new GWindow<ClientGUI>(client_gui);
    }

    public static void main(String args[]) throws UnknownHostException, IOException
    {
        Client client = new Client();
        ClientGUI client_gui = client.getClientGUI();

        Scanner scn = new Scanner(System.in);

        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection
        Socket s = new Socket(ip, ServerPort);

        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        // sendMessage thread
        Thread sendMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {

                    String msg = client_gui.getCommand();

                        try {
                            // write on the output stream
                            if(client_gui.isConfirmed()){
                                dos.writeUTF(msg);
                                client_gui.clearCommandLine();
                            }

                        } catch (IOException e) {

                            System.out.println("DOS -> SERVER CONNECTION HAS BEEN LOST ...");
                            break;
                        }
                }
            }
        });

        // readMessage thread
        Thread readMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = dis.readUTF();
                        System.out.println(msg);

                        StringTokenizer token = new StringTokenizer(msg, "-");
                        String command = token.nextToken();
                        String val = token.nextToken();

                        switch(command){

                            case("[SERVER]"):
                                client_gui.addLog(msg);
                                break;
                            case("[LOGIN]"):
                                client_gui.addClient(val);
                                break;
                            case("[LOGOUT]"):
                                client_gui.removeClient(val);
                                break;
                            case("[ME]"):
                                //client_gui.addInbox(val);
                                break;
                            default:
                                //client_gui.addInbox(msg);
                                break;
                        }


                    } catch (IOException e) {

                        System.out.println("DIS -> SERVER CONNECTION HAS BEEN LOST...");
                        break;
                    }
                }
            }
        });

        sendMessage.start();
        readMessage.start();
    }
}