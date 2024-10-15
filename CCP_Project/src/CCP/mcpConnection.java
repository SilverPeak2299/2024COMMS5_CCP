package CCP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import java.util.Random;

/**
 * mcpConnection
 */
public class mcpConnection{
    private DatagramSocket clientSocket;

    DatagramPacket recivePacket;
    DatagramPacket sendPacket;

    messageQueue messages;
    

    jsonHandler JsonHandler;

    String mcpIP = "10.20.30.1";
    int mcpPort = 2001;

    Boolean connected = false;
    int msgsWithoutReply = 0;
    
    mcpConnection(jsonHandler JsonHandler) {
        this.JsonHandler = JsonHandler;
    }

    void inialiseConnection() {
        Random rand = new Random();
        Long lastMsg = System.currentTimeMillis();
        

        do {
            JsonHandler.setMcpSeq(1000 + rand.nextInt(29000)); // setting new seq number for new connection attempt
            Long currTime = System.currentTimeMillis();

            try {
                if (currTime - lastMsg > 2000) { // execute every 2 seconds
                    clientSocket = new DatagramSocket(mcpPort);
                    sendMsg(JsonHandler.generateMCPCommand("CCIN"));
                    lastMsg = System.currentTimeMillis();
                }
    
                reciveMsg();
                if (jsonHandler.searchJSON(messages.peakMessage().getMsg(), "message").equals("AKIN"))
                    connected = true;
    
            } catch (SocketException e) {
                // TODO: handle exception
                System.out.println("wating for mcp response");
            }
    
        } while (!isConnected());


    }

    boolean isConnected() {
        if (msgsWithoutReply >= 3) {
            connected = false;
        }

        return connected;
    }





    public void reciveMsg() {
        byte [] recive = new byte[999];

        try {
            recivePacket = new DatagramPacket(recive, recive.length);
            clientSocket.receive(recivePacket);

            String msg = new String(recivePacket.getData(), 0, recivePacket.getLength());
            messages.addMessage(JsonHandler.convertString(msg));

            msgsWithoutReply = 0; // resetting message counter

        } catch (Exception e) {
            // TODO: Log issue and stuffs
        }
    }

    public void sendMsg(String msg) {
        try {
            byte[] info = msg.getBytes();
            sendPacket = new DatagramPacket(info, info.length, InetAddress.getByName(mcpIP),mcpPort);
            clientSocket.send(sendPacket);

            msgsWithoutReply += 1; // Incrementing message count

        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}