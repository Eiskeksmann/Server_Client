package Network;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import Graphics.*;
import Logic.Spieler;
import Logic.VierGewinnt;
import Util.Location;

public class Client
{
    private GWindow gui;
    private ClientGUI client_gui;
    private GameGUI game_gui;
    private String id;
    private GWindow<GameGUI> game_window;

    public GWindow<ClientGUI> getGUI(){ return gui; }
    public ClientGUI getClientGUI(){ return client_gui; }
    public GameGUI getGame_gui(){ return game_gui; }
    public String getId(){ return id; }

    public void setId(String set){ id = set; }
    public void setGame_gui(GameGUI set){
        game_gui = set;
        game_window = new GWindow<>(game_gui);
    }

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
                            case("[HOST]"):
                                //val-> V|opponent
                                StringTokenizer s = new StringTokenizer(val, "|");
                                String gametype = s.nextToken();
                                String player = s.nextToken();
                                if(gametype.equals("V")) {

                                    client.setGame_gui(new VierGewinntGUI(
                                            new Spieler(client.getId(),false),
                                            new Spieler(player,false)));
                                    client.getGame_gui().startGame();
                                    client_gui.addLog("Your Turn-Make your Turn with: \n" +
                                            "VTURN / FTURN");

                                } else if(gametype.equals("F")) {

                                    client.setGame_gui(new FutternGUI(
                                            new Spieler(client.getId(),false),
                                            new Spieler(player,false)));
                                    client.getGame_gui().startGame();
                                    dos.writeUTF("CREATE-"+player);
                                }
                                break;
                            case("[CONNECT]"):
                                StringTokenizer k = new StringTokenizer(val, "|");
                                String type = k.nextToken();
                                String host = k.nextToken();
                                if(type.equals("V")){
                                    client.setGame_gui(new VierGewinntGUI(
                                            new Spieler(host,false),
                                            new Spieler(client.getId(),false)));
                                    client.getGame_gui().startGame();

                                } else if (type.equals("F")){

                                    client.setGame_gui(new FutternGUI(
                                            new Spieler(host,false),
                                            new Spieler(client.getId(),false)));
                                    client.getGame_gui().startGame();
                                }
                                break;
                            case("[OWNVTURN]"):
                                if(client.getGame_gui().getHostId().equals(client.getId())){
                                    //i am the host --> player 1
                                    client.getGame_gui().setSp1Turn(
                                            new Location(Integer.parseInt(val),0));
                                    client_gui.addLog("Enemys Turn now - wait for his VTURN");

                                }else {
                                    //i am not the host --> player 2
                                    client.getGame_gui().setSp2Turn(
                                            new Location(Integer.parseInt(val),0));
                                    client_gui.addLog("Enemys Turn now - wait for his VTURN");
                                }
                                break;
                            case("[OWNFTURN]"):
                                StringTokenizer z = new StringTokenizer(val, "|");
                                int fturnvalx = Integer.parseInt(z.nextToken());
                                int fturnvaly = Integer.parseInt(z.nextToken());

                                if(client.getGame_gui().getHostId().equals(client.getId())){
                                    //i am the host --> player 1
                                    client.getGame_gui().setSp1Turn(
                                            new Location(fturnvalx, fturnvaly));
                                    client_gui.addLog("Enemys Turn now - wait for his FTURN");
                                }else {
                                    //i am not the host --> player 2
                                    client.getGame_gui().setSp2Turn(
                                            new Location(fturnvalx, fturnvaly));
                                    client_gui.addLog("Enemys Turn now - wait for his FTURN");
                                }
                                break;
                            case("[VTURN]"):
                                if(client.getGame_gui().getHostId().equals(client.getId())){
                                    //i am the host --> player 1
                                    client.getGame_gui().setSp1Turn(
                                            new Location(Integer.parseInt(val),0));
                                    client_gui.addLog("Your Turn now - Make your Turn with VTURN");
                                }else {
                                    //i am not the host --> player 2
                                    client.getGame_gui().setSp2Turn(
                                            new Location(Integer.parseInt(val),0));
                                    client_gui.addLog("Your Turn now - Make your Turn with VTURN");
                                }
                                break;
                            case("[FTURN]"):
                                StringTokenizer x = new StringTokenizer(val, "|");
                                int fturnx = Integer.parseInt(x.nextToken());
                                int fturny = Integer.parseInt(x.nextToken());

                                if(client.getGame_gui().getHostId().equals(client.getId())){
                                    //i am the host --> player 1
                                    client.getGame_gui().setSp1Turn(
                                            new Location(fturnx, fturny));
                                    client_gui.addLog("Your Turn now - Make your Turn with FTURN");
                                }else {
                                    //i am not the host --> player 2
                                    client.getGame_gui().setSp2Turn(
                                            new Location(fturnx, fturny));
                                    client_gui.addLog("Your Turn now - Make your Turn with FTURN");
                                }
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