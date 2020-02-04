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
    private GWindow gui;
    private ClientGUI client_gui;
    private String id;

    public GWindow<ClientGUI> getGUI(){ return gui; }
    public ClientGUI getClientGUI(){ return client_gui; }
    public String getId(){ return id; }

    public void setId(String set){ id = set; }

    public Client(){

        client_gui = new ClientGUI();
        gui = new GWindow<ClientGUI>(client_gui);
        id = "";
    }

    public static void main(String args[]) throws UnknownHostException, IOException
    {
        Client client = new Client();
        ClientGUI client_gui = client.getClientGUI();

        Scanner scn = new Scanner(System.in);

        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection
        Socket s = new Socket(ip, 40000);

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
                            case("[MYLOGIN]"):
                                client_gui.addClient(val);
                                client.setId(val);
                                client_gui.setId(val);
                                break;
                            case("[LOGOUT]"):
                                client_gui.removeClient(val);
                                break;
                            case("[MSG]"):
                                StringTokenizer tok = new StringTokenizer(val, ":");
                                String transmitter = tok.nextToken();
                                String message = tok.nextToken();
                                String receiver = tok.nextToken();
                                client_gui.transmittMessage(transmitter, message, receiver, false);
                                break;
                            case("[ME]"):
                                StringTokenizer t = new StringTokenizer(val, ":");
                                String trans = t.nextToken();
                                String mes = t.nextToken();
                                String rec = t.nextToken();
                                client_gui.transmittMessage(trans, mes, rec, true);
                                break;
                            default:
                                client_gui.addClientLog(msg);
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