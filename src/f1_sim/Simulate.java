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

        int numRaces = 0;
        String readString;
        do {
            System.out.println("How many races will be run in this championship? "
                    + "(3-5 is possible)");
            readString = sc.nextLine();
            numRaces = Integer.parseInt(readString);
        } while (numRaces > 5 || numRaces < 3);

        Championship chmp = new Championship(drivers_txt, venues_txt, numRaces);

        System.out.println("---------------------------------------");
        int chosenRace = 0;
        int[] races = new int[numRaces]; //array of chosen races, example: 2 5 3 are chosen
        for (int i = 1; i <= numRaces; i++) {
            System.out.println("* RACE " + i + " *");
            System.out.println("Choose venue for race number " + i);
            for (int j = 1; j <= chmp.getNumberOfVenues(); j++) {
                if (!chmp.isChosenRace(j - 1)) {
                    System.out.println(j + ") " + chmp.getVenuesName(j - 1));
                }
            }
            do {
                readString = sc.nextLine();
                chosenRace = Integer.parseInt(readString);
            } while (chosenRace > chmp.getNumberOfVenues() || chosenRace < 1 || chmp.isChosenRace(chosenRace - 1));

            chmp.setChosenRace(chosenRace - 1);
            races[i - 1] = chosenRace - 1;
        }

        System.out.println("---------------------------------------");
        System.out.println("RACING CHAMPIONSHIP IS ABOUT TO BEGIN!");
        System.out.println("---------------------------------------");

        for (int i = 0; i < numRaces; i++) {
            System.out.println("* RACE NUMBER " + (i + 1) + " IS STARTING *");
            System.out.println("---------------------------------------");
            for (int j = 0; j < chmp.getNumberOfLaps(i); j++) {
                //prepare !!
                System.out.println("Drivers are ready to start lap number " + (j + 1)
                        + " on vanue " + chmp.getVenuesName(races[i]) + ".");
                chmp.driveAverageLapTime(races[i]);
                chmp.applySpecialSkills();

                System.out.println("Lap number " + (j + 1) + " is finished.");
                chmp.checkMechanicalProblem();
                //chmp.updatePoints();
                //chmp.printLeader();

                System.out.println("Insert enter to continue.");
                readString = sc.nextLine();
            }
            //chmp.printWinnersAfterRace();
            System.out.println("---------------------------------------");
        }
        System.out.println("CHAMPIONSHIP IS FINISHED.");
        System.out.println("---------------------------------------");

        //chmp.printChampion();
    }
}
