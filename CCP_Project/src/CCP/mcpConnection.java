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

    jsonHandler JsonHandler;

    String mcpIP = "10.20.30.1";
    int mcpPort = 2001;
    
    mcpConnection(jsonHandler JsonHandler) {
        this.JsonHandler = JsonHandler;
    }

    boolean inialiseConnection() {
        try {
            clientSocket = new DatagramSocket(mcpPort);
            sendMsg(JsonHandler.generateCommand("CCIN"));

            //TODO check for ACK in protocol then return true / false

        } catch (SocketException e) {
            // TODO: handle exception
            return false;
        }
    }




    public String reciveMsg() {
        byte [] recive = new byte[999];

        try {
            recivePacket = new DatagramPacket(recive, recive.length);
            clientSocket.receive(recivePacket);

            return new String(recivePacket.getData(), 0, recivePacket.getLength());
        } catch (Exception e) {
            // TODO: Log issue and stuffs
            return "";
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