package CCP;

/**
 * connectionManager
 */
public class connectionManager {
    int id;
    espConnection espCon;
    mcpConnection mcpCon;

    jsonHandler JsonHandler;


    connectionManager(int id) {
        this.id = id;
        JsonHandler = new jsonHandler(id);

        espCon = new espConnection(id, JsonHandler);
        mcpCon = new mcpConnection(JsonHandler);
    }

    void enforeceConnection () {
    // Checking conenctions
        if (!espCon.isConnected()) {
            espCon.inialiseConnection();
            espCon.status = "ERR";
        }

        if (!mcpCon.isConnected()) { mcpCon.inialiseConnection(); }
    }

    //TODO: check if there actually exists a message
    boolean checkMcpMessages() {
        mcpCon.reciveMsg();
        return true;
    }

    boolean checkEspMessages() {
        espCon.reciveMsg();
        return true;
    }

    messageQueue getMcpMessages() {
        return mcpCon.messages;
    }

    messageQueue getEspMessages() {
        return espCon.messages;
    }

    void mcpAckExec() {
        mcpCon.sendMsg(JsonHandler.generateMCPCommand("AKEX"));
    }

    void mcpStat() {
        mcpCon.sendMsg(JsonHandler.generateMCPCommand("STAT", espCon));
    }

    void espExec(String cmd) {
        espCon.sendMsg(JsonHandler.generateMCPCommand(cmd, espCon));
    }
}