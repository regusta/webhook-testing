package group22;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
 
import java.io.IOException;
 
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import group22.utils.CloneRepository;
import group22.utils.Helpers;
import group22.utils.SetCommitStatus;



/** 
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class CIServer extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {   
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);

        JSONObject jsonObject = new JSONObject();
        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        try{
            jsonObject = Helpers.convertBody(request);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        String cloneUrl = Helpers.getCloneUrl(jsonObject);
        CloneRepository.cloneRepository(cloneUrl, "./repo");
        //2nd compile the code
                     

        //3rd set commit status
        String statusUrl=Helpers.getStatusUrl(jsonObject);
        SetCommitStatus.setCommitStatus(statusUrl,"success");
        


        response.getWriter().println("CI job done");

    }
 
    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new CIServer()); 
        server.start();
        server.join();
    }
}
