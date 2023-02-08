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
import group22.utils.CompileProject;
import group22.utils.Helpers;


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

        String method = request.getMethod();
        String cloneUrl = null;
        String branch = null;
        String localPath = "./repo";
        JSONObject jsonObject = new JSONObject();
        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        if ("POST".equals(method))
            try{
                jsonObject = Helpers.convertBody(request);
                cloneUrl = Helpers.getCloneUrl(jsonObject);
                branch = Helpers.getBranch(jsonObject);
                CloneRepository.cloneRepository(cloneUrl, localPath, branch);
                CompileProject.compileProject(localPath);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        
        // 1st clone your repository
        // 2nd compile the code
        // 3d  run all the tests
        
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
