package CCP;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * jsonHandler
 * 
 * - Tool for working with Json inputs should be static but the json library throws a fit.
 * should probably make this a singleton
 */
public class jsonHandler {
    String id;
    JSONParser parser;

    jsonHandler(int id) {
        this.id = "BR"+ id;
        parser = new JSONParser();
    }

   private String timestamp() {
    //TODO Impliment propper timestamp generation
    return "";
   }

    // java throws a fit cause the JSONobject is an extension of hashmap
    @SuppressWarnings("unchecked")
    public String generateMCPCommand(String cmd) {
        JSONObject command = new JSONObject();
    
        command.put("client_type", "ccp");
        command.put("message", cmd);
        command.put("client_id", id);
        command.put("timestamp", timestamp());

        return command.toJSONString();
    }

    // java throws a fit cause the JSONobject is an extension of hashmap
    @SuppressWarnings("unchecked")
    public String generateESPCommand(String cmd) {
        JSONObject command = new JSONObject();
        command.put("message", cmd);

        return command.toJSONString();
    }

    JSONObject convertString(String msg) {
        try {
            return (JSONObject) parser.parse(msg);

        } catch (Exception e) {
            return null;
            // TODO: handle exception
        }
    }

    String searchJSON(JSONObject msg, String key) { // this type casting might give some shit idk
        return (String) msg.get(key);
    }

}