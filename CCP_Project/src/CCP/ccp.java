package CCP;
import java.io.*;
import java.util.logging.*;

/**
 * ccp
 */
public class ccp {
    final static int id = 26; // Id can either be 26 or 27 depending on the blade runner

    static boolean running = true;
    static messageQueue mcpMsgs;
    static messageQueue espMsgs;

    public static void main(String[] args) {
        connectionManager conManager = new connectionManager(id);
        mcpMsgs = conManager.getMcpMessages();
        espMsgs = conManager.getEspMessages();

        while (running) {
            conManager.enforeceConnection();

            conManager.checkMcpMessages();
            
            processMcpMsg();

        }
    }




    static void processMcpMsg() {
        String msg =  jsonHandler.searchJSON(mcpMsgs.peakMessage().getMsg() , "message");

        if (msg.equals("STRQ")) {

        } else if (msg.equals("EXEC")) {
            String action = jsonHandler.searchJSON(mcpMsgs.peakMessage().getMsg() , "action");

            switch ("action") {
                case "STOPC":
                    break;

                case "STOPO":
                    break;
                
                case "FLOWC":
                    break;
                
                case "FFASTC":
                    break;

                case "RSLOWC":
                    break;
                
                case "DISCONNECT":
                    break;
    
                default:
                    System.out.println("Wrong Action code");
                    break;
            }
        }
       
        
        
        
        switch (msg) {
            case "STRQ":
                
                break;

            case "EXEC":
                
                break;

            default:
                break;
        }
    }
    
}