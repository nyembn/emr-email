import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by d4k1d23 on 5/27/16.
 */
public class testSend {
    private static int id;
    private static String firstName, lastName, email;
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        try{
            System.out.print("Enter the id of the patient: ");
            id = in.nextInt();
            System.out.print("Enter the first name of the patient: ");
            firstName = in.next();
            System.out.print("Enter the last name of the patient: ");
            lastName = in.next();
            if(!allFunctions.generateFromDBFile(id,firstName,lastName,"testData","patient_data.csv")) throw new Exception();
            System.out.print("Enter the email you want to send the record to: ");
            email = in.next();
            allFunctions.defaultConfiguration();
            allFunctions.send(email,"Medical Health Record", allFunctions.getRecordName());
            in.close();

        } catch (NumberFormatException e){
            System.err.println("Input error!");
        } catch (InputMismatchException e){
            System.err.println("Input error! The id must be a number and the first and last name have to be strings!");
        } catch (Exception e) {
            System.err.println("Error occurred!");
        } finally {
            in.close();
        }

    }

}
