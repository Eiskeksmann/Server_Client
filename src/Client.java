import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.*;
import java.rmi.server.ExportException;
import java.util.Scanner;

public class Client
{
    final static int ServerPort = 40000;
    static boolean iswaiting = false;

    public static void main(String args[]) throws UnknownHostException, IOException
    {
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

                    iswaiting = false;
                    // read the message to deliver.
                    String msg = scn.nextLine();

                    try {
                        // write on the output stream
                        dos.writeUTF(msg);
                    } catch (IOException e) {

                        System.out.println("SERVER CONNECTION HAS BEEN LOST ...");
                        iswaiting = true;

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

                iswaiting = false;
                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = dis.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {

                        System.out.println("SERVER CONNECTION HAS BEEN LOST...");
                        iswaiting = true;
                        try{
                            dis.close();
                        }catch(IOException e1){

                            e1.printStackTrace();
                        }
                        break;
                    }
                }

            }
        });

        Thread reconnectOption = new Thread(new Runnable()
        {
            @Override
            public void run() {

                while (true) {
                    if(iswaiting) {
                        System.out.println("TYPE - [END] To Exit \n" + "TYPE - [RECONNECT] To Reconnect");
                        String cmd = scn.nextLine();
                        switch (cmd) {

                            case ("END"):
                                System.exit(0);
                                break;

                            case ("RECONNECT"):


                                break;
                        }
                    }
                }
            }
        });

        reconnectOption.start();
        sendMessage.start();
        readMessage.start();
    }
}