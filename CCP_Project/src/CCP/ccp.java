package CCP;
import java.io.*;
import java.util.logging.*;
import java.util.stream.Collectors;

/**
 * ccp
 */
public class ccp {
    final static int id = 26; // Id can either be 26 or 27 depending on the blade runner

    static boolean running = true;
    static messageQueue mcpMsgs;
    static messageQueue espMsgs;
    static connectionManager conManager;

    public static void main(String[] args) {
        conManager = new connectionManager(id);
        mcpMsgs = conManager.getMcpMessages();
        espMsgs = conManager.getEspMessages();

        while (running) {
            conManager.enforeceConnection();

            //conManager.checkMcpMessages();
            conManager.espExec(getInput());
        }
    }

    static String getInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.lines().collect(Collectors.joining("\n"));
    }


    static void processMcpMsg() {
        String msg =  jsonHandler.searchJSON(mcpMsgs.peakMessage().getMsg() , "message");

        if (msg.equals("STRQ")) {
            conManager.mcpStat();

        } else if (msg.equals("EXEC")) {
            String action = jsonHandler.searchJSON(mcpMsgs.peakMessage().getMsg() , "action");
            conManager.mcpAckExec();

            switch (action) {
                case "STOPC":
                    conManager.espExec(action);
                    break;

                case "STOPO":
                    conManager.espExec(action);
                    break;
            
                case "FLOWC":
                    conManager.espExec(action);
                    break;
            
                case "FFASTC":
                    conManager.espExec(action);
                    break;

                case "RSLOWC":
                    conManager.espExec(action);
                    break;
            
                case "DISCONNECT":
                    conManager.espExec(action);
                    break;
    
                default:
                    System.out.println("Wrong Action Code");
                    break;
            }
        }  
    }
    
}