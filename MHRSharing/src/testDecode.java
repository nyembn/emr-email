import java.io.File;
import java.io.FileNotFoundException;
import java.nio.channels.IllegalBlockingModeException;
import java.util.Scanner;

/**
 * Created by d4k1d23 on 5/27/16.
 */
public class testDecode {
    public static void main(String[] args) {
        String fileName, SSN;
        Scanner in = new Scanner(System.in);

        try{
            System.out.println("Enter the file name + .xml (Note: if the file is not in the same directory as the .class file " +
                    "then enter the full directory + the file name + .xml): ");
            fileName = in.next();
            System.out.println("Enter the last 4 digits of the patient's SSN: ");
            SSN = in.next();
            if(SSN.length()!=4 && !allFunctions.isInteger(SSN)) throw new Exception();
            CryptoUtils.decrypt(SSN+getTimeStampFromFile(fileName),new File(fileName),
                    new File(fileName));
            System.out.println("Decryption successful!");

        }catch (IllegalBlockingModeException e){
            System.err.println("The file is not encrypted!");
        }catch (FileNotFoundException e) {
            System.err.println("File Not found!");
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println("Error occurred! Please make sure that the file ends with .xml and SSN is 4 digits");
        } finally {
            in.close();
        }
    }

    public static String getTimeStampFromFile(String fileName){
        return fileName.substring(fileName.length()-16,fileName.length()-4);
    }
}
