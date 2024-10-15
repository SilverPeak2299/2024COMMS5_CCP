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

    int ccpMcpSeq;
    int ccpEspSeq;

    JSONParser parser;

    jsonHandler(int id) {
        this.id = "BR"+ id;
        parser = new JSONParser();
    }


    // java throws a fit cause the JSONobject is an extension of hashmap
    @SuppressWarnings("unchecked")
    public String generateMCPCommand(String cmd) {
        JSONObject command = new JSONObject();
    
        command.put("client_type", "ccp");
        command.put("message", cmd);
        command.put("client_id", id);
        command.put("sequence_number", ccpMcpSeq);

        ccpMcpSeq += 1;
        return command.toJSONString();
    }

    // java throws a fit cause the JSONobject is an extension of hashmap
    @SuppressWarnings("unchecked")
    public String generateESPCommand(String cmd) {
        JSONObject command = new JSONObject();
        command.put("message", cmd);
        command.put("sequence_number", ccpEspSeq);

        ccpEspSeq += 1;
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

    static String searchJSON(JSONObject msg, String key) { // this type casting might give some shit idk
        return (String) msg.get(key);
    }

    public void setMcpSeq(int mcpSeq) {
        this.ccpMcpSeq = mcpSeq;
    }

    public void setEspSeq(int espSeq) {
        this.ccpEspSeq = espSeq;
    }
}