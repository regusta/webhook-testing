package group22.utils;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import java.util.ArrayList;


//sets the commit status to one of the possible values according to
//github documentation, possible statuses are error or failure(red X in github),
//pending(yellow circle), or success (green checkmark)
public class SetCommitStatus {
    private static String token="";
    public static void setCommitStatus(String statusUrl, String state) {     
        try {
            //open and send a http post request to the given url
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(statusUrl);
            //procedure to authenticate user with server and set some fields
            //in the header
            //this is my local token
            httpPost.setHeader("Authorization", "token "+ "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");
            httpPost.setHeader("Content-type", "application/json");
            //set value in header
            httpPost.setHeader("Accept", "application/vnd.github+json");
            //set rest of parameters in response
            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("state", state));
            params.add(new BasicNameValuePair("message", state));
            params.add(new BasicNameValuePair("context", state));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //get response code to see if the commit status was set successfully
            //code 200 means successful
            int code = response.getStatusLine().getStatusCode();
            System.out.println(code);
            httpClient.close();
            response.close();
            // if(code==200){
            //     return "successful";
            // }
            // else {
            //     return "unsuccessful "+code;
            // }


        } catch (Exception e) {
            System.out.println( "Error setting commit status " + e.getMessage());
        }

    }

}
