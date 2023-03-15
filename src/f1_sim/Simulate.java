package f1_sim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Anja Tanovic
 */
public class Simulate {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        //Keyboard input
        Scanner sc = new Scanner(System.in);

        String drivers_txt;
        File drivers_file;
        String venues_txt;
        File venues_file;

        do {
            System.out.print("Insert the absolute path to the driver text document: ");
            drivers_txt = sc.nextLine();
            drivers_file = new File(drivers_txt);
        } while (!drivers_file.exists());

        do {
            System.out.print("Insert the absolute path to the venue text document: ");
            venues_txt = sc.nextLine();
            venues_file = new File(venues_txt);
        } while (!venues_file.exists());

        int num_races = 0;
        String readString;
        do {
            System.out.println("How many races will be run in this championship? "
                    + "(3-5 is possible)");
            readString = sc.nextLine();
            num_races = Integer.parseInt(readString);
        } while (num_races > 5 || num_races < 3);

        Championship chmp = new Championship(drivers_txt, venues_txt, num_races);

        System.out.println("-------------------------------------");

    }
}
