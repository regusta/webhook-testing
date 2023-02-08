package group22.utils;
import java.io.BufferedReader;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.eclipse.jetty.server.Request;
import javax.servlet.http.HttpServletRequest;



public class Helpers {
    // create JSON object of the http post request body from the webhook
    // @param buff http Post request payload
    // "return JSON object containing the data from the webhook
    // @throws Exception
    public static JSONObject convertBody(HttpServletRequest request) throws Exception{
        //read the payload line by line
        StringBuilder st = new StringBuilder();
        BufferedReader buff = request.getReader();
        String temp=buff.readLine();
        String payload="";
        while(temp!=null){
                st.append(temp);
                temp=buff.readLine();
        }
        buff.close();
        
        payload = st.toString();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(payload);
        return json;
        
    }

    public static String getCloneUrl(JSONObject payload){
        return ((JSONObject) payload.get("repository")).get("clone_url").toString();
    }

    public static String getStatusUrl(JSONObject payload) {
        String tempStatusURL=((JSONObject) payload.get("repository")).get("statuses_url").toString();
        String sha=payload.get("after").toString();
        String temp="{sha}";
        String statusURL=tempStatusURL.replace(temp,sha);
        System.out.println(statusURL);
        return statusURL;
    }
}
