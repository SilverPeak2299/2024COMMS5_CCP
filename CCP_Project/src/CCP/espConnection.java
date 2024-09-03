package CCP;
import java.io.*;
import java.net.*;


public class espConnection{
    private DatagramSocket serverSocket;
    int port;
    String esp32IP;

    DatagramPacket recivePacket;
    DatagramPacket sendPacket;

    jsonHandler JsonHandler;
    messageQueue messages;

    espConnection(int id, jsonHandler JsonHandler) {
        port = 3000 + id;
        esp32IP = "10.20.30.1" + id;

        this.JsonHandler = JsonHandler;
        messages = new messageQueue();
    }


    public boolean inialiseConnection() {
        try {
            serverSocket = new DatagramSocket(port);
            reciveMsg();

            if (JsonHandler.searchJSON(messages.peakMessage().getMsg(), "message").equals("INIT")) {
                sendMsg(JsonHandler.generateESPCommand("INIT"));

                return true;
            }

            return false;

        } catch (Exception sockException) {
            System.out.println();
            return false;
        }
    }


    public boolean isConnected() {
        // TODO Auto-generated method stub
        return false;
    }

    public void reciveMsg() {
        byte [] recive = new byte[999];

        try {
            recivePacket = new DatagramPacket(recive, recive.length);
            serverSocket.receive(recivePacket);

            String msg = new String(recivePacket.getData(), 0, recivePacket.getLength());
            messages.addMessage(JsonHandler.convertString(msg));

        } catch (Exception e) {
            // TODO: Log issue and stuffs
        }
    }

    public void sendMsg(String msg) {
        try {
            byte[] info = msg.getBytes();
            sendPacket = new DatagramPacket(info, info.length, InetAddress.getByName(esp32IP), port);
            serverSocket.send(sendPacket);

        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}
