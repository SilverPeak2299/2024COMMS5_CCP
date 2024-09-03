package CCP;

/**
 * connectionManager
 */
public class connectionManager {
    int id;
    espConnection espCon;
    mcpConnection mcpCon;

    connectionManager(int id) {
        this.id = id;
        espCon = new espConnection(id);
    }

    void inialiseConnections () {
    // initalising esp connection
    while (!espCon.inialiseConnection());
    
    

    }
}