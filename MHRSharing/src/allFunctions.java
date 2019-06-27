import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by d4k1d23 on 5/27/16.
 */
public abstract class allFunctions {
    private static String username, password, host, port;
    private static int patientID;
    private static String patientName;
    private static String patientLastName;
    private static String recordName;
    private static String dbHost;
    private static String dbUser;
    private static String dbPassword;
    private static String timeStampString;
    private static int dbDriver;
    private static String dbTableName;
    private enum COLUMNS {id, fname, lname, ss};

    public static void setRecordName(String recordName) {
        allFunctions.recordName = recordName;
    }

    public static String getRecordName() {
        return recordName;
    }

    public static void setTimeStampString(String timeStampString) {
        allFunctions.timeStampString = timeStampString;
    }

    public static void defaultConfiguration(){
        username = "openemr.healthrecord@gmail.com";
        password = "Cook68^put^^";
        host = "smtp.gmail.com";
        port = "587";
    }

    public static boolean generateFromDBFile(int id, String firstName, String lastName, String subdirectory, String fullFileName) throws Exception {
        Scanner fileScan = loadCSV(subdirectory,fullFileName);
        String tempFName, tempLName, tempSSN, tempDOB;
        int tempID;
        fileScan.useDelimiter(","); // setting the delimiter
        fileScan.nextLine();// skipping the first line since it is the header
        while(fileScan.hasNextLine()){
            tempID = Integer.parseInt(fileScan.next());
            tempFName = fileScan.next();
            tempLName = fileScan.next();
            tempDOB = fileScan.next();
            tempSSN = fileScan.next();
            if(tempID==id && tempFName.equals(firstName) && tempLName.equals(lastName)){
                CCDGenerator ccdGenerator = new CCDGenerator(tempID+"",tempFName,tempLName,tempDOB,fileScan.next(),fileScan.next(),fileScan.next(),fileScan.next());
                tempSSN=tempSSN.substring(7,11);
                String timeStamp = ccdGenerator.getTimeStampString();
                recordName = ccdGenerator.getFileName();
                CryptoUtils.encrypt(tempSSN+timeStamp, new File(recordName),
                        new File(recordName));
                System.out.println("Record found!");
                return true;
            }
            fileScan.nextLine();
        }
        System.err.println("Record not found!");
        return false;
    }

    private static Scanner loadCSV(String subDirectory, String fileName) {
        String path = System.getProperty("user.dir") + File.separator + subDirectory + File.separator;
        Scanner input = null;
        try {
            input = new Scanner(new File(path + fileName));
        } catch (FileNotFoundException e) {
            System.err.println("The file is not found!");
        }
        return input;
    }

    public static boolean generateRecord(int id, String firstName, String lastName) throws Exception{
        patientID=id;
        patientName=firstName;
        patientLastName=lastName;
        ResultSet resultSet = ConnectNQuery.connectNQuery(dbHost,dbUser,dbPassword,dbDriver,
                "select * from "+dbTableName+" where "+ COLUMNS.id+"="+patientID+" && "+ COLUMNS.fname+"="+patientName+" && " +
                        COLUMNS.lname+"="+patientLastName);
        if(resultSet.next()){
            new CCDGenerator(resultSet);
            String ssn = resultSet.getString(COLUMNS.ss.toString());
            CryptoUtils.encrypt(ssn.substring(6,10)+recordName, new File(recordName+".xml"),new File(recordName+".xml"));
            return true;
        }
        return false;
    }

    public static void loadDBConfig(String host, String user, String password, int driver, String tableName){
        dbHost=host;
        dbUser=user;
        dbPassword=password;
        dbDriver=driver;
        dbTableName=tableName;
    }

    public static String getPatientName() {
        return patientName;
    }

    public static String getPatientLastName() {
        return patientLastName;
    }

    public static boolean isInteger(String string){
        try {
            Integer.parseInt(string);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static void send(String to, String subject, String tempRecord){

        //Mail properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Default MimeMessage object.
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            //MimeBodyPart
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("This email contains a health record");

            //MimeMultipart
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();

            //Attachment
            File record = new File(tempRecord);
            DataSource source = new FileDataSource(record);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(record.getName());
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            Files.delete(record.toPath());
            System.out.println("Email sent!");

        } catch (MessagingException e) {
            System.err.println(e.getCause());
        } catch (IOException e) {
            System.err.println(e.getCause());
        }
    }
}
