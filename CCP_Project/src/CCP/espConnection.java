package CCP;
import java.io.*;
import java.net.*;
import java.util.Random;


public class espConnection{
    private DatagramSocket serverSocket;
    int port;

    String esp32IP;
    int espSeq;

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


    public void inialiseConnection() {

        do {
            try {
                serverSocket = new DatagramSocket(port);
                reciveMsg();

                espSeq = Integer.parseInt(JsonHandler.searchJSON(messages.peakMessage().getMsg(), "sequence_number"));
    
                if (JsonHandler.searchJSON(messages.peakMessage().getMsg(), "message").equals("INIT")) {
                    sendMsg(JsonHandler.generateESPCommand("INIT"));
                }

            } catch (Exception sockException) {
                System.out.println("Waiting for ESP connection");
            }
    
        } while (isConnected() == false);



        Random rand = new Random();
        JsonHandler.setEspSeq(100 + rand.nextInt(100));

    }


    public boolean isConnected() {
        //TODO I wan my notebook
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
