package CCP;
import java.io.*;
import java.net.*;
import java.util.Random;


public class espConnection{
    private DatagramSocket serverSocket;
    int port;

    String esp32IP;
    int espSeq;

    boolean connection;
    int msgsWithoutReply = 0;


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

                espSeq = Integer.parseInt(jsonHandler.searchJSON(messages.peakMessage().getMsg(), "sequence_number"));
    
                if (jsonHandler.searchJSON(messages.peakMessage().getMsg(), "message").equals("INIT")) {
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
        if (msgsWithoutReply >= 3) { connection = false; }

        return connection;
    }

    public void reciveMsg() {
        byte [] recive = new byte[999];

        try {
            recivePacket = new DatagramPacket(recive, recive.length);
            serverSocket.receive(recivePacket);

            String msg = new String(recivePacket.getData(), 0, recivePacket.getLength());
            messages.addMessage(JsonHandler.convertString(msg));

            //TODO: Check Data is valid
            msgsWithoutReply = 0;

        } catch (Exception e) {
        }
    }

    public void sendMsg(String msg) {
        try {
            byte[] info = msg.getBytes();
            sendPacket = new DatagramPacket(info, info.length, InetAddress.getByName(esp32IP), port);
            serverSocket.send(sendPacket);
            msgsWithoutReply += 1;

        } catch (IOException e) {
            System.out.println("message failed to send");
        }
    }
}
