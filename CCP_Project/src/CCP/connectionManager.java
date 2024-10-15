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
        if (!espCon.isConnected()) { espCon.inialiseConnection(); }

        if (!mcpCon.isConnected()) { mcpCon.inialiseConnection(); }
    }

    //TODO: check if there actually exists a message
    boolean checkMcpMessages() {
        mcpCon.reciveMsg();
        return true;
    }

    messageQueue getMcpMessages() {
        return mcpCon.messages;
    }

    messageQueue getEspMessages() {
        return espCon.messages;
    }


}