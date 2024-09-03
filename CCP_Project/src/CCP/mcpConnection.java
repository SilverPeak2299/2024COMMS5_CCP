package CCP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

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
    
    mcpConnection(jsonHandler JsonHandler) {
        this.JsonHandler = JsonHandler;
    }

    boolean inialiseConnection() {
        try {
            clientSocket = new DatagramSocket(mcpPort);
            sendMsg(JsonHandler.generateMCPCommand("CCIN"));
            reciveMsg();

            if (JsonHandler.searchJSON(messages.peakMessage().getMsg(), "message").equals("AKIN"))
                return true;

            return false;

        } catch (SocketException e) {
            // TODO: handle exception
            return false;
        }
    }




    public void reciveMsg() {
        byte [] recive = new byte[999];

        try {
            recivePacket = new DatagramPacket(recive, recive.length);
            clientSocket.receive(recivePacket);

            String msg = new String(recivePacket.getData(), 0, recivePacket.getLength());
            messages.addMessage(JsonHandler.convertString(msg));

        } catch (Exception e) {
            // TODO: Log issue and stuffs
        }
    }

    public void sendMsg(String msg) {
        try {
            byte[] info = msg.getBytes();
            sendPacket = new DatagramPacket(info, info.length, InetAddress.getByName(mcpIP),mcpPort);
            clientSocket.send(sendPacket);

        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}