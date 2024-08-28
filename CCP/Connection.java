package CCP;

public abstract class Connection {
    private int port; // port 3026 , 3027
    private String ip; //  p 10.20.30.126 , ip 10.20.30.127
    
    Connection(int id){
        port = 3000 + id;
        ip = "10.20.30.1" + id;
    }

    public abstract boolean inialiseConnection();

    public abstract boolean isConnected();
}


