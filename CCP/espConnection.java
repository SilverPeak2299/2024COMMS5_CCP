package CCP;
import java.io.*;
import java.net.*;


public class espConnection{
    private DatagramSocket serverSocket;
    int port;
    String esp32IP;

    DatagramPacket recivePacket;
    DatagramPacket sendPacket;

    espConnection(int id) {
        port = 3000 + id;
        esp32IP = "10.20.30.1" + id;
    }


    public boolean inialiseConnection() {
        try {
            serverSocket = new DatagramSocket(port);

            if (reciveMsg().equals("Helo")) {
                sendMsg("Helo");

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

    public String reciveMsg() {
        byte [] recive = new byte[999];

        try {
            recivePacket = new DatagramPacket(recive, recive.length);
            serverSocket.receive(recivePacket);

            return new String(recivePacket.getData(), 0, recivePacket.getLength());
        } catch (Exception e) {
            // TODO: Log issue and stuffs
            return "";
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
