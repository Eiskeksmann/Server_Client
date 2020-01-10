package Network;

import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import Graphics.ClientPanel;
import Graphics.Window;

public class Client
{
    final static int ServerPort = 40000;
    public static Window gui;
    public static ClientPanel pan;

    public Client(){

       gui = new Window("CLIENT");
       pan = (ClientPanel)gui.getBpanel();

    }

    public static void main(String args[]) throws UnknownHostException, IOException
    {
        new Client();

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

                    String msg = pan.getCommand();

                        try {
                            // write on the output stream
                            if(pan.getSendCommand()){
                                dos.writeUTF(msg);
                                pan.setCommandText("");
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
                                pan.addServerCom(msg);
                                break;
                            case("[LOGIN]"):
                                pan.addClient(val);
                                break;
                            case("[LOGOUT]"):
                                pan.removeClient(val);
                                break;
                            case("[ME]"):
                                pan.addInbox(val);
                                break;
                            default:
                                pan.addInbox(msg);
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

        //Graphical Overlay
    }
}