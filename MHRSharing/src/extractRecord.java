import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * Created by Error404 Team on 5/21/16.
 */
@WebServlet ("/extractRecord")
public class extractRecord extends HttpServlet {
    public static boolean extracted = false;



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //getting the information about the patient that has been searched
            allFunctions.loadDBConfig("jdbc:mysql://localhost/openemr?zeroDateTimeBehavior=convertToNull", "root", "", 2, "patient_data");
            extracted = allFunctions.generateRecord(Integer.parseInt(req.getParameter("patientID")), req.getParameter("patientName"), req.getParameter("patientLastName"));
            if (extracted) resp.sendRedirect("/recordFound.jsp");
            else resp.sendRedirect("/recordNotFound.jsp");

        }//if an error occured
            catch(Exception e){
                extracted = false;
                resp.sendRedirect("/recordNotFound.jsp");
            }
    }

}
