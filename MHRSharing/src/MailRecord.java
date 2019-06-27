import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;

/**
 * Created by Error404 Team on 5/20/16.
 */

@WebServlet("/MailRecord")

public class MailRecord extends HttpServlet{

    private static String recordLocation = System.getProperty("user.dir");


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,NullPointerException {
        //Checking if there is an extracted record from the database
        if(extractRecord.extracted){
                allFunctions.defaultConfiguration();
                allFunctions.send(req.getParameter("destinationEmail"),"Medical Health Record", recordLocation+ File.separator+allFunctions.getRecordName());
                resp.sendRedirect("/emailSent.jsp");
                extractRecord.extracted=false;// setting back the flag to false to allow further sharing
        }
        //redirect to this page if not found
        else {
            resp.sendRedirect("/recordNotFound.jsp");
        }
    }


}
