import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;


/**
 * Created by Error404 Team on 5/22/16.
 */
@WebServlet ("/decodeRecord")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB
        maxFileSize=1024*1024*50,      	// 50 MB
        maxRequestSize=1024*1024*100)   	// 100 MB

public class decodeRecord extends HttpServlet {
    private String uploadFilePath = System.getProperty("user.home") + File.separator+"Desktop"+File.separator;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        try {
            File fileSaveDir = new File(uploadFilePath);

            //check if the dir does not exist and creates one
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }

            //getting the http part named fileName
            Part part = request.getPart("fileName");
            String fileName = getFileName(part);
            String[] parseFileName = fileName.split("\\\\");  //for Windows system to get the filename without the path information
            fileName = parseFileName[parseFileName.length - 1];

            //writing the file to the specified path
            part.write(uploadFilePath + File.separator + fileName);

            //decrypting the chosen encrypted file
            CryptoUtils.decrypt(request.getParameter("ssn")+fileName.substring(0,12),new File(uploadFilePath + File.separator + fileName),
                    new File(uploadFilePath + File.separator + fileName));

            //notifying the user
            response.sendRedirect("/fileDecoded.jsp");

        } catch (Exception e) {
            response.sendRedirect("/decryptionFailed.jsp");
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }
}
