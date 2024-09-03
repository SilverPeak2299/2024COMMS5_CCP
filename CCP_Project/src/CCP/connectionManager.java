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

    void inialiseConnections () {
    // initalising esp connection
    while (!espCon.inialiseConnection());
    
    

    }
}