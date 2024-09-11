package CCP;
import java.io.*;
import java.util.logging.*;

/**
 * ccp
 */
public class ccp {
    final static int id = 26; // Id can either be 26 or 27 depending on the blade runner

    public static void main(String[] args) {
        connectionManager conManager = new connectionManager(id);
        conManager.inialiseConnections();
        
    }
    
}