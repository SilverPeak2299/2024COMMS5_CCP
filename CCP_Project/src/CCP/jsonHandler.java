package CCP;

import org.json.simple.JSONObject;

/**
 * jsonHandler
 */
public class jsonHandler {
    String id;

    jsonHandler(int id) {
        this.id = "BR"+ id;
    }

   private String timestamp() {
    //TODO Impliment propper timestamp generation
    return "";
   }


@SuppressWarnings("unchecked")
public String generateCommand(String cmd) {
    JSONObject command = new JSONObject();
    
    command.put("client_type", "ccp");
    command.put("message", cmd);
    command.put("client_id", id);
    command.put("timestamp", timestamp());

    return command.toJSONString();
   }
}